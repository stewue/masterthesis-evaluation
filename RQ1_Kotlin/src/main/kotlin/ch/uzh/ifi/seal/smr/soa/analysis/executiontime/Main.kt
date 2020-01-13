package ch.uzh.ifi.seal.smr.soa.analysis.executiontime

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.onlyModeChanged
import java.io.File

private val output = mutableListOf<ResExecutionTime>()

fun main() {
    val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv").toPath()

    val all = CsvResultParser(resultFile).getList()

    val filtered = all.filter { it.jmhVersion != null }
    filtered.forEach {
        val res = ResExecutionTime(
                project = it.project,
                className = it.className,
                benchmarkName = it.benchmarkName,
                jmhVersion = it.jmhVersion!!,
                executionTimeDefault = getDefaultExecutionTime(it.jmhVersion!!),
                executionTime = it.calcExecutionTime(),
                warmupTimeDefault = getDefaultWarmupTime(it.jmhVersion!!),
                warmupTime = it.calcWarmupTime(),
                measurementTimeDefault = getDefaultMeasurementTime(it.jmhVersion!!),
                measurementTime = it.calcMeasurementTime(),
                onlyModeChanged = it.onlyModeChanged(),
                onlySingleShot = it.onlySingleShot(),
                measurementWarmupRatio = it.calcMeasurementTime() / it.calcWarmupTime(),
                measurementWarmupRatioPerMeasurementFork = it.calcMeasurementTime() / it.calcWarmupTimePerMeasurementFork(),
                hasWarmupForks = it.warmupForks != null && it.warmupForks!! > 0,
                parameterizationCombinations = it.parameterizationCombinations
        )
        output.add(res)
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResExecutionTime::class.java))
}

private fun getDefaultExecutionTime(jmhVersion: JMHVersion): Long {
    return if (jmhVersion.compareTo(jmh120) == 1) {
        500
    } else {
        400
    }
}

private fun getDefaultWarmupTime(jmhVersion: JMHVersion): Long {
    return if (jmhVersion.compareTo(jmh120) == 1) {
        250
    } else {
        200
    }
}

private fun getDefaultMeasurementTime(jmhVersion: JMHVersion): Long {
    return if (jmhVersion.compareTo(jmh120) == 1) {
        250
    } else {
        200
    }
}