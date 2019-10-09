package ch.uzh.ifi.seal.smr.soa.analysis.javaversionchange

import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val projects = CsvProjectParser(fileProjects).getList()
    val output = mutableListOf<ResJavaVersionChange>()

    val changed = items.filter { !it.javaTarget.isNullOrBlank() || !it.javaTarget.isNullOrBlank() }
            .map {
                it.project to if (it.javaTarget == null) {
                    it.javaSource
                } else {
                    it.javaTarget
                }
            }
            .distinct()
            .groupBy { it.first }

    val shortLived = mutableMapOf<Int, Int>()
    val longLived = mutableMapOf<Int, Int>()

    changed.forEach { (project, list) ->
        val changeCount = list.size - 1

        val p = projects.first { it.project == project }

        if (p.numberOfBenchmarks!! > 0) {
            val useJmhSince = if (p.lastCommit == null || p.firstBenchmarkFound == null) {
                0.0
            } else {
                round((p.lastCommit!! - p.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0
            }

            if (useJmhSince >= 1) {
                longLived[changeCount] = longLived.getOrDefault(changeCount, 0) + 1
            } else {
                shortLived[changeCount] = shortLived.getOrDefault(changeCount, 0) + 1
            }
        }
    }

    longLived.keys.forEach { count ->
        val short = shortLived[count] ?: 0
        val long = longLived[count] ?: 0
        output.add(ResJavaVersionChange(count, short, long))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJavaVersionChange::class.java))
}