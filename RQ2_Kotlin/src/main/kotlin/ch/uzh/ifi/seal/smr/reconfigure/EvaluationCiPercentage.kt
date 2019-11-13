package ch.uzh.ifi.seal.smr.reconfigure

import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.evaluation.CiPercentageEvaluation
import org.openjdk.jmh.runner.Defaults
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

private val outputCi = FileWriter(Paths.get(outputDirectory, "outputCi.csv").toFile())

fun evalBenchmarkCiPercentage(key: CsvLineKey, list: Collection<CsvLine>) {
    outputCi.append(key.output())
    evaluation(list)
    outputCi.appendln("")
    outputCi.flush()
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

    val evaluation = CiPercentageEvaluation(Defaults.RECONFIGURE_CI_THRESHOLD)

    histogram.forEach { (iteration, list) ->
        evaluation.addIteration(list)
        evaluation.calculateVariability()
        val currentCiPercentage = evaluation.getCiPercentageOfIteration(iteration)

        outputCi.append(";$currentCiPercentage")
        deleteTmp()
    }
}

private fun deleteTmp(){
    val tmp = File(System.getProperty("java.io.tmpdir"))
    tmp.listFiles().forEach {
        if(it.name.startsWith("reconfigure")){
            it.delete()
        }
    }
}