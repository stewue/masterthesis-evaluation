package ch.uzh.ifi.seal.smr.soa.result.jmhversionchange

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import java.io.File

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val items = CsvResultParser(fileHistory).getList()
    val projects = CsvProjectParser(fileProjects).getList()

    val changed = items.filter { it.jmhVersion != null }
            .map { it.project to it.jmhVersion }
            .distinct()
            .groupingBy { it.first }
            .eachCount()

    val final = changed.toList().map { (project, count) ->
        val numberOfBenchmarks = projects.filter { it.project == project }.first().numberOfBenchmarks!!
        project to Pair(count - 1, numberOfBenchmarks)
    }

    println(averageChanged(minNumberOfBenchmarks(final, 1)))
    println(averageChanged(minNumberOfBenchmarks(final, 10)))
    println(averageChanged(minNumberOfBenchmarks(final, 25)))
    println(averageChanged(minNumberOfBenchmarks(final, 50)))
    println(averageChanged(minNumberOfBenchmarks(final, 100)))
}

fun minNumberOfBenchmarks(list: List<Pair<String, Pair<Int, Int>>>, minBenchs: Int): List<Pair<String, Pair<Int, Int>>> {
    return list.filter { (_, pair) ->
        pair.second >= minBenchs
    }
}

fun averageChanged(list: List<Pair<String, Pair<Int, Int>>>): Double {
    return list.map { (_, pair) ->
        pair.first
    }.average()
}