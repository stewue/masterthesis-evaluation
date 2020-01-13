package ch.uzh.ifi.seal.smr.soa.analysis.comments

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val output = mutableListOf<ResComment>()

fun main() {
    val comments = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\comments\\current-commit-comments.csv")
    val history = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\merged.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\comments.csv").toPath()

    val allBenchmarks = CsvResultParser(history).getList()

    comments.forEachLine { comment ->
        val (project, _, className, benchmarkName) = comment.split(";")
        val find = allBenchmarks.filter { it.project == project && it.className == className && it.benchmarkName == benchmarkName }

        val found = find.isNotEmpty()
        output.add(ResComment(found, project, className, benchmarkName))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResComment::class.java))
}