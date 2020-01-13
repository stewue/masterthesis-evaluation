package ch.uzh.ifi.seal.smr.soa.utils.resultismain

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val benchmarkFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged.csv")
    val output = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv").toPath()
    val projects = CsvProjectParser(projectFile).getList()
    val benchmarks = CsvResultParser(benchmarkFile).getList()

    val filtered = benchmarks.filter { benchmark ->
        projects.find {
            benchmark.project == it.project
        }!!.mainRepo == true
    }

    OpenCSVWriter.write(output, filtered)
}