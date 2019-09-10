package ch.uzh.ifi.seal.smr.soa.result.method

import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

fun main() {
    val file = File("D:\\merged-current-commit.csv")
    val items = CsvResultParser(file).getList()

    count(items, "paramCount", Result::paramCount)
    hasProperty(items, "blackhole", Result::hasBlackhole)
    hasProperty(items, "control", Result::hasControl)
    hasProperty(items, "benchmarkParams", Result::hasBenchmarkParams)
    hasProperty(items, "iterationParams", Result::hasIterationParams)
    hasProperty(items, "threadParams", Result::hasThreadParams)
    count(items, "jmhParamCount", Result::jmhParamCount)
    count(items, "jmhParamCountOwnClass", Result::jmhParamCountOwnClass)
    count(items, "jmhParamCountArgument", Result::jmhParamCountArgument)
    hasReturnType(items)
    hasReturnTypeOrBlackHole(items)
    count(items, "partOfGroup", Result::partOfGroup)
}

private fun count(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val grouped = items.groupingBy(property).eachCount().toList().sortedWith(compareBy {
        it.first as Comparable<*>
    })

    println("~~~$title~~~")
    grouped.forEach { (value, count) ->
        println("$value -> $count (${percentage(count, items.size)})")
    }
    println("-----")
}

private fun hasProperty(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val list = items.filter { property.get(it) == true }

    println("~~~$title~~~")
    println("${list.size} of ${items.size} (${percentage(list.size, items.size)}) use a $title")
    println("-----")
}

private fun hasReturnType(items: Set<Result>) {
    val list = items.filter { !it.returnType.isNullOrBlank() }

    println("~~~hasReturnType~~~")
    println("${list.size} of ${items.size} (${percentage(list.size, items.size)})")
    println("-----")
}

private fun hasReturnTypeOrBlackHole(items: Set<Result>) {
    val list = items.filter { !it.returnType.isNullOrBlank() || it.hasBlackhole }

    println("~~~hasReturnTypeOrBlackHole~~~")
    println("${list.size} of ${items.size} (${percentage(list.size, items.size)})")
    println("-----")
}