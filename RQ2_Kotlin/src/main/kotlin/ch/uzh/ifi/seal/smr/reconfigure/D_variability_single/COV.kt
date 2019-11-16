package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLine
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem.Companion.header
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.evaluation.CovEvaluation
import org.openjdk.jmh.runner.Defaults.RECONFIGURE_COV_THRESHOLD
import java.io.FileWriter
import java.nio.file.Paths

class COV(outputDir: String) {
    private val outputCov = FileWriter(Paths.get(outputDir, "outputCovSingle.csv").toFile())

    fun generateHeader() {
        outputCov.append(header)
    }

    fun run(key: CsvLineKey, list: Collection<CsvLine>) {
        outputCov.append(key.output())
        evaluation(list)
        outputCov.appendln("")
        outputCov.flush()
    }

    private fun evaluation(list: Collection<CsvLine>) {
        val histogram = mutableMapOf<Int, MutableMap<Int, MutableList<HistogramItem>>>()

        list.forEach {
            if (histogram[it.fork] == null) {
                histogram[it.fork] = mutableMapOf()
            }

            if (histogram.getValue(it.fork)[it.iteration] == null) {
                histogram.getValue(it.fork)[it.iteration] = mutableListOf()
            }

            val iterationList = histogram.getValue(it.fork).getValue(it.iteration)
            iterationList.add(it.getHistogramItem())
        }

        histogram.forEach { (_, map) ->
            val evaluation = CovEvaluation(RECONFIGURE_COV_THRESHOLD)
            map.forEach { (iteration, list) ->
                evaluation.addIteration(list)
                evaluation.calculateVariability()
                val currentCov = evaluation.getCovOfIteration(iteration)

                outputCov.append(";$currentCov")
            }
        }
    }
}