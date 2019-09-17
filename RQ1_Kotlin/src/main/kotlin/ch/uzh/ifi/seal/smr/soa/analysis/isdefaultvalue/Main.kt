package ch.uzh.ifi.seal.smr.soa.analysis.isdefaultvalue

import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.analysis.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

private val output = mutableListOf<ResIsDefault>()

fun main() {
    val file = File("D:\\mp\\current-merged-isMain.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val all = CsvResultParser(file).getList().filter { it.jmhVersion != null }.toSet()

    output.add(ResIsDefault("${all.size} benchmarks has a jmh version"))

    analyze(all, "warmupIterations", Result::warmupIterations, ExecutionConfiguration::warmupIterations)
    analyze(all, "warmupTime", Result::warmupTime, ExecutionConfiguration::warmupTime)
    analyze(all, "measurementIterations", Result::measurementIterations, ExecutionConfiguration::measurementIterations)
    analyze(all, "measurementTime", Result::measurementTime, ExecutionConfiguration::measurementTime)
    analyze(all, "forks", Result::forks, ExecutionConfiguration::forks)
    analyze(all, "warmupForks", Result::warmupForks, ExecutionConfiguration::warmupForks)
    analyzeMode(all)

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResIsDefault::class.java))
}

private fun analyze(all: Set<Result>, title: String, property: KMutableProperty1<Result, *>, propertyDefault: KProperty1<ExecutionConfiguration, *>) {
    val hasValue = all.filter { property.get(it) != null }
    val isDefault = hasValue.filter {
        if (property == Result::warmupTime || property == Result::measurementTime) {
            if (it.jmhVersion!!.compareTo(jmh120) == 1) {
                property.get(it) == 10000000000
            } else {
                property.get(it) == 1000000000
            }
        } else {
            property.get(it) == propertyDefault.get(defaultExecConfig(it.jmhVersion!!))
        }
    }

    val post120 = isDefault.filter { it.jmhVersion!!.compareTo(jmh120) == 1 }

    output.add(ResIsDefault(
            name = title,
            annotationPresent = hasValue.size,
            isDefaultValue = isDefault.size,
            isDefaultValuePercentage = percentage(isDefault.size, hasValue.size),
            isDefaultValueIn121 = post120.size,
            isDefaultValueIn121Percentage = percentage(post120.size, isDefault.size)
    ))
}

private fun analyzeMode(all: Set<Result>) {
    val someModeSet = all.filter {
        it.modeIsThroughput == true || it.modeIsAverageTime == true || it.modeIsSampleTime == true || it.modeIsSingleShotTime == true

    }

    val onlyThrptUsed = all.filter {
        it.modeIsThroughput == true && it.modeIsAverageTime != true && it.modeIsSampleTime != true && it.modeIsSingleShotTime != true
    }

    output.add(ResIsDefault(
            name = "mode",
            annotationPresent = someModeSet.size,
            isDefaultValue = onlyThrptUsed.size,
            isDefaultValuePercentage = percentage(onlyThrptUsed.size, someModeSet.size)
    ))
}