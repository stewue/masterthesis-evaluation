package ch.uzh.ifi.seal.smr.soa.analysis.benchmarkrepocorrelation

import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File

private val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
private val projects = CsvProjectParser(projectFile).getList()
private val output = mutableListOf<ResBenchmarkRepoCorrelation>()

fun main() {
    val file = File("D:\\mp\\current-merged-isMain.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()
    val benchmarks = CsvResultParser(file).getList()

    benchmarks.forEach { b ->
        val p = projects.first { it.project == b.project }
        output.add(ResBenchmarkRepoCorrelation(b.project, b.className, b.benchmarkName, b.warmupIterations, b.warmupTime, b.measurementIterations, b.measurementTime, b.forks, b.warmupForks, b.modeIsThroughput.toInt(), b.modeIsAverageTime.toInt(), b.modeIsSampleTime.toInt(), b.modeIsSingleShotTime.toInt(), p.stars!!, p.forks!!, p.watchers!!, p.numberOfCommits!!, p.numberOfContributors!!, p.numberOfBenchmarks!!, b.nothingSet().toInt(), b.parameterizationCombinations))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResBenchmarkRepoCorrelation::class.java))
}