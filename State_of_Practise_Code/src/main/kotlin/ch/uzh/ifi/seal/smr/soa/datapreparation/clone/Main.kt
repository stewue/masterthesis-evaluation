package ch.uzh.ifi.seal.smr.soa.datapreparation.clone

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.size != 2){
        println("Needed arguments: inputFile outputDirectory")
        exitProcess(-1)
    }

    val inFile = File(args[0])
    val outputDir = args[1]

    val res = CsvResultParser(inFile).getList()

    res.forEach {
        val projectName = it.project.toFileSystemName

        val projectDir = Paths.get(outputDir, projectName).toFile()
        val c = projectDir.mkdir()

        if (!c) {
            println("${it.project} already exists")
            return@forEach
        }

        val process = Runtime.getRuntime().exec("git clone ${it.cloneUrl} .", null, projectDir)
        process.waitFor()

        println("${it.project} created")
    }
}