package ch.uzh.ifi.seal.smr.soa.analysis.values

import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv")
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
        println("\t $value: $count (${percentageString(count, list.size)})")
    }

    println("-----")
}

private fun analyzeMode(items: Set<Result>) {
    val counter = mutableMapOf<Result, Int>()
    count(items, Result::modeIsThroughput, counter)
    count(items, Result::modeIsAverageTime, counter)
    count(items, Result::modeIsSampleTime, counter)
    count(items, Result::modeIsSingleShotTime, counter)

    // default = 1 mode
    val isDefaultMode = items.filter { it.modeIsThroughput == null && it.modeIsAverageTime == null && it.modeIsSampleTime == null && it.modeIsSingleShotTime == null }

    isDefaultMode.forEach {
        counter[it] = counter.getOrDefault(it, 0) + 1
    }

    val exact1Mode = counter.filter { (_, value) -> value == 1 }.map { (key, _) -> key }

    println("~~~mode~~~")
    println("default: ${isDefaultMode.size} (${percentageString(isDefaultMode.size, exact1Mode.size)})")
    analyzeSingleMode(exact1Mode, "throughput", Result::modeIsThroughput)
    analyzeSingleMode(exact1Mode, "averageTime", Result::modeIsAverageTime)
    analyzeSingleMode(exact1Mode, "sampleTime", Result::modeIsSampleTime)
    analyzeSingleMode(exact1Mode, "singleShotTime", Result::modeIsSingleShotTime)
    analyzeModeAll(items)
    analyzeNumberOfModes(counter)
}

private fun analyzeSingleMode(items: List<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val list = items.filter { property.get(it) == true }
    println("$title: ${list.size} (${percentageString(list.size, items.size)})")
}

private fun analyzeModeAll(items: Set<Result>) {
    val list = items.filter {
        it.modeIsThroughput == true && it.modeIsAverageTime == true && it.modeIsSampleTime == true && it.modeIsSingleShotTime == true
    }
    println("all: ${list.size} (${percentageString(list.size, items.size)})")
}

private fun analyzeNumberOfModes(counter: Map<Result, Int>) {
    val grouped = counter.toList().groupingBy { it.second }.eachCount().toList().sortedBy { it.first }

    grouped.forEach { (numberOfModes, occurrences) ->
        println("$numberOfModes mode: $occurrences (${percentageString(occurrences, counter.size)})")
    }
}

private fun count(items: Set<Result>, property: KMutableProperty1<Result, *>, counter: MutableMap<Result, Int>) {
    items.filter { property.get(it) == true }.forEach {
        counter[it] = counter.getOrDefault(it, 0) + 1
    }
}