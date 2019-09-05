package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import java.io.File
import java.nio.file.Paths

class EvaluationCurrentCommit(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {
    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        evaluate(project, null, null, sourceDir, Paths.get(outputDir.absolutePath, "${project.toFileSystemName}.csv"), outputFile)
    }
}