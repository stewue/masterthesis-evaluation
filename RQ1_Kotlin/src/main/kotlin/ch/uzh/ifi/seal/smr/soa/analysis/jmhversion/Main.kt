package ch.uzh.ifi.seal.smr.soa.analysis.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val output = mutableListOf<ResJmhVersion>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvProjectParser(file).getList()

    val list = items
            .filter {
                it.mainRepo == true
            }
            .map {
                convertJmhVersionWithoutPatch(it.jmhVersion)
            }
            .filterNotNull()
            .groupingBy { it }
            .eachCount()
            .toList()

    val sorted = list.sortedWith(
            compareBy {
                it.first
            }
    )

    sorted.forEach { (value, count) ->
        output.add(ResJmhVersion(value, count))
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