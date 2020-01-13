package ch.uzh.ifi.seal.smr.soa.analysis.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.analysis.jmhDate
import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

private val output = mutableListOf<ResJmhVersion>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversion.csv").toPath()

    val items = CsvProjectParser(file).getList()

    val list = items
            .filter {
                it.mainRepo == true && it.numberOfBenchmarks!! > 0
            }
            .map {
                val version = convertJmhVersionWithoutPatch(it.jmhVersion)
                val useJmhSince = if (it.lastCommit == null || it.firstBenchmarkFound == null) {
                    0.0
                } else {
                    round((it.lastCommit!! - it.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0
                }

                Pair(version, useJmhSince)
            }
            .filter { it.first != null }

    val shortLived = list.filter { it.second < 1 }
            .map { it.first }
            .filterNotNull()
            .groupingBy { it }
            .eachCount()

    val longLived = list.filter { it.second >= 1 }
            .map { it.first }
            .filterNotNull()
            .groupingBy { it }
            .eachCount()

    val versions = jmhDate.toList().filter { it.first.patch == 0 }.reversed()

    versions.forEach { (version, _) ->
        val short = shortLived[version] ?: 0
        val long = longLived[version] ?: 0
        output.add(ResJmhVersion(version, short + long, short, long))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmhVersion::class.java))
}

private fun convertJmhVersionWithoutPatch(input: String?): JMHVersion? {
    return if (input == null || input.isNullOrBlank()) {
        null
    } else {
        val list = input.split(".")
        val major = list[0].toInt()
        val minor = list[1].toInt()
        JMHVersion(major, minor)
    }
}