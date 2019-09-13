package ch.uzh.ifi.seal.smr.soa.analysis.hash

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File

fun main() {
    val file = File("D:\\mp\\history-per-project")
    file.walkTopDown().forEach {
        if (it.isFile) {
            val changes = processProject(it)
            val added = changes.filter { it.type == ChangeType.ADDED }
            val updated = changes.filter { it.type == ChangeType.UPDATED }
            val addedSame = changes.filter { it.type == ChangeType.ADDED_SAME }
            val addedDifferent = changes.filter { it.type == ChangeType.ADDED_DIFFERENT }
            val removed = changes.filter { it.type == ChangeType.REMOVED }
            val differentConfig = changes.filter { it.configChanged }

            val addedTotal = added.size + addedSame.size
            val updatedTotal = updated.size + addedDifferent.size

            val updateRatio = updatedTotal.toDouble() / addedTotal
            val differentConfigUpdateRatio = if (updatedTotal == 0) {
                0.0
            } else {
                differentConfig.size.toDouble() / updatedTotal
            }

            println("${it.nameWithoutExtension};${added.size};${updated.size};${addedSame.size};${addedDifferent.size};${removed.size};${differentConfig.size};$updateRatio;$differentConfigUpdateRatio")
        }
    }
}

fun processProject(inputFile: File): List<Change> {
    val items = CsvResultParser(inputFile).getList()

    val timepoints = items.groupBy(Result::commitTime).toList().sortedBy { it.first }

    val lastResults = mutableMapOf<String, Result>()

    val existingBenchmarks = mutableListOf<String>()

    val changes = mutableListOf<Change>()

    timepoints.forEach { (time, results) ->
        results.forEach { result ->
            val benchmarkName = result.benchmarkName
            val lastResult = lastResults[benchmarkName]
            val lastBenchmarkHash = lastResult?.methodHash

            if (lastBenchmarkHash == null) {
                changes.add(Change(ChangeType.ADDED, time!!, benchmarkName, false))
                existingBenchmarks.add(benchmarkName)
            } else if (!existingBenchmarks.contains(benchmarkName) && lastBenchmarkHash == result.methodHash) {
                changes.add(Change(ChangeType.ADDED_SAME, time!!, benchmarkName, false))
            } else if (!existingBenchmarks.contains(benchmarkName)) {
                val sameConfig = result.sameConfig(lastResult)
                changes.add(Change(ChangeType.ADDED_DIFFERENT, time!!, benchmarkName, sameConfig))
            } else if (lastBenchmarkHash != result.methodHash) {
                val sameConfig = result.sameConfig(lastResult)
                changes.add(Change(ChangeType.UPDATED, time!!, benchmarkName, sameConfig))
            }

            lastResults[benchmarkName] = result
        }

        val iterator = existingBenchmarks.iterator()
        while (iterator.hasNext()) {
            val benchmark = iterator.next()

            if (results.filter { it.benchmarkName == benchmark }.isEmpty()) {
                changes.add(Change(ChangeType.REMOVED, time!!, benchmark, false))
                iterator.remove()
            }
        }
    }

    return changes
}