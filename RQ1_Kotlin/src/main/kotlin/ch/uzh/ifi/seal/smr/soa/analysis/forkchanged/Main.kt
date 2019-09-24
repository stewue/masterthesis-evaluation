package ch.uzh.ifi.seal.smr.soa.analysis.forkchanged

import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File
import java.nio.file.Paths

fun main() {
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val currentBenchmarkFile = File("D:\\mp\\current-merged.csv")
    val historyPath = "D:\\mp\\history-per-project"

    val projects = CsvProjectParser(projectFile).getList()
    val currentBenchmark = CsvResultParser(currentBenchmarkFile).getList()

    val mainProjects = projects.map {
        it.project to if (it.mainRepo == true) {
            ""
        } else {
            it.parentProject
        }
    }.toMap()

    val output = mutableListOf<ResForkChanged>()
    val outputFile = File("D:\\mp\\out.csv").toPath()

    projects.filter { it.repoAvailable == true && it.mainRepo != true && it.numberOfBenchmarks!! > 0 && it.rootProject != "orbit/orbit" }.forEach { p ->
        val benchmarks = currentBenchmark.filter { it.project == p.project }
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
            val lastInHistoryBefore = sameBenchmark.filter { it.commitTime!! <= b.commitTime!! }.sortedByDescending { it.commitTime }.firstOrNull()

            val sameConfig = lastInHistoryBefore?.sameConfig(b)
            val sameCode = sameBenchmark.map { it.methodHash }.contains(b.methodHash)
            val bothSame = if (sameConfig == null) {
                null
            } else {
                sameCode && sameConfig
            }

            output.add(ResForkChanged(p.project, b.className, b.benchmarkName, sameConfig, sameCode, bothSame, sameBenchmark.isEmpty()))
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResForkChanged::class.java))
}