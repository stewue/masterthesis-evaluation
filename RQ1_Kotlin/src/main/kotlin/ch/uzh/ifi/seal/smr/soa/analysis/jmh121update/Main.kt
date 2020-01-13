package ch.uzh.ifi.seal.smr.soa.analysis.jmh121update

import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.toSecond
import java.io.File

fun main() {
    val fileHistory = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\merged.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\jmh121update.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val output = mutableListOf<ResJmh121Update>()

    val grouped = items.groupBy { Triple(it.project, it.className, it.benchmarkName) }

    grouped.forEach { (triple, list) ->
        val firstPost121 = list.lastOrNull { it.jmhVersion != null && it.jmhVersion!!.compareTo(jmh120) == 1 }
        val firstPre121 = list.lastOrNull { it.jmhVersion != null && it.jmhVersion!!.compareTo(jmh120) < 1 }

        if (firstPost121 != null && firstPre121 != null) {
            val sameConfig = firstPost121.sameConfigWithoutMode(firstPre121)
            output.add(ResJmh121Update(triple.first, triple.second, triple.third, sameConfig, firstPre121.warmupIterations, firstPost121.warmupIterations, firstPre121.warmupTime?.toSecond(), firstPost121.warmupTime?.toSecond(), firstPre121.measurementIterations, firstPost121.measurementIterations, firstPre121.measurementTime?.toSecond(), firstPost121.measurementTime?.toSecond(), firstPre121.forks, firstPost121.forks))
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmh121Update::class.java))
}