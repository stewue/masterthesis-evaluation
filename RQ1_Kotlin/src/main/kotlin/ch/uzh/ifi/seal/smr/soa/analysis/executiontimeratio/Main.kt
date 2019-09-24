package ch.uzh.ifi.seal.smr.soa.analysis.executiontimeratio

import ch.uzh.ifi.seal.smr.soa.analysis.executiontime.ResExecutionTime
import ch.uzh.ifi.seal.smr.soa.analysis.percentageString
import ch.uzh.ifi.seal.smr.soa.utils.CsvResExecutionTimeParser
import java.io.File

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\executiontime.csv")
    val all = CsvResExecutionTimeParser(file).getList().filter { !it.onlySingleShot && !it.onlyModeChanged }

    println("All")
    matrixTotal(all)
    println("")
    println("Per Fork")
    matrixMeasurementFork(all)
    println("")
    println("Per Fork if different")
    matrixMeasurementFork(all.filter { it.hasWarmupForks })
}

private fun matrixTotal(all: Collection<ResExecutionTime>) {
    val h = all.filter { it.measurementWarmupRatio > 1 }
    val e = all.filter { it.measurementWarmupRatio == 1.0 }
    val s = all.filter { it.measurementWarmupRatio < 1 }

    println("execution time is proportionally larger: ${h.size} (${percentageString(h.size, all.size)})")
    println("same ratio: ${e.size} (${percentageString(e.size, all.size)})")
    println("warmup time is proportionally larger: ${s.size} (${percentageString(s.size, all.size)})")
}

private fun matrixMeasurementFork(all: Collection<ResExecutionTime>) {
    val h = all.filter { it.measurementWarmupRatioPerMeasurementFork > 1 }
    val e = all.filter { it.measurementWarmupRatioPerMeasurementFork == 1.0 }
    val s = all.filter { it.measurementWarmupRatioPerMeasurementFork < 1 }

    println("execution time is proportionally larger: ${h.size} (${percentageString(h.size, all.size)})")
    println("same ratio: ${e.size} (${percentageString(e.size, all.size)})")
    println("warmup time is proportionally larger: ${s.size} (${percentageString(s.size, all.size)})")
}