package ch.uzh.ifi.seal.smr.soa.result.annotation

import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

fun main() {
    val file = File("D:\\merged-current-commit.csv")
    val items = CsvResultParser(file).getList()

    analyze(items, "warmupIterations", Result::warmupIterations, Result::warmupIterationsClass, Result::warmupIterationsMethod)
    analyze(items, "warmupTime", Result::warmupTime, Result::warmupTimeClass, Result::warmupTimeMethod)
    analyze(items, "measurementIterations", Result::measurementIterations, Result::measurementIterationsClass, Result::measurementIterationsMethod)
    analyze(items, "measurementTime", Result::measurementTime, Result::measurementTimeClass, Result::measurementTimeMethod)
    analyze(items, "forks", Result::forks, Result::forksClass, Result::forksMethod)
    analyze(items, "warmupForks", Result::warmupForks, Result::warmupForksClass, Result::warmupForksMethod)
    analyzeMode(items)
}

private fun analyze(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>, propertyClass: KMutableProperty1<Result, *>, propertyMethod: KMutableProperty1<Result, *>) {
    val list = items.filter { property.get(it) != null }
    val listClass = items.filter { propertyClass.get(it) != null && propertyMethod.get(it) == null }
    val listMethod = items.filter { propertyMethod.get(it) != null && propertyClass.get(it) == null }
    val listBoth = items.filter { propertyMethod.get(it) != null && propertyClass.get(it) != null }

    println("~~~$title (${percentage(list.size, items.size)})~~~")
    println("only Class -> ${listClass.size} (${percentage(listClass.size, list.size)})")
    println("only Method -> ${listMethod.size} (${percentage(listMethod.size, list.size)})")
    println("Both -> ${listBoth.size} (${percentage(listBoth.size, list.size)})")
    listBoth.forEach {
        println("\tmethod: ${propertyMethod.get(it)} & class: ${propertyClass.get(it)}")
    }
    println("-----")
}

private fun analyzeMode(items: Set<Result>) {
    val notDefault = items.filter {
        (it.modeIsThroughput != null && it.modeIsThroughput != false) ||
                (it.modeIsAverageTime != null && it.modeIsAverageTime != false) ||
                (it.modeIsSampleTime != null && it.modeIsSampleTime != false) ||
                (it.modeIsSingleShotTime != null && it.modeIsSingleShotTime != false)
    }.toSet()
    println("~~~mode (${percentage(items.size - notDefault.size, items.size)})~~~")
    analyzeSingleMode(notDefault, "throughput", Result::modeIsThroughput, Result::modeIsThroughputClass, Result::modeIsThroughputMethod)
    analyzeSingleMode(notDefault, "average", Result::modeIsAverageTime, Result::modeIsAverageTimeClass, Result::modeIsAverageTimeMethod)
    analyzeSingleMode(notDefault, "sample", Result::modeIsSampleTime, Result::modeIsSampleTimeClass, Result::modeIsSampleTimeMethod)
    analyzeSingleMode(notDefault, "singleshot", Result::modeIsSingleShotTime, Result::modeIsSingleShotTimeClass, Result::modeIsSingleShotTimeMethod)
}

private fun analyzeSingleMode(items: Set<Result>, title: String, p: KMutableProperty1<Result, *>, pc: KMutableProperty1<Result, *>, pm: KMutableProperty1<Result, *>) {
    val list = items.filter { p.get(it) != null && p.get(it) == true }
    val listClass = items.filter { pc.get(it) != null && pc.get(it) == true && pm.get(it) == null }
    val listMethod = items.filter { pm.get(it) != null && pm.get(it) == true && pc.get(it) == null }
    val listBothSame = items.filter { pm.get(it) != null && pm.get(it) == true && pc.get(it) != null && pc.get(it) == true }
    val listBothDifferent = items.filter { pm.get(it) != null && pc.get(it) != null && pc.get(it) != pm.get(it) }

    println("$title (${percentage(list.size, items.size)})")
    println("\tonly Class -> ${listClass.size} (${percentage(listClass.size, list.size)})")
    println("\tonly Method -> ${listMethod.size} (${percentage(listMethod.size, list.size)})")
    println("\tBoth (same value) -> ${listBothSame.size} (${percentage(listBothSame.size, list.size)})")
    println("\tBoth (different value) -> ${listBothDifferent.size} (${percentage(listBothDifferent.size, list.size)})")
}