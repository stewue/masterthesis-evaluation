package ch.uzh.ifi.seal.smr.soa.result.features

import ch.uzh.ifi.seal.smr.soa.result.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

private val output = mutableListOf<ResKeyValue>()

fun main() {
    val file = File("D:\\mp\\current-merged-isMain.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()
    val all = CsvResultParser(file).getList()

    val noMethodArgument = returnCountMap(all, Result::paramCount)[0] ?: 0
    output.add(ResKeyValue("Benchmark has no method argument", "$noMethodArgument (${percentageString(noMethodArgument, all.size)})"))

    hasProperty(all, "blackhole", Result::hasBlackhole)
    hasProperty(all, "control", Result::hasControl)
    hasProperty(all, "benchmarkParams", Result::hasBenchmarkParams)
    hasProperty(all, "iterationParams", Result::hasIterationParams)
    hasProperty(all, "threadParams", Result::hasThreadParams)
    jmhParams(all)
    hasReturnType(all)
    hasReturnTypeOrBlackHole(all)

    val hasGroup = returnCountMap(all, Result::partOfGroup)[true] ?: 0
    output.add(ResKeyValue("Benchmark is part of a group", "$hasGroup (${percentageString(hasGroup, all.size)})"))

    val hasNoParametrization = returnCountMap(all, Result::parametrizationCombinations)[1] ?: 0
    val hasParametrization = all.size - hasNoParametrization
    output.add(ResKeyValue("Benchmark has parametrization", "$hasParametrization (${percentageString(hasParametrization, all.size)})"))

    params(all)

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResKeyValue::class.java))
}

private fun returnCountMap(all: Set<Result>, property: KMutableProperty1<Result, *>): Map<Any?, Int> {
    return all.groupingBy(property).eachCount().toList().sortedWith(compareBy {
        it.first as Comparable<*>
    }).toMap()
}

private fun count(all: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val grouped = all.groupingBy(property).eachCount().toList().sortedWith(compareBy {
        it.first as Comparable<*>
    })

    println("~~~$title~~~")
    grouped.forEach { (value, count) ->
        println("$value -> $count (${percentageString(count, all.size)})")
    }
    println("-----")
}

private fun hasProperty(all: Set<Result>, title: String, property: KMutableProperty1<Result, *>) {
    val list = all.filter { property.get(it) == true }

    output.add(ResKeyValue(title, "${list.size} of ${all.size} (${percentageString(list.size, all.size)})"))
}

private fun hasReturnType(all: Set<Result>) {
    val list = all.filter { !it.returnType.isNullOrBlank() }

    output.add(ResKeyValue("Benchmark has return type", "${list.size} of ${all.size} (${percentageString(list.size, all.size)})"))
}

private fun hasReturnTypeOrBlackHole(all: Set<Result>) {
    val list = all.filter { !it.returnType.isNullOrBlank() || it.hasBlackhole }

    output.add(ResKeyValue("Benchmark has return type or a blackhole", "${list.size} of ${all.size} (${percentageString(list.size, all.size)})"))
}

private fun jmhParams(all: Set<Result>) {
    val hasNoJmhParams = returnCountMap(all, Result::jmhParamCount)[0] ?: 0
    output.add(ResKeyValue("Benchmark has no jmh params", "$hasNoJmhParams (${percentageString(hasNoJmhParams, all.size)})"))

    val jmhParamsCleaned = all.filter { it.jmhParamCount == it.jmhParamCountOwnClass + it.jmhParamCountArgument }
    val jmhParamsFailed = all.filter { it.jmhParamCount != it.jmhParamCountOwnClass + it.jmhParamCountArgument }.size

    val jmhParamsDefinedInClass = jmhParamsCleaned.map { it.jmhParamCountOwnClass }.sum()
    val jmhParamsDefinedExternal = jmhParamsCleaned.map { it.jmhParamCountArgument }.sum()
    val jmhParamsTotal = jmhParamsDefinedInClass + jmhParamsDefinedExternal
    output.add(ResKeyValue("$jmhParamsTotal JmhParams used", "$jmhParamsDefinedInClass (${percentageString(jmhParamsDefinedInClass, jmhParamsTotal)}) defined in own class, $jmhParamsDefinedExternal (${percentageString(jmhParamsDefinedExternal, jmhParamsTotal)}) defined external & $jmhParamsFailed uses own class as function argument"))

}

private fun params(all: Set<Result>) {
    val numberOfStateObjectsWithJmhParams = all.map { it.paramCountStateObject - it.paramCountStateObjectWithoutJmhParam }.sum()
    val numberOfStateObjectsWithoutJmhParams = all.map { it.paramCountStateObjectWithoutJmhParam }.sum()
    val totalStateObjects = all.map { it.paramCountStateObject }.sum()
    output.add(ResKeyValue("$totalStateObjects stateObjects are used", "$numberOfStateObjectsWithJmhParams uses one or more jmhParams and $numberOfStateObjectsWithoutJmhParams has no jmh param"))

    val hasStateObject = all.filter { it.paramCountStateObject > 0 }.size

    output.add(ResKeyValue("state object usage", "$hasStateObject (${percentageString(hasStateObject, all.size)}) benchmarks use a state object"))

    val countBlackhole = all.filter { it.hasBlackhole }.size
    val countControl = all.filter { it.hasControl }.size
    val countBenchmarkParams = all.filter { it.hasBenchmarkParams }.size
    val countIterationParams = all.filter { it.hasIterationParams }.size
    val countThreadParams = all.filter { it.hasThreadParams }.size
    val totalArguments = all.map { it.paramCount }.sum()
    val unresolved = totalArguments - numberOfStateObjectsWithJmhParams - numberOfStateObjectsWithoutJmhParams - countBlackhole - countControl - countBenchmarkParams - countIterationParams - countThreadParams
    output.add(ResKeyValue("$totalArguments method arguments are used", "$unresolved cannot be resolved ${percentageString(unresolved, totalArguments)}"))
}