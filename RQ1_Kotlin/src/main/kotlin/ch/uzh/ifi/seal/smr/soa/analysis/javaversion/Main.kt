package ch.uzh.ifi.seal.smr.soa.analysis.javaversion

import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

private val output = mutableListOf<ResJavaVersion>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\javaversion.csv").toPath()

    val items = CsvProjectParser(file).getList()

    val list = items
            .filter {
                it.mainRepo == true && it.numberOfBenchmarks!! > 0 && (!it.javaTarget.isNullOrBlank() || !it.javaSource.isNullOrBlank())
            }
            .map {
                val version = if (it.javaTarget.isNullOrBlank()) {
                    it.javaSource!!
                } else {
                    it.javaTarget
                }
                val useJmhSince = if (it.lastCommit == null || it.firstBenchmarkFound == null) {
                    0.0
                } else {
                    round((it.lastCommit!! - it.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0
                }

                Pair(version, useJmhSince)
            }

    val shortLived = list.filter { it.second < 1 }
            .map { it.first }
            .groupingBy { it }
            .eachCount()

    val longLived = list.filter { it.second >= 1 }
            .map { it.first }
            .groupingBy { it }
            .eachCount()

    val versions = listOf("1.5", "1.6", "1.7", "1.8", "9", "10", "11", "12")
    versions.forEach { version ->
        val short = shortLived[version] ?: 0
        val long = longLived[version] ?: 0
        output.add(ResJavaVersion(version, short + long, short, long))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJavaVersion::class.java))
}