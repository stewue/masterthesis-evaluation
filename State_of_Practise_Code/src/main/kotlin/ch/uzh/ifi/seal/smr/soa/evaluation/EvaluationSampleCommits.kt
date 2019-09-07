package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.smr.soa.evaluation.history.HistoryManager
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import org.eclipse.jgit.api.Git
import java.io.File
import java.nio.file.Paths
import kotlin.system.exitProcess

class EvaluationSampleCommits(inputFile: File, inputDir: String, outputDir: File, outputFile: File) : Evaluation(inputFile, inputDir, outputDir, outputFile) {

    override fun processProject(project: String, sourceDir: File, outputDir: File, outputFile: File) {
        try {
            HistoryManager.getRepo(sourceDir).use { repository ->
                Git(repository).use { git ->
                    HistoryManager.resetToBranch(git)
                    val commits = HistoryManager.sampleCommits(repository, git)
                    commits.forEach { commit ->
                        try {
                            val commitId = commit.second.name
                            val commitTime = commit.first
                            log.info("[$project] Checkout commit $commitId")
                            HistoryManager.resetToHead(git, commitId)
                            val resultFile = Paths.get(outputDir.absolutePath, "${project.toFileSystemName}-$commitTime.csv")
                            evaluate(project, commitId, commitTime, sourceDir, resultFile, outputFile)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            log.error("${e.cause}: ${e.message}")
                            exitProcess(-1)
                        }
                    }
                    if (commits.isEmpty()) {
                        log.error("[$project] No commit was selected")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("${e.cause}: ${e.message}")
            exitProcess(-1)
        }
    }
}