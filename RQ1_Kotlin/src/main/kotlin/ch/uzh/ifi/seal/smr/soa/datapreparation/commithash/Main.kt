package ch.uzh.ifi.seal.smr.soa.datapreparation.commithash

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.toFileSystemName
import java.io.File
import java.nio.file.Paths

fun main() {
    val input = "C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results"
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val projects = CsvProjectParser(projectFile).getList()

    projects.forEach {
        val file = Paths.get(input, "${it.project.toFileSystemName}.csv").toFile()

        if (file.exists()) {
            val lines = file.readLines()
            if (lines.isNotEmpty()) {
                val firstLine = lines.first()
                val (_, commitHash, _) = firstLine.split(",")
                it.lastCommitHash = commitHash.replace("\"", "")
            }
        }
    }

    OpenCSVWriter.write(projectFile.toPath(), projects)
}