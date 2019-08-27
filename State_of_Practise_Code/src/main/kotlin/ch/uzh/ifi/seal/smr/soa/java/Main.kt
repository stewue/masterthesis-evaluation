package ch.uzh.ifi.seal.smr.soa.java

import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    if (args.size != 1) {
        log.error("Needed arguments: inputDirectory")
        exitProcess(-1)
    }

    val inDir = File(args[0])
    val outputFile = File(args[0] + "java.csv")

    inDir.listFiles().forEach {
        if (it.isDirectory) {
            val res = JavaVersionExtractor(it).get()
            log.info("$it : $res")

            val resString = res ?: ""
            outputFile.appendText("${it.name.replace('#', '/')};$resString\n")
        }
    }
}