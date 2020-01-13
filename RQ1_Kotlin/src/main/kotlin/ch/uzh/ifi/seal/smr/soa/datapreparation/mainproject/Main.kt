package ch.uzh.ifi.seal.smr.soa.datapreparation.mainproject

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import org.apache.logging.log4j.LogManager
import java.io.File

private val log = LogManager.getLogger()

fun main() {
    val csv = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val list = CsvProjectParser(csv).getList()

    val numberOfStars = mutableMapOf<String, MutableList<Pair<String, Int>>>()
    val isMain = mutableListOf<String>()

    list.forEach {
        if (it.repoAvailable) {
            if (numberOfStars[it.rootProject] == null) {
                numberOfStars[it.rootProject!!] = mutableListOf()
            }

            numberOfStars.getValue(it.rootProject!!).add(Pair(it.project, it.stars!!))
        }
    }

    numberOfStars.forEach { (name, list) ->
        list.sortByDescending { it.second }
        val mainName = list.first().first

        val root = list.find { it.first == name }
        if (root!!.first != mainName) {
            log.info("$name root repo has not most stars")
        }

        isMain.add(mainName)
    }

    list.forEach {
        if (isMain.contains(it.project)) {
            it.mainRepo = true
        }
    }

    OpenCSVWriter.write(csv.toPath(), list)
}