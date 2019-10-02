package ch.uzh.ifi.seal.smr.soa.analysis.jmhversionnormalized

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.analysis.jmhDate
import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

private val output = mutableListOf<ResJmhVersionNormalized>()

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
            .groupingBy { it }
            .eachCount()

    val versions = jmhDate.toList().filter { it.first.patch == 0 }.reversed()

    versions.forEachIndexed{ i, current ->
        var difference = if(i + 1 < versions.size){
            val nextVersion = versions[i + 1]
            round((nextVersion.second - current.second) / yearInSeconds * 12 )
        }else{
            15.0
        }

        if(difference < 1){
            difference = 1.0
        }

        val count = list.getValue(current.first)
        val normalizedCount = count / difference
        output.add(ResJmhVersionNormalized(current.first, normalizedCount))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmhVersionNormalized::class.java))
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