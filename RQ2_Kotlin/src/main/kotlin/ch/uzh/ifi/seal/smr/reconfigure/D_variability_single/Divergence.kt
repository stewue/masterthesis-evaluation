package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLine
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem.Companion.header
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.evaluation.DivergenceEvaluation
import org.openjdk.jmh.runner.Defaults.RECONFIGURE_KLD_THRESHOLD
import java.io.FileWriter
import java.nio.file.Paths

class Divergence(outputDir: String) {
    private val outputDivergence = FileWriter(Paths.get(outputDir, "outputDivergenceSingle.csv").toFile())

    fun generateHeader() {
        outputDivergence.append(header)
    }


    fun run(key: CsvLineKey, list: Collection<CsvLine>) {
        outputDivergence.append(key.output())
        evaluation(list.map { it.getHistogramItem() })
        outputDivergence.appendln("")
        outputDivergence.flush()
    }

    private fun evaluation(list: List<HistogramItem>) {
        list.groupBy { it.fork }.forEach { (_, iterationList) ->
            val evaluation = DivergenceEvaluation(RECONFIGURE_KLD_THRESHOLD)
            iterationList.groupBy { it.iteration }.forEach { (iteration, list) ->
                evaluation.addIteration(list)
                evaluation.calculateVariability()
                val currentPValue = evaluation.getPValueOfIteration(iteration)

                if (currentPValue == null) {
                    outputDivergence.append(";")
                } else {
                    outputDivergence.append(";$currentPValue")
                }
            }
        }
    }
}