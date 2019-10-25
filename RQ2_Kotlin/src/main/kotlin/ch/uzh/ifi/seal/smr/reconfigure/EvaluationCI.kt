package ch.uzh.ifi.seal.smr.reconfigure


import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.statistics.Sampler
import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.CI
import java.io.File
import java.io.FileWriter
import kotlin.math.abs
import kotlin.math.round

//fun main() {
//    val file = File("D:\\rq2\\out100.csv")
//    val list = CsvLineParser(file).getList()
//    evaluation(list)
//}

private val output = FileWriter(File("D:\\outputString.csv"))
private val output2 = FileWriter(File("D:\\outputString2.csv"))

fun main(){
    val folder = File("D:\\rq2\\pre\\log4j2_100_iterations_1_second\\")

    folder.walk().forEach {
        if(it.isFile){
            evalBenchmark(it)
        }
    }

    output.flush()
}

private fun evalBenchmark(file: File){
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project,commit, benchmark, params)
    val list = CsvLineParser(file).getList()
    output.append(key.output())
    output2.append(key.output())
    evaluation(list)
    output.appendln("")
    output2.appendln("")
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
        val sample = Sampler(list).getSample(sampleSize)
        Pair(iteration, sample)
    }.toMap()

    val all = mutableListOf<HistogramItem>()

    val relativeWidths = mutableMapOf<Int, Double>()
    sampledHistogram.forEach { (iteration, iterationList) ->
        all.addAll(iterationList)
        val ci = CI(all)
        ci.run()
        val relativeWidth = (ci.upper -ci.lower) / ci.statisticMetric

        relativeWidths[iteration] = relativeWidth
        output2.append(";$relativeWidth")

        if (iteration >= 5) {
            val i1 = relativeWidths.getValue(iteration - 1) - relativeWidth
            val i2 = relativeWidths.getValue(iteration - 2) - relativeWidth
            val i3 = relativeWidths.getValue(iteration - 3) - relativeWidth
            val i4 = relativeWidths.getValue(iteration - 4) - relativeWidth

            val max = Math.max(Math.max(Math.abs(i1), Math.abs(i2)), Math.max(Math.abs(i3), Math.abs(i4)))
            output.append(";$max")
        }
    }
}