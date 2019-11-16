package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLine
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem.Companion.header
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.evaluation.CiPercentageEvaluation
import org.openjdk.jmh.runner.Defaults
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

class CiPercentage(outputDir: String) {
    private val outputCi = FileWriter(Paths.get(outputDir, "outputCiSingle.csv").toFile())

    fun generateHeader() {
        outputCi.append(header)
    }

    fun run(key: CsvLineKey, list: Collection<CsvLine>) {
        outputCi.append(key.output())
        evaluation(list)
        outputCi.appendln("")
        outputCi.flush()
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
            val evaluation = CiPercentageEvaluation(Defaults.RECONFIGURE_CI_THRESHOLD)
            map.forEach { (iteration, list) ->
                evaluation.addIteration(list)
                evaluation.calculateVariability()
                val currentCiPercentage = evaluation.getCiPercentageOfIteration(iteration)

                outputCi.append(";$currentCiPercentage")
                deleteTmp()
            }
        }
    }

    private fun deleteTmp() {
        val tmp = File(System.getProperty("java.io.tmpdir"))
        tmp.listFiles().forEach {
            if (it.name.startsWith("reconfigure")) {
                it.delete()
            }
        }
    }
}