package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import ch.uzh.ifi.seal.smr.reconfigure.statistics.Sampler
import java.io.File
import java.io.FileWriter

private val output = FileWriter(File("D:\\outputString.csv"))
private val output2 = FileWriter(File("D:\\outputString2.csv"))

fun main() {
    val folder = File("D:\\rq2\\pre\\rxjava_10_iterations_10_seconds\\")

    folder.walk().forEach {
        if (it.isFile) {
            evalBenchmark(it)
        }
    }

    output.flush()
    output2.flush()
}

//fun main() {
//    val file = File("D:\\rq2\\out100.csv")
//    val list = CsvLineParser(file).getList()
//    evaluation(list)
//}

private fun evalBenchmark(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList()
    output.append(key.output())
    output2.append(key.output())
    evaluation(list)
    output.appendln("")
    output2.appendln("")
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

        output2.append(";${cov.value}")

        if (iteration >= 5) {
            try {
                val i1 = Math.abs(covs.getValue(iteration - 1) - cov.value)
                val i2 = Math.abs(covs.getValue(iteration - 2) - cov.value)
                val i3 = Math.abs(covs.getValue(iteration - 3) - cov.value)
                val i4 = Math.abs(covs.getValue(iteration - 4) - cov.value)
                val max = getMax(listOf(i1, i2, i3, i4))
                output.append(";$max")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}

private fun getMax(list: List<Double>): Double {
    var max = list.first()

    list.forEach {
        if (it > max) {
            max = it
        }
    }

    return max
}