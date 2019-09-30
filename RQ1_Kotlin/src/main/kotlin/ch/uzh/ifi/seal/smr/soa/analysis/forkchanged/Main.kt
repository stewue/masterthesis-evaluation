package ch.uzh.ifi.seal.smr.soa.analysis.forkchanged

import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File
import java.nio.file.Paths

fun main() {
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val currentBenchmarkFile = File("D:\\mp\\current-merged.csv")
    val historyPath = "D:\\mp\\history-per-project"
    val forkAheadFile = File("D:\\mp\\forkAhead.csv")

    val projects = CsvProjectParser(projectFile).getList()
    val currentBenchmark = CsvResultParser(currentBenchmarkFile).getList()
    val forkAhead = CsvForkAheadParser(forkAheadFile).getList()

    val mainProjects = projects.map {
        it.project to if (it.mainRepo == true) {
            ""
        } else {
            it.parentProject
        }
    }.toMap()

    val forkNothingChanged = forkAhead.filter { it.nothingChanged == true }.map{ it.project }

    val output = mutableListOf<ResForkChanged>()
    val outputFile = File("D:\\mp\\out.csv").toPath()

    projects.filter { it.repoAvailable == true && it.mainRepo != true && it.numberOfBenchmarks!! > 0 && it.rootProject != "orbit/orbit" }.forEach { p ->
        val benchmarks = currentBenchmark.filter { it.project == p.project }

        if(forkNothingChanged.contains(p.project)){
            benchmarks.forEach { b ->
                output.add(ResForkChanged(p.project, b.className, b.benchmarkName, true, true, true, false))
            }
            return@forEach
        }

        var mainProject = p.parentProject
        while (mainProject != null && !mainProjects[mainProject].isNullOrBlank()) {
            mainProject = mainProjects[mainProject]
        }

        if (mainProject.isNullOrBlank()) {
            return@forEach
        }

        val historyFile = Paths.get(historyPath, "${mainProject!!.toFileSystemName}.csv").toFile()

        if (!historyFile.exists()) {
            benchmarks.forEach { b ->
                output.add(ResForkChanged(p.project, b.className, b.benchmarkName, null, false, null, true))
            }
            return@forEach
        }
        val historyBenchmark = CsvResultParser(historyFile).getList()
        benchmarks.forEach { b ->
            val sameBenchmark = historyBenchmark.filter { it.className == b.className && it.benchmarkName == b.benchmarkName }

            val (sameConfig, sameCode, bothSame) = if (sameBenchmark.isNotEmpty()) {
                val config = sameConfig(sameBenchmark, b)
                val code = sameBenchmark.map { it.methodHash }.contains(b.methodHash)
                Triple(config, code, config && code)
            } else {
                Triple(null, null, null)
            }

            output.add(ResForkChanged(p.project, b.className, b.benchmarkName, sameConfig, sameCode, bothSame, sameBenchmark.isEmpty()))
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResForkChanged::class.java))
}

private fun sameConfig(sameBenchmark: List<Result>, benchmark: Result): Boolean {
    sameBenchmark.forEach {
        if (it.sameConfig(benchmark)) {
            return true
        }
    }

    return false
}