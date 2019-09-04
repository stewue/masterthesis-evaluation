package ch.uzh.ifi.seal.smr.soa.evaluation.comments

import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.apache.logging.log4j.LogManager
import org.eclipse.jdt.core.dom.*
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 4) {
        log.error("Needed arguments: inputFile inputDir outputFile #threads")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]
    val outputFile = File(args[2])
    val numberOfThreads = args[3].toInt()

    val executor = Executors.newFixedThreadPool(numberOfThreads) as ThreadPoolExecutor
    inputFile.forEachLine { project ->
        executor.submit<Any> {
            val dir = File(inputDir + project.toFileSystemName)
            if (dir.exists()) {
                log.info("[$project] evaluation started")
                processProject(project, dir, outputFile)
                log.info("[$project] evaluation ended")
            } else {
                log.warn("[$project] repo does not exist -> evaluation skipped")
            }
        }
    }
}

private fun processProject(project: String, sourceDir: File, outputFile: File) {
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
            val className = sourceFilePath.substringAfter(sourceDirectory.absolutePath).substring(1)
            val cv = CommentVisitor(javaUnit, text, className, project)

            comments.forEach {
                it.accept(cv)
            }

            val classVisitor = ClassVisitor()
            javaUnit.accept(classVisitor)
            val fqnClass = classVisitor.fullyQualifiedClassName

            cv.methodNames.forEach {
                outputFile.appendText("$project;$className;$fqnClass.$it\n")
            }
        }
    }, null)
}