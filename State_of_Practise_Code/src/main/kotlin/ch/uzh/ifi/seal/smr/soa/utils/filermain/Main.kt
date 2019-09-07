package ch.uzh.ifi.seal.smr.soa.utils.filermain

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.toGithubName
import java.io.File

fun main() {
    val inputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\projects\\current-commit")
    val outputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\projects\\current-filtered")
    val csv = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")

    val projects = CsvProjectParser(csv).getList()
    val isMain = projects.map {
        it.project to (it.mainRepo ?: false)
    }.toMap()

    inputDir.walk().forEach {
        if (it.isFile) {
            val project = it.nameWithoutExtension.toGithubName
            if (isMain[project] == true) {
                val newFile = File("${outputDir.absolutePath}/${it.name}")
                it.copyTo(newFile)
            }
        }
    }
}