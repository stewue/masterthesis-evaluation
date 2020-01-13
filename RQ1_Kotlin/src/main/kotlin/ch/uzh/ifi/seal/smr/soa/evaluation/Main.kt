package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.smr.soa.utils.disableSystemErr
import org.apache.logging.log4j.LogManager
import java.io.File
import kotlin.system.exitProcess

private val log = LogManager.getLogger()

fun main(args: Array<String>) {
    disableSystemErr()

    if (args.size != 4) {
        // inputFile = see input.csv as example file
        // inputDir = Folder where all projects are stored
        // outputDir = Folder where created files are persisted
        // outputFile = File for error messages
        log.error("Needed arguments: inputFile inputDir outputDir outputFile")
        exitProcess(-1)
    }

    val inputFile = File(args[0])
    val inputDir = args[1]
    val outputDir = File(args[2])
    val outputFile = File(args[3])

    /////////////////////////////////
    // Choose here type of evaluation
    /////////////////////////////////
    val evaluation = EvaluationCurrentCommit(inputFile, inputDir, outputDir, outputFile)
    //val evaluation = EvaluationCurrentTag(inputFile, inputDir, outputDir, outputFile)
    //val evaluation = EvaluationSampleCommits(inputFile, inputDir, outputDir, outputFile)
    evaluation.start()
}