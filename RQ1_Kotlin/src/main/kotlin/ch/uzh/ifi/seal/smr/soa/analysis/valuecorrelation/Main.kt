package ch.uzh.ifi.seal.smr.soa.analysis.valuecorrelation

import ch.uzh.ifi.seal.bencher.execution.defaultExecConfig
import ch.uzh.ifi.seal.smr.soa.analysis.jmh120
import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File

fun main() {
    val inputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv")
    val resultFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\valuecorrelation.csv").toPath()

    val all = CsvResultParser(inputFile).getList()

    val mapped = onlySetValues(all)

    OpenCSVWriter.write(resultFile, mapped, CustomMappingStrategy(ResValueCorrelation::class.java))
}

private fun onlySetValues(all: Set<Result>): List<ResValueCorrelation> {
    return all.filter { it.jmhVersion != null && !it.nothingSet() }.map {
        ResValueCorrelation(it.warmupIterations, it.warmupTime, it.measurementIterations, it.measurementTime, it.forks, it.warmupForks)
    }
}

private fun alsoDefaultValues(all: Set<Result>): List<ResValueCorrelation> {
    return all.filter { it.jmhVersion != null && !it.nothingSet() }.map {
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

        ResValueCorrelation(it.warmupIterations, it.warmupTime, it.measurementIterations, it.measurementTime, it.forks, it.warmupForks)
    }
}

