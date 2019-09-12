package ch.uzh.ifi.seal.smr.soa.result.comments

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import java.io.File

fun main() {
    val comments = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\comments\\current-commit-comments.csv")
    val history = File("D:\\mp\\history-all.csv")

    val allBenchmarks = CsvResultParser(history).getList()

    comments.forEachLine { comment ->
        val (project, _, className, benchmarkName) = comment.split(";")
        val find = allBenchmarks.filter { it.project == project && it.className == className && it.benchmarkName == benchmarkName }

        val found = find.isNotEmpty()
        println("$found;$project;$className;$benchmarkName")
    }
}