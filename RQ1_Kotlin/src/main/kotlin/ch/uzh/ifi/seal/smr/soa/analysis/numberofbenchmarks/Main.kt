package ch.uzh.ifi.seal.smr.soa.analysis.numberofbenchmarks

import ch.uzh.ifi.seal.smr.soa.analysis.yearInSeconds
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

private val output = mutableListOf<ResNumberOfBenchmarks>()

fun main() {
    val fileProjects = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\numberofbenchmarks.csv").toPath()
    val projects = CsvProjectParser(fileProjects).getList()

    projects.filter { it.mainRepo == true }
            .forEach {
                output.add(ResNumberOfBenchmarks(it.project, it.numberOfBenchmarks!!))
            }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResNumberOfBenchmarks::class.java))
}