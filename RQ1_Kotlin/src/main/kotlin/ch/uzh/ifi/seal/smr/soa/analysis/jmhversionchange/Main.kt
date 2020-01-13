package ch.uzh.ifi.seal.smr.soa.analysis.jmhversionchange

import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

private val output = mutableListOf<ResJmhVersionChange>()

fun main() {
    val fileHistory = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\merged.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmhversionchange.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val projects = CsvProjectParser(fileProjects).getList()

    val changed = items.filter { it.jmhVersion != null }
            .map { it.project to it.jmhVersion }
            .distinct()
            .groupingBy { it.first }
            .eachCount()

    val final = changed.toList().map { (project, count) ->
        val p = projects.find { it.project == project }!!
        if (p.numberOfBenchmarks!! == 0) {
            null
        } else {
            val numberOfBenchmarks = p.numberOfBenchmarks!!
            val useJmhSince = p.lastCommit!! - p.firstBenchmarkFound!!
            project to Triple(count - 1, numberOfBenchmarks, useJmhSince)
        }
    }.filterNotNull()

    println("How often was JmhVersion changed if project uses jmh over a year (add jmh as new dependency does not count)")
    println(averageChanged(minUsed(final, yearInSeconds.toInt())))

    var sum = 0.0
    var counter = 0
    final.forEach { (project, triple) ->
        val p = projects.first { it.project == project }
        val useJmhSince = round((p.lastCommit!! - p.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0
        if (triple.first > 0) {
            val averageChangeTime = round(triple.third / (triple.first + 1).toDouble() / yearInSeconds * 100) / 100
            output.add(ResJmhVersionChange(project, triple.second, triple.first, averageChangeTime, useJmhSince))
            sum += averageChangeTime
            counter++
        } else {
            output.add(ResJmhVersionChange(project, triple.second, triple.first, null, useJmhSince))
        }
    }
    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmhVersionChange::class.java))

    println("Average time between two changes in years")
    println(sum / counter)
}

fun minUsed(list: List<Pair<String, Triple<Int, Int, Int>>>, minInSeconds: Int): List<Pair<String, Triple<Int, Int, Int>>> {
    return list.filter { (_, pair) ->
        pair.third >= minInSeconds
    }
}

fun averageChanged(list: List<Pair<String, Triple<Int, Int, Int>>>): Double {
    return list.map { (_, pair) ->
        pair.first
    }.average()
}