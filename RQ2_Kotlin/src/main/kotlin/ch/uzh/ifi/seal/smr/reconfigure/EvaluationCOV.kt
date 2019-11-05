package ch.uzh.ifi.seal.smr.reconfigure

import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.COV
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.SAMPLE_SIZE
import org.openjdk.jmh.reconfigure.statistics.Sampler
import java.io.File
import java.io.FileWriter

private val outputCovChange = FileWriter(File("/home/user/stefan-masterthesis/outputCovChange.csv"))
private val outputCov = FileWriter(File("/home/user/stefan-masterthesis/outputCov.csv"))

fun evalBenchmarkCOV(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList()
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

    val sampledHistogram = histogram.map { (iteration, list) ->
        val od = OutlierDetector(OUTLIER_FACTOR, list)
        od.run()
        val sample = Sampler(od.inlier).getSample(SAMPLE_SIZE)
        Pair(iteration, sample)
    }.toMap()

    val all = mutableListOf<HistogramItem>()

    val covs = mutableMapOf<Int, Double>()
    sampledHistogram.forEach { (iteration, iterationList) ->
        all.addAll(iterationList)
        val cov = COV(all, 0.0)
        covs[iteration] = cov.value

        outputCov.append(";${cov.value}")

        if (iteration >= 5) {
            try {
                val i1 = Math.abs(covs.getValue(iteration - 1) - cov.value)
                val i2 = Math.abs(covs.getValue(iteration - 2) - cov.value)
                val i3 = Math.abs(covs.getValue(iteration - 3) - cov.value)
                val i4 = Math.abs(covs.getValue(iteration - 4) - cov.value)
                val max = getMax(listOf(i1, i2, i3, i4))
                outputCovChange.append(";$max")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}