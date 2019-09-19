package ch.uzh.ifi.seal.smr.soa.analysis.hash

import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import ch.uzh.ifi.seal.smr.soa.utils.toGithubName
import java.io.File

private val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
private val projects = CsvProjectParser(projectFile).getList()

fun main() {
    val file = File("D:\\mp\\history-per-project")

    file.walkTopDown().forEach {
        if (it.isFile) {
            val changes = processProject(it)
            val added = changes.filter { it.type == ChangeType.ADDED }
            val updated = changes.filter { it.type == ChangeType.UPDATED }
            val addedSame = changes.filter { it.type == ChangeType.ADDED_SAME }
            val removed = changes.filter { it.type == ChangeType.REMOVED }
            val removedAddedLater = changes.filter { it.type == ChangeType.REMOVED_ADDED_LATER }
            val differentConfig = changes.filter { it.configChanged }

            //println("${it.nameWithoutExtension.toGithubName};${added.size};${updated.size};${addedSame.size};${removed.size};${removedAddedLater.size};${differentConfig.size}")
            analyzeConfigChange(it.nameWithoutExtension.toGithubName, changes)
        }
    }
}

private fun processProject(inputFile: File): List<Change> {
    val items = CsvResultParser(inputFile).getList()

    val timepoints = items.groupBy(Result::commitTime).toList().sortedBy { it.first }

    val lastResults = mutableMapOf<String, Result>()

    val existingBenchmarks = mutableListOf<String>()

    val lastTimepoint = projects.first { it.project == inputFile.nameWithoutExtension.toGithubName }.lastCommit
    val lastTimepointWithExistingBenchmarks = timepoints.last().first

    val changes = mutableListOf<Change>()

    timepoints.forEach { (time, results) ->
        results.forEach { result ->
            val benchmarkName = "${result.className}.${result.benchmarkName}"
            val lastResult = lastResults[benchmarkName]
            val lastBenchmarkHash = lastResult?.methodHash

            if (lastBenchmarkHash == null) {
                changes.add(Change(ChangeType.ADDED, time!!, benchmarkName, false))
                existingBenchmarks.add(benchmarkName)
            } else if (!existingBenchmarks.contains(benchmarkName) && lastBenchmarkHash == result.methodHash) {
                changes.add(Change(ChangeType.ADDED_SAME, time!!, benchmarkName, false))
                existingBenchmarks.add(benchmarkName)
            } else if (lastBenchmarkHash != result.methodHash) {
                val sameConfig = result.sameConfig(lastResult)
                changes.add(Change(ChangeType.UPDATED, time!!, benchmarkName, !sameConfig))
            }

            if (lastTimepoint == time) {
                changes.add(Change(ChangeType.EXISTING, time!!, benchmarkName, false))
            }
            // Add remove change if in last timepoint no benchmark exists
            else if (lastTimepointWithExistingBenchmarks == time) {
                changes.add(Change(ChangeType.REMOVED, time!!, benchmarkName, false))
            }

            lastResults[benchmarkName] = result
        }

        val iterator = existingBenchmarks.iterator()
        while (iterator.hasNext()) {
            val benchmark = iterator.next()

            if (results.filter { "${it.className}.${it.benchmarkName}" == benchmark }.isEmpty()) {
                changes.add(Change(ChangeType.REMOVED, time!!, benchmark, false))
                iterator.remove()
            }
        }
    }

    changes.filter { it.type == ChangeType.REMOVED }.forEach { removedItem ->
        val later = changes.filter { it.commitTime > removedItem.commitTime && it.benchmark == removedItem.benchmark }
        if (later.isNotEmpty()) {
            removedItem.type = ChangeType.REMOVED_ADDED_LATER
        }
    }

    return changes
}

private fun analyzeCodeChange(project: String, changes: List<Change>) {
    val grouped = changes.groupBy { it.benchmark }
    grouped.forEach { (benchmarkName, changeList) ->
        val first = changeList.first()
        if (first.type != ChangeType.ADDED) {
            throw IllegalArgumentException("First change should be always ADDED")
        }

        val last = changeList.last()
        if (last.type != ChangeType.REMOVED && last.type != ChangeType.EXISTING) {
            throw IllegalArgumentException("Last change should be always REMOVED or EXISTING")
        }

        val filtered = changeList.filter { it.type == ChangeType.UPDATED }

        filtered.forEach { change ->
            println("$project;$benchmarkName;${lin(first.commitTime, last.commitTime, change.commitTime)}")
        }
    }

}

private fun analyzeConfigChange(project: String, changes: List<Change>) {
    val grouped = changes.groupBy { it.benchmark }
    grouped.forEach { (benchmarkName, changeList) ->
        val filtered = changeList.filter { it.configChanged }
        if (filtered.isNotEmpty()) {
            println("$project;$benchmarkName;${filtered.size}")
        }
    }
}

private fun lin(min: Int, max: Int, value: Int): Double {
    return (value - min) / (max - min).toDouble()
}