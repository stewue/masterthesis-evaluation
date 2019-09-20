package ch.uzh.ifi.seal.smr.soa.analysis.valuecorrelation

import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File

fun main() {
    val resultFile = File("D:\\mp\\corr3.csv")
    val all = CsvResultParser(resultFile).getList()

    all.filter { it.jmhVersion != null && !it.nothingSet() }.forEach {
        if (it.warmupIterations == null) {
            it.warmupIterations = defaultExecConfig(it.jmhVersion!!).warmupIterations
        }

        if (it.warmupTime == null) {
            it.warmupTime = if (it.jmhVersion!!.compareTo(jmh120) == 1) {
                10000000000
            } else {
                1000000000
            }
        }

        if (it.measurementIterations == null) {
            it.measurementIterations = defaultExecConfig(it.jmhVersion!!).measurementIterations
        }

        if (it.measurementTime == null) {
            it.measurementTime = if (it.jmhVersion!!.compareTo(jmh120) == 1) {
                10000000000
            } else {
                1000000000
            }
        }

        if (it.forks == null) {
            it.forks = defaultExecConfig(it.jmhVersion!!).forks
        }

        if (it.warmupForks == null) {
            it.warmupForks = defaultExecConfig(it.jmhVersion!!).warmupForks
        }
    }

    OpenCSVWriter.write(resultFile.toPath(), all, CustomMappingStrategy(Result::class.java))
}