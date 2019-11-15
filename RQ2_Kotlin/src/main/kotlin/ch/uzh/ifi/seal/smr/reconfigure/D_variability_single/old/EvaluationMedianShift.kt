package ch.uzh.ifi.seal.smr.reconfigure.D_variability_single.old

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLine
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineKey
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.SAMPLE_SIZE
import org.openjdk.jmh.reconfigure.statistics.Sampler
import java.io.File
import java.io.FileWriter

private val output = FileWriter(File("D:\\outputString.csv"))
private val output2 = FileWriter(File("D:\\outputString2.csv"))

fun main() {
    val folder = File("D:\\rq2\\pre\\rxjava_100_iterations_1_second\\")

    folder.walk().forEach {
        if (it.isFile) {
            evalBenchmark(it)
        }
    }

    output.flush()
    output2.flush()
}

private fun evalBenchmark(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList()
    output.append(key.output())
    output2.append(key.output())
    evaluation(list)
    output.appendln("")
    output2.appendln("")
    output.flush()
    output2.flush()
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

    val sampledHistogram = histogram.map { (iteration, list) ->
        val od = OutlierDetector(ReconfigureConstants.OUTLIER_FACTOR, list)
        od.run()
        val sample = Sampler(od.inlier).getSample(SAMPLE_SIZE)
        Pair(iteration, sample)
    }.toMap()

    val all = mutableListOf<HistogramItem>()

    val medians = mutableMapOf<Int, Double>()
    sampledHistogram.forEach { (iteration, iterationList) ->
        all.addAll(iterationList)
        val median = iterationList.map{ it.value }.median()
        medians[iteration] = median

        output2.append(";$median")

        if (iteration >= 3) {
            try {
                val relativeMedianShift = Math.abs(medians.getValue(iteration - 1) - median) / median
                output.append(";$relativeMedianShift")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}

private fun List<Double>.median() = sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }