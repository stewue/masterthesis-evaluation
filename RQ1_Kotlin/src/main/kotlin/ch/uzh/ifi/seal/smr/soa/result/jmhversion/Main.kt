package ch.uzh.ifi.seal.smr.soa.result.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.result.jmhDate
import ch.uzh.ifi.seal.smr.soa.result.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.Row
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val items = CsvProjectParser(file).getList().filter { it.repoAvailable }

    jmhVersion(items)
    println("-----")
    jmhDate(items)
}

private fun jmhVersion(items: List<Row>) {
    val list = items
            .groupingBy { it.jmhVersion }
            .eachCount()
            .map {
                val key = convertJmhVersion(it.key)
                if (key == null) {
                    null
                } else {
                    key to it.value
                }
            }
            .filterNotNull()

    val sorted = list.sortedWith(
            compareBy {
                it.first
            }
    )

    val total = sorted.map { it.second }.sum()
    sorted.forEach { (value, count) ->
        println("$value -> $count (${percentageString(count, total)})")
    }
}

private fun jmhDate(items: List<Row>) {
    val list = items
            .filter { !it.jmhVersion.isNullOrBlank() }
            .map { jmhDate.getValue(convertJmhVersion(it.jmhVersion)!!) }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedBy { it.first }

    val total = list.map { it.second }.sum()
    list.forEach { (value, count) ->
        println("${getDate(value)} -> $count (${percentageString(count, total)})")
    }
}

private fun convertJmhVersion(input: String?): JMHVersion? {
    return if (input == null || input.isNullOrBlank()) {
        null
    } else {
        val list = input.split(".")
        val major = list[0].toInt()
        val minor = list[1].toInt()
        val patch = list.getOrNull(2)?.toInt() ?: 0
        JMHVersion(major, minor, patch)
    }
}

private fun getDate(timestamp: Int): String {
    val date = Date(timestamp.toLong() * 1000)
    return SimpleDateFormat("yyyy-MM").format(date)
}