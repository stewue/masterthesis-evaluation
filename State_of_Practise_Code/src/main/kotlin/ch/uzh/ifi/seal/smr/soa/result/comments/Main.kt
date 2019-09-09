package ch.uzh.ifi.seal.smr.soa.result.comments

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import java.io.File

fun main() {
    val comments = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\projects\\current-commit-comments.csv")
    val history = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\project-history\\merged.csv")

    val allBenchmarks = CsvResultParser(history).getList()

    comments.forEachLine { comment ->
        val (project, file, className, benchmarkName) = comment.split(";")
        val fqn = "$className.$benchmarkName"
        val find = allBenchmarks.filter { it.project == project && it.benchmarkName == fqn }

        val found = find.isNotEmpty()
        println("$found;$project;$className;$benchmarkName")
    }
}