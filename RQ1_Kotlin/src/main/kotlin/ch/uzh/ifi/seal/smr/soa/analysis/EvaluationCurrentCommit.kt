package ch.uzh.ifi.seal.smr.soa.analysis

import ch.uzh.ifi.seal.smr.soa.analysis.history.HistoryManager
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.eclipse.jgit.api.Git
import java.io.File
import java.nio.file.Paths

class EvaluationCurrentCommit(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {
    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        super.processProject(project, sourceDir, outputDir, outputFile)
        HistoryManager.getRepo(sourceDir).use { repository ->
            Git(repository).use { git ->
                val commits = HistoryManager.sampleCommits(repository, git)
                val currentCommit = commits.first()
                val commitId = currentCommit.second.name
                val commitTime = currentCommit.first
                evaluate(project, commitId, commitTime, sourceDir, Paths.get(outputDir.absolutePath, "${project.toFileSystemName}.csv"), outputFile)
            }
        }
    }
}