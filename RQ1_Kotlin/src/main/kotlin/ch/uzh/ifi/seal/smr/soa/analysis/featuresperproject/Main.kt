package ch.uzh.ifi.seal.smr.soa.analysis.featuresperproject

import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File

private val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
private val projects = CsvProjectParser(projectFile).getList()
private val output = mutableListOf<ResFeaturesProject>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\featuresperproject.csv").toPath()
    val all = CsvResultParser(file).getList()

    val grouped = all.groupBy { it.project }
    grouped.forEach {
        processProject(it.key, it.value)
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResFeaturesProject::class.java))
}

private fun processProject(project: String, list: List<Result>) {
    val p = projects.first { it.project == project }
    val numberOfBenchmarks = list.size
    output.add(ResFeaturesProject(
            project = project,
            numberOfBenchmarks = numberOfBenchmarks,
            avgNumberOfBenchmarksPerClass = numberOfBenchmarks / list.groupBy { it.className }.count().toDouble(),
            avgNumberOfBenchmarksPerFile = numberOfBenchmarks / list.groupBy { it.className.substringBefore("$") }.count().toDouble(),
            parameterizationUsedPercentage = list.map {
                if (it.parameterizationCombinations > 1) {
                    1
                } else {
                    0
                }
            }.sum().toDouble() / numberOfBenchmarks,
            groupsUsedPercentage = list.map {
                if (it.partOfGroup) {
                    1
                } else {
                    0
                }
            }.sum().toDouble() / numberOfBenchmarks,
            blackholeUsedPercentage = list.map {
                if (it.hasBlackhole) {
                    1
                } else {
                    0
                }
            }.sum().toDouble() / numberOfBenchmarks,
            controlUsedPercentage = list.map {
                if (it.hasControl) {
                    1
                } else {
                    0
                }
            }.sum().toDouble() / numberOfBenchmarks,
            hasStateObjectsWithJmhParamsPercentage = list.filter { it.paramCountStateObject - it.paramCountStateObjectWithoutJmhParam > 0 }.size.toDouble() / numberOfBenchmarks,
            hasStateObjectsWithoutJmhParamsPercentage = list.filter { it.paramCountStateObjectWithoutJmhParam > 0 }.size.toDouble() / numberOfBenchmarks,
            returnTypeUsedPercentage = list.filter { !it.returnType.isNullOrBlank() }.size.toDouble() / numberOfBenchmarks,
            returnTypeOrBlackholeUsedPercentage = list.filter { !it.returnType.isNullOrBlank() || it.hasBlackhole }.size.toDouble() / numberOfBenchmarks,
            nothingSetPercentage = list.nothingSet().size.toDouble() / numberOfBenchmarks,
            benchmarkIsInnerClassPercentage = list.filter { it.className.contains("$") }.size.toDouble() / numberOfBenchmarks,
            stars = p.stars,
            forks = p.forks,
            watchers = p.watchers,
            numberOfCommits = p.numberOfCommits,
            numberOfContributors = p.numberOfContributors
    ))
}