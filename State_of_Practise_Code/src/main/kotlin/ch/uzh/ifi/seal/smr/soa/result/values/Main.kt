package ch.uzh.ifi.seal.smr.soa.result.values

import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

fun main() {
    val file = File("D:\\merged-current-commit.csv")
    val items = CsvResultParser(file).getList()

    analyze(items, "warmupIterations", Result::warmupIterations)
    analyze(items, "warmupTime", Result::warmupTime)
    analyze(items, "measurementIterations", Result::measurementIterations)
    analyze(items, "measurementTime", Result::measurementTime)
    analyze(items, "forks", Result::forks)
    analyze(items, "warmupForks", Result::warmupForks)
    analyzeMode(items)
}

private fun analyze(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val list = items.filter { property.get(it) != null }
    val grouped = list.groupingBy(property).eachCount().toList().sortedWith(compareBy {
        it.second
    }).asReversed()

    println("~~~$title~~~")
    grouped.forEach { (value, count) ->
        println("\t $value: $count (${percentage(count, list.size)})")
    }

    println("-----")
}

private fun analyzeMode(items: Set<Result>) {
    println("~~~mode~~~")
    analyzeSingleMode(items, "throughput", Result::modeIsThroughput)
    analyzeSingleMode(items, "averageTime", Result::modeIsAverageTime)
    analyzeSingleMode(items, "sampleTime", Result::modeIsSampleTime)
    analyzeSingleMode(items, "singleShotTime", Result::modeIsSingleShotTime)
    analyzeModeAll(items)
    analyzeNumberOfModes(items)
}

private fun analyzeSingleMode(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val list = items.filter { property.get(it) != null && property.get(it) == true }
    println("$title: ${list.size} (${percentage(list.size, items.size)})")
}

private fun analyzeModeAll(items: Set<Result>) {
    val list = items.filter {
        it.modeIsThroughput != null && it.modeIsThroughput == true &&
                it.modeIsAverageTime != null && it.modeIsAverageTime == true &&
                it.modeIsSampleTime != null && it.modeIsSampleTime == true &&
                it.modeIsSingleShotTime != null && it.modeIsSingleShotTime == true
    }
    println("all: ${list.size} (${percentage(list.size, items.size)})")
}

private fun analyzeNumberOfModes(items: Set<Result>) {
    val counter = mutableMapOf<Result, Int>()
    count(items, Result::modeIsThroughput, counter)
    count(items, Result::modeIsAverageTime, counter)
    count(items, Result::modeIsSampleTime, counter)
    count(items, Result::modeIsSingleShotTime, counter)

    val grouped = counter.toList().groupingBy { it.second }.eachCount().toList().sortedBy { it.first }

    grouped.forEach { (numberOfModes, occurrences) ->
        println("$numberOfModes mode: $occurrences (${percentage(occurrences, counter.size)})")
    }
}

private fun count(items: Set<Result>, property: KMutableProperty1<Result, *>, counter: MutableMap<Result, Int>) {
    items.filter { property.get(it) != null && property.get(it) == true }.forEach {
        counter[it] = counter.getOrDefault(it, 0) + 1
    }
}