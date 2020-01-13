package ch.uzh.ifi.seal.smr.soa.analysis.executiontimerepocorrelation

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResExecutionTimeParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
private val projects = CsvProjectParser(projectFile).getList()
private val output = mutableListOf<ResExecutionTimeRepoCorrelation>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontimerepocorrelation.csv").toPath()
    val executionTime = CsvResExecutionTimeParser(file).getList()

    executionTime.forEach { e ->
        if (!e.onlySingleShot && !e.onlyModeChanged) {
            val p = projects.first { it.project == e.project }
            output.add(ResExecutionTimeRepoCorrelation(e.project, e.className, e.jmhVersion, e.benchmarkName, e.executionTimePercentage, e.warmupTimePercentage, e.measurementTimePercentage, e.measurementWarmupRatio, p.stars!!, p.forks!!, p.watchers!!, p.numberOfCommits!!, p.numberOfContributors!!, p.numberOfBenchmarks!!, e.parameterizationCombinations))
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResExecutionTimeRepoCorrelation::class.java))
}