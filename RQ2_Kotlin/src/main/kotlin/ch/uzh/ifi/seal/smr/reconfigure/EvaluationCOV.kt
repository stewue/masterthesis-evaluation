package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import ch.uzh.ifi.seal.smr.reconfigure.statistics.Sampler
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

    val sampleSize = 1000

    val sampledHistogram = histogram.map { (iteration, list) ->
        val od = OutlierDetector(10.0, list)
        od.run()
        val sample = Sampler(od.inlier).getSample(sampleSize)
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