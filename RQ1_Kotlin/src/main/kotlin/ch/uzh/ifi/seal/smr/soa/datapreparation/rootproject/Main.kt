package ch.uzh.ifi.seal.smr.soa.datapreparation.rootproject

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val csv = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val list = CsvProjectParser(csv).getList()

    val parent = mutableMapOf<String, String?>()

    list.forEach {
        if (it.parentProject != null) {
            parent[it.project] = it.parentProject
        }
    }

    list.forEach {
        if (it.repoAvailable) {
            var lastParent: String? = it.project
            var currentParent = it.parentProject

            while (!currentParent.isNullOrBlank()) {
                lastParent = currentParent
                currentParent = parent[currentParent]
            }

            it.rootProject = lastParent
        }
    }

    OpenCSVWriter.write(csv.toPath(), list)
}