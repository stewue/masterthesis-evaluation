package ch.uzh.ifi.seal.smr.soa.numberOfBenchmarks

import ch.uzh.ifi.seal.bencher.analysis.finder.jdt.JdtBenchFinder
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.system.exitProcess

val log = LogManager.getLogger()

fun main(args: Array<String>) {
    if (args.size != 1) {
        log.error("Needed arguments: dir")
        exitProcess(-1)
    }

    val dir = File(args[0])
    val outputFile = File(args[0] + "benchmarks.csv")

    dir.listFiles().forEach {

        if (it.isDirectory) {
            val name = it.name
            try {
                val res = doPerProject(it)
                log.info("Process $name => $res")
                outputFile.appendText("${name.replace('#', '/')};$res\n")
            } catch (e: Exception) {
                log.error("Error during parsing $name: ${e.message}")
            }
        }

    }
}

fun doPerProject(sourceDir: File): Int {
    val finder = JdtBenchFinder(sourceDir)

    return finder.all().right().get().size
}