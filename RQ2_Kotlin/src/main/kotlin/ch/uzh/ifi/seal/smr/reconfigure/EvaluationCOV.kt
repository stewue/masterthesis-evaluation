package ch.uzh.ifi.seal.smr.reconfigure

import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.evaluation.CovEvaluation
import org.openjdk.jmh.runner.Defaults.RECONFIGURE_COV_THRESHOLD
import java.io.FileWriter
import java.nio.file.Paths

private val outputCovChange = FileWriter(Paths.get(outputDirectory, "outputCovChange.csv").toFile())
private val outputCov = FileWriter(Paths.get(outputDirectory, "outputCov.csv").toFile())

fun evalBenchmarkCOV(key: CsvLineKey, list: Collection<CsvLine>) {
    outputCovChange.append(key.output())
    outputCov.append(key.output())
    evaluation(list)
    outputCovChange.appendln("")
    outputCov.appendln("")
    outputCovChange.flush()
    outputCov.flush()
}

private fun evaluation(list: Collection<CsvLine>) {
    val histogram = mutableMapOf<Int, MutableList<HistogramItem>>()

    list.forEach {
        if (histogram[it.iteration] == null) {
            histogram[it.iteration] = mutableListOf()
        }

        val iterationList = histogram.getValue(it.iteration)
        iterationList.add(it.getHistogramItem())
    }

    val evaluation = CovEvaluation(RECONFIGURE_COV_THRESHOLD)

    histogram.forEach { (iteration, list) ->
        evaluation.addIteration(list)
        val delta = evaluation.calculateVariability()
        val currentCov = evaluation.getCovOfIteration(iteration)

        outputCov.append(";$currentCov")
        outputCovChange.append(";$delta")
    }
}