package ch.uzh.ifi.seal.smr.soa.datapreparation.firstbenchmarkfound

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outFile = File("D:\\mp\\new.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val projects = CsvProjectParser(fileProjects).getList()

    val firstFound = items.groupBy { it.project }
            .map { (project, list) ->
                project to list.map { it.commitTime!! }.min()!!
            }
            .toMap()

    projects.forEach {
        it.firstBenchmarkFound = firstFound[it.project]
    }

    OpenCSVWriter.write(outFile, projects)
}