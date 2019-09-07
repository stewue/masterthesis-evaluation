package ch.uzh.ifi.seal.smr.soa.result.isdefaultvalue

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

fun main() {
    val file = File("D:\\merged-current-commit.csv")
    val items = CsvResultParser(file).getList()
    val hasJmhVersion = items.filter { it.jmhVersion != null }.toSet()

    println("${hasJmhVersion.size} benchmarks has a jmh version")

    analyze(hasJmhVersion, "warmupIterations", Result::warmupIterations, ExecutionConfiguration::warmupIterations)
    analyze(hasJmhVersion, "warmupTime", Result::warmupTime, ExecutionConfiguration::warmupTime)
    analyze(hasJmhVersion, "measurementIterations", Result::measurementIterations, ExecutionConfiguration::measurementIterations)
    analyze(hasJmhVersion, "measurementTime", Result::measurementTime, ExecutionConfiguration::measurementTime)
    analyze(hasJmhVersion, "forks", Result::forks, ExecutionConfiguration::forks)
    analyze(hasJmhVersion, "warmupForks", Result::warmupForks, ExecutionConfiguration::warmupForks)
    analyzeMode(hasJmhVersion)
}

private fun analyze(items: Set<Result>, title: String, property: KMutableProperty1<Result, *>, propertyDefault: KProperty1<ExecutionConfiguration, *>) {
    val hasValue = items.filter { property.get(it) != null }
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

    val pre120 = hasValue.filter { it.jmhVersion!!.compareTo(jmh120) != 1 }
    val pre120Filtered = pre120.filter {
        if (property == Result::warmupTime || property == Result::measurementTime) {
            property.get(it) == 10000000000
        } else {
            property.get(it) == propertyDefault.get(defaultExecConfig(jmh121))
        }
    }
    val post120 = isDefault.filter { it.jmhVersion!!.compareTo(jmh120) == 1 }

    println("~~~$title~~~")
    println("is default in ${isDefault.size} of ${hasValue.size} cases (${percentage(isDefault.size, hasValue.size)})")
    println("is default in ${post120.size} of ${isDefault.size} cases only = 1.21 (${percentage(post120.size, isDefault.size)})")
    println("is new default value in ${pre120Filtered.size} of ${pre120.size} cases < 1.21 (${percentage(pre120Filtered.size, pre120.size)})")
    println("------")
}

private fun analyzeMode(items: Set<Result>) {
    val list = items.filter {
        it.modeIsThroughput == true && it.modeIsAverageTime != true && it.modeIsSampleTime != true && it.modeIsSingleShotTime != true
    }

    val modeUsed = items.filter { it.modeIsThroughput == true }

    println("~~~mode~~~")
    println("in ${list.size} of ${items.size} cases is throughput the only mode (${percentage(list.size, items.size)})")
    val notSingleModeCount = modeUsed.size - list.size
    println("throughput is not single mode in $notSingleModeCount cases (${percentage(notSingleModeCount, modeUsed.size)})")
}

private val jmh120 = JMHVersion(major = 1, minor = 20)
private val jmh121 = JMHVersion(major = 1, minor = 21)