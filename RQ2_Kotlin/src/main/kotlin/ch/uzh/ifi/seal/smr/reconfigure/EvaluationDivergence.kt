package ch.uzh.ifi.seal.smr.reconfigure

import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.divergence.Divergence
import org.openjdk.jmh.reconfigure.statistics.evaluation.DivergenceEvaluation
import org.openjdk.jmh.runner.Defaults.RECONFIGURE_KLD_THRESHOLD
import java.io.FileWriter
import java.nio.file.Paths

private val outputDivergence = FileWriter(Paths.get(outputDirectory, "outputDivergence.csv").toFile())

fun evalBenchmarkDivergence(key: CsvLineKey, list: Collection<CsvLine>) {
    outputDivergence.append(key.output())
    evaluation(list.map{ it.getHistogramItem() })
    outputDivergence.appendln("")
    outputDivergence.flush()
}

private fun evaluation(list: List<HistogramItem>) {
    val evaluation = DivergenceEvaluation(RECONFIGURE_KLD_THRESHOLD)
    list.groupBy{ it.iteration }.forEach { (iteration, list) ->
        evaluation.addIteration(list)
        evaluation.calculateVariability()
        val currentPValue = evaluation.getPValueOfIteration(iteration)

        outputDivergence.append(";$currentPValue")
    }
}