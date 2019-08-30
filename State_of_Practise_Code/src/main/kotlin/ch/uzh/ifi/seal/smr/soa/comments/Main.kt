package ch.uzh.ifi.seal.smr.soa.comments

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.apache.logging.log4j.LogManager
import org.eclipse.jdt.core.dom.*
import java.io.File
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 2) {
        log.error("Needed arguments: inputFile inputDir")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]
    val outputFile = File(args[1] + "comments.csv")

    val list = CsvResultParser(inputFile).getList()

    list.forEach {
        if (!it.forked) {
            val dir = File(inputDir + "${it.project.toFileSystemName}")
            if (dir.exists()) {
                log.info("Process $dir")
                doPerProject(it.project, dir, outputFile)
            } else {
                log.warn("Project ${it.project} does not exist in input directory -> skipped")
            }
        }
    }
}

fun doPerProject(project: String, sourceDir: File, outputFile: File) {
    val filePaths = sourceDir.walkTopDown().filter { f ->
        f.isFile && f.extension == "java"
    }.map { it.absolutePath }.toList().toTypedArray()

    parse(project, sourceDir, filePaths, outputFile)
}

private fun parse(project: String, sourceDirectory: File, filePaths: Array<String>, outputFile: File) {
    val parser = ASTParser.newParser(AST.JLS11)
    parser.setKind(ASTParser.K_COMPILATION_UNIT)
    parser.setEnvironment(arrayOf<String>(), arrayOf(sourceDirectory.absolutePath), arrayOf("UTF-8"), true)
    parser.setResolveBindings(true)
    parser.setBindingsRecovery(true)
    parser.createASTs(filePaths, null, arrayOf(), object : FileASTRequestor() {
        override fun acceptAST(sourceFilePath: String, javaUnit: CompilationUnit) {
            val comments = javaUnit.commentList as List<ASTNode>
            val text = File(sourceFilePath).readLines()
            val cv = CommentVisitor(javaUnit, text)

            comments.forEach {
                it.accept(cv)
            }

            if (cv.counter > 0) {
                val className = sourceFilePath.substringAfter(sourceDirectory.absolutePath).substring(1)
                outputFile.appendText("$project;$className;${cv.counter}\n")
            }
        }
    }, null)
}