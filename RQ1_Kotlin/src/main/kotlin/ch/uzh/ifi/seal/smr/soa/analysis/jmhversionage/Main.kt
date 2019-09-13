package ch.uzh.ifi.seal.smr.soa.analysis.jmhversionage

import ch.uzh.ifi.seal.smr.soa.analysis.jmhDate
import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File
import kotlin.math.round

private val output = mutableListOf<ResJmhVersionAge>()

fun main() {
    val fileBenchmarks = File("D:\\mp\\current-merged-isMain.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvResultParser(fileBenchmarks).getList()
    val projects = CsvProjectParser(fileProjects).getList()

    items.filter { it.jmhVersion != null }
            .groupBy { it.project }
            .map { (_, items) ->
                Pair(items.first(), items.size)
            }
            .forEach { (item, count) ->
                val jmhVersionDate = jmhDate.getValue(item.jmhVersion!!)
                val timeDiff = round((item.commitTime!! - jmhVersionDate) / yearInSeconds * 100) / 100.0

                val project = projects.find { it.project == item.project }!!
                val useJmhSince = round((project.lastCommit!! - project.firstBenchmarkFound!!) / yearInSeconds * 100) / 100.0
                output.add(ResJmhVersionAge(item.project, count, timeDiff, useJmhSince))
            }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmhVersionAge::class.java))
}