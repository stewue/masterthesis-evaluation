package ch.uzh.ifi.seal.smr.soa.result.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.Row
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private val jmhDate = mapOf(
        JMHVersion(1, 21) to 1525132800,
        JMHVersion(1, 20) to 1514764800,
        JMHVersion(1, 19) to 1493596800,
        JMHVersion(1, 18) to 1488326400,
        JMHVersion(1, 17, 5) to 1485907200,
        JMHVersion(1, 17, 4) to 1483228800,
        JMHVersion(1, 17, 3) to 1480550400,
        JMHVersion(1, 17, 2) to 1480550400,
        JMHVersion(1, 17, 1) to 1477958400,
        JMHVersion(1, 17) to 1477958400,
        JMHVersion(1, 16) to 1477958400,
        JMHVersion(1, 15) to 1472688000,
        JMHVersion(1, 14, 1) to 1472688000,
        JMHVersion(1, 14) to 1472688000,
        JMHVersion(1, 13) to 1464739200,
        JMHVersion(1, 12) to 1459468800,
        JMHVersion(1, 11, 3) to 1451606400,
        JMHVersion(1, 11, 2) to 1443657600,
        JMHVersion(1, 11, 1) to 1441065600,
        JMHVersion(1, 11) to 1441065600,
        JMHVersion(1, 10, 5) to 1438387200,
        JMHVersion(1, 10, 4) to 1438387200,
        JMHVersion(1, 10, 3) to 1435708800,
        JMHVersion(1, 10, 2) to 1435708800,
        JMHVersion(1, 10, 1) to 1433116800,
        JMHVersion(1, 10) to 1433116800,
        JMHVersion(1, 9, 3) to 1430438400,
        JMHVersion(1, 9, 2) to 1430438400,
        JMHVersion(1, 9, 1) to 1427846400,
        JMHVersion(1, 9) to 1427846400,
        JMHVersion(1, 8) to 1427846400,
        JMHVersion(1, 7, 1) to 1427846400,
        JMHVersion(1, 7) to 1425168000,
        JMHVersion(1, 6, 3) to 1425168000,
        JMHVersion(1, 6, 2) to 1425168000,
        JMHVersion(1, 6, 1) to 1422748800,
        JMHVersion(1, 6) to 1422748800,
        JMHVersion(1, 5, 2) to 1422748800,
        JMHVersion(1, 5, 1) to 1420070400,
        JMHVersion(1, 5) to 1420070400,
        JMHVersion(1, 4, 2) to 1420070400,
        JMHVersion(1, 4, 1) to 1417392000,
        JMHVersion(1, 4) to 1417392000,
        JMHVersion(1, 3, 4) to 1417392000,
        JMHVersion(1, 3, 3) to 1417392000,
        JMHVersion(1, 3, 2) to 1414800000,
        JMHVersion(1, 3, 1) to 1414800000,
        JMHVersion(1, 3) to 1414800000,
        JMHVersion(1, 2) to 1414800000,
        JMHVersion(1, 1, 1) to 1409529600,
        JMHVersion(1, 1) to 1409529600,
        JMHVersion(1, 0, 1) to 1409529600,
        JMHVersion(1, 0) to 1406851200,
        JMHVersion(0, 9, 8) to 1406851200,
        JMHVersion(0, 9, 7) to 1406851200,
        JMHVersion(0, 9, 6) to 1406851200,
        JMHVersion(0, 9, 5) to 1404172800,
        JMHVersion(0, 9, 4) to 1404172800,
        JMHVersion(0, 9, 3) to 1404172800,
        JMHVersion(0, 9, 2) to 1404172800,
        JMHVersion(0, 9, 1) to 1401580800,
        JMHVersion(0, 9) to 1401580800,
        JMHVersion(0, 8) to 1398902400,
        JMHVersion(0, 7, 3) to 1398902400,
        JMHVersion(0, 7, 2) to 1398902400,
        JMHVersion(0, 7, 1) to 1398902400,
        JMHVersion(0, 7) to 1398902400,
        JMHVersion(0, 6) to 1396310400,
        JMHVersion(0, 5, 7) to 1396310400,
        JMHVersion(0, 5, 6) to 1396310400,
        JMHVersion(0, 5, 5) to 1393632000,
        JMHVersion(0, 5, 4) to 1393632000,
        JMHVersion(0, 5, 3) to 1393632000,
        JMHVersion(0, 5, 2) to 1393632000,
        JMHVersion(0, 5) to 1393632000,
        JMHVersion(0, 4, 2) to 1391212800,
        JMHVersion(0, 4, 1) to 1391212800,
        JMHVersion(0, 4) to 1391212800,
        JMHVersion(0, 3, 2) to 1391212800,
        JMHVersion(0, 3, 1) to 1388534400,
        JMHVersion(0, 3) to 1388534400,
        JMHVersion(0, 2, 1) to 1388534400,
        JMHVersion(0, 2) to 1385856000,
        JMHVersion(0, 1) to 1383264000
)

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
        println("$value -> $count (${percentage(count, total)})")
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
        println("${getDate(value)} -> $count (${percentage(count, total)})")
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