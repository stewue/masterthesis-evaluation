package ch.uzh.ifi.seal.smr.soa.datapreparation.lastcommit

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val input = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\history\\history-selected-commits.csv")
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val projects = CsvProjectParser(projectFile).getList()

    val map = mutableMapOf<String, Pair<Int, String>>()
    input.forEachLine {
        if (it == "project,commitTime,commitId") {
            return@forEachLine
        }
        val (project, time, commitId) = it.split(",")
        if (map[project] == null) {
            map[project] = Pair(time.toInt(), commitId)
        }
    }

    map.forEach { (key, value) ->
        val project = projects.first { it.project == key }
        project.lastCommit = value.first
    }

    OpenCSVWriter.write(projectFile.toPath(), projects)
}