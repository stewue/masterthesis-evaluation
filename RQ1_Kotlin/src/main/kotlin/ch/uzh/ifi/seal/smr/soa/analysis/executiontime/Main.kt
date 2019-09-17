package ch.uzh.ifi.seal.smr.soa.analysis.executiontime

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File


private val output = mutableListOf<ResExecutionTime>()

fun main() {
    val resultFile = File("D:\\mp\\current-merged-isMain.csv")
    val projectFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val all = CsvResultParser(resultFile).getList()
    val projects = CsvProjectParser(projectFile).getList()

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
                group = projects.first { i -> it.project == i.project }.getGroup()
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