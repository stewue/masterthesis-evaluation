package ch.uzh.ifi.seal.smr.soa.result.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.Row
import java.io.File

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val items = CsvProjectParser(file).getList().filter { it.repoAvailable }

    jmhVersion(items)
}

private fun jmhVersion(items: List<Row>) {
    val list = items
            .filter{
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

    println("version,count")
    sorted.forEach { (value, count) ->
        println("$value,$count")
    }
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