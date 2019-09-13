package ch.uzh.ifi.seal.smr.soa.result.jmhversionchange

import ch.uzh.ifi.seal.smr.soa.result.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val output = mutableListOf<ResJmhVersionChange>()

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val projects = CsvProjectParser(fileProjects).getList()

    val changed = items.filter { it.jmhVersion != null }
            .map { it.project to it.jmhVersion }
            .distinct()
            .groupingBy { it.first }
            .eachCount()

    val final = changed.toList().map { (project, count) ->
        val p = projects.find { it.project == project }!!
        val numberOfBenchmarks = p.numberOfBenchmarks!!
        val useJmhSince = p.lastCommit!! - p.firstBenchmarkFound!!
        project to Triple(count - 1, numberOfBenchmarks, useJmhSince)
    }

    println("How often was jJmhVersion changed if project uses jmh over a year (add jmh as new dependency does not count)")
    println(averageChanged(minUsed(final, yearInSeconds.toInt())))

    //output.add(ResJmhVersionChange(item.project, count, timeDiff, useJmhSince))
    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmhVersionChange::class.java))
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