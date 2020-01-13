package ch.uzh.ifi.seal.smr.soa.analysis.samplecommits

import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvCommitParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

fun main() {
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val commitFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\history-selected-commits.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\samplecommits.csv").toPath()

    val projects = CsvProjectParser(projectFile).getList()
    val commits = CsvCommitParser(commitFile).getList()

    val groupedCommits = commits.groupBy { it.project }

    val output = projects
            .filter {
                it.mainRepo == true && it.numberOfBenchmarks!! > 0
            }
            .map { p ->
                val projectCommits = groupedCommits.getValue(p.project).filter { it.commitTime >= p.firstBenchmarkFound!! }
                val useJmhSince = round((p.lastCommit!! - p.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0

                ResSampleCommit(p.project, projectCommits.size, useJmhSince)
            }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResSampleCommit::class.java))
}