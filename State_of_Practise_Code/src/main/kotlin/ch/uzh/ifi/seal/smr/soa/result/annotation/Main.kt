package ch.uzh.ifi.seal.smr.soa.result.annotation

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.math.round
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
}

fun analyze(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>, propertyClass: KMutableProperty1<Result, *>, propertyMethod: KMutableProperty1<Result, *>) {
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

fun percentage(above: Int, below: Int): String {
    val percentage = round(10000.0 * above / below) / 100
    return "$percentage%"
}