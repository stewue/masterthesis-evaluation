package ch.uzh.ifi.seal.smr.soa.analysis

import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 4) {
        log.error("Needed arguments: inputFile inputDir outputDir outputFile")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]
    val outputDir = File(args[2])
    val outputFile = File(args[3])

    val evaluation = EvaluationCurrentCommit(inputFile, inputDir, outputDir, outputFile)
    evaluation.start()
}