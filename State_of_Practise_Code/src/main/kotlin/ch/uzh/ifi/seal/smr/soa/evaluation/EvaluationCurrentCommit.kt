package ch.uzh.ifi.seal.smr.soa.evaluation

import java.io.File

class EvaluationCurrentCommit(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {
    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        evaluate(project, null, null, sourceDir, outputDir, outputFile)
    }
}