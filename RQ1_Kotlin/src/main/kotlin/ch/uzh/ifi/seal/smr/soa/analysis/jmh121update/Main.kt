package ch.uzh.ifi.seal.smr.soa.analysis.jmh121update

import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.analysis.jmh121
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.toSecond
import java.io.File

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val output = mutableListOf<ResJmh121Update>()

    val grouped = items.groupBy { Triple(it.project, it.className, it.benchmarkName) }

    val defaultExecConfig120 = defaultExecConfig(jmh120)
    val defaultExecConfig121 = defaultExecConfig(jmh121)

    grouped.forEach { (triple, list) ->
        val first121 = list.lastOrNull { it.jmhVersion != null && it.jmhVersion!!.compareTo(jmh120) == 1 }
        val firstPre121 = list.lastOrNull { it.jmhVersion != null && it.jmhVersion!!.compareTo(jmh120) < 1 }

        if (first121 != null && firstPre121 != null) {
            val sameConfig = first121.sameConfigWithoutMode(firstPre121)
            if (sameConfig) {
                output.add(ResJmh121Update(triple.first, triple.second, triple.third, sameConfig))
            } else {
                val warmupIterationsOldDefault = first121.warmupIterations == defaultExecConfig120.warmupIterations
                val warmupTimeOldDefault = first121.warmupTime != null && first121.warmupTime!!.toSecond() == 1.0
                val measurementIterationsOldDefault = first121.measurementIterations == defaultExecConfig120.measurementIterations
                val measurementTimeOldDefault = first121.measurementTime != null && first121.measurementTime!!.toSecond() == 1.0
                val forksOldDefault = first121.forks == defaultExecConfig120.forks

                val warmupIterationsChanged = first121.warmupIterations != firstPre121.warmupIterations
                val warmupTimeChanged = first121.warmupTime != firstPre121.warmupTime
                val measurementIterationsChanged = first121.measurementIterations != firstPre121.measurementIterations
                val measurementTimeChanged = first121.measurementTime != firstPre121.measurementTime
                val forksChanged = first121.forks != firstPre121.forks

                val warmupIterationsCombined = warmupIterationsOldDefault && warmupIterationsChanged
                val warmupTimeCombined = warmupTimeOldDefault && warmupTimeChanged
                val measurementIterationsCombined = measurementIterationsOldDefault && measurementIterationsChanged
                val measurementTimeCombined = measurementTimeOldDefault && measurementTimeChanged
                val forksCombined = forksOldDefault && forksChanged
                val iterationsCombined = warmupIterationsCombined || measurementIterationsCombined
                val timeCombined = warmupTimeCombined || measurementTimeCombined

                val warmupIterationsNullChanged = first121.warmupIterations != null && firstPre121.warmupIterations == null
                val warmupTimeNullChanged = first121.warmupTime != null && firstPre121.warmupTime == null
                val measurementIterationsNullChanged = first121.measurementIterations != null && firstPre121.measurementIterations == null
                val measurementTimeNullChanged = first121.measurementTime != null && firstPre121.measurementTime == null
                val forksNullChanged = first121.forks != null && firstPre121.forks == null

                val warmupIterationsDifference = if (warmupIterationsChanged) {
                    val pre = firstPre121.warmupIterations ?: defaultExecConfig120.warmupIterations
                    val post = first121.warmupIterations ?: defaultExecConfig121.warmupIterations
                    post - pre
                } else {
                    null
                }
                val warmupTimeDifference = if (warmupTimeChanged) {
                    val pre = firstPre121.warmupTime?.toSecond() ?: 1.0
                    val post = first121.warmupTime?.toSecond() ?: 10.0
                    post - pre
                } else {
                    null
                }
                val measurementIterationsDifference = if (measurementIterationsChanged) {
                    val pre = firstPre121.measurementIterations ?: defaultExecConfig120.measurementIterations
                    val post = first121.measurementIterations ?: defaultExecConfig121.measurementIterations
                    post - pre
                } else {
                    null
                }
                val measurementTimeDifference = if (measurementIterationsChanged) {
                    val pre = firstPre121.measurementTime?.toSecond() ?: 1.0
                    val post = first121.measurementTime?.toSecond() ?: 10.0
                    post - pre
                } else {
                    null
                }
                val forksDifference = if (forksChanged) {
                    val pre = firstPre121.forks ?: defaultExecConfig120.forks
                    val post = first121.forks ?: defaultExecConfig121.forks
                    post - pre
                } else {
                    null
                }

                output.add(ResJmh121Update(triple.first, triple.second, triple.third, sameConfig, warmupIterationsCombined, warmupTimeCombined, measurementIterationsCombined, measurementTimeCombined, forksCombined, iterationsCombined, timeCombined, warmupIterationsNullChanged, warmupTimeNullChanged, measurementIterationsNullChanged, measurementTimeNullChanged, forksNullChanged, warmupIterationsDifference, warmupTimeDifference, measurementIterationsDifference, measurementTimeDifference, forksDifference, firstPre121.warmupIterations, firstPre121.warmupTime?.toSecond(), firstPre121.measurementIterations, firstPre121.measurementTime?.toSecond(), firstPre121.forks, first121.warmupIterations, first121.warmupTime?.toSecond(), first121.measurementIterations, first121.measurementTime?.toSecond(), first121.forks))
            }
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJmh121Update::class.java))
}