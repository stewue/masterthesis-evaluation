package ch.uzh.ifi.seal.smr.reconfigure


import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.SAMPLE_SIZE
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.RelativeCiWidthByMean
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

private val outputCiChange = FileWriter(Paths.get(outputDirectory, "outputCiChange.csv").toFile())
private val outputCi = FileWriter(Paths.get(outputDirectory, "outputCi.csv").toFile())

fun evalBenchmarkCi(key: CsvLineKey, list: Collection<CsvLine>) {
    outputCiChange.append(key.output())
    outputCi.append(key.output())
    evaluation(list)
    outputCiChange.appendln("")
    outputCi.appendln("")

    outputCiChange.flush()
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

    val sampledHistogram = histogram.map { (iteration, list) ->
        val sample = Sampler(list).getSample(SAMPLE_SIZE)
        Pair(iteration, sample)
    }.toMap()

    val all = mutableListOf<HistogramItem>()

    val relativeWidths = mutableMapOf<Int, Double>()
    sampledHistogram.forEach { (iteration, iterationList) ->
        all.addAll(iterationList)
        val rcwm = RelativeCiWidthByMean(all)

        val relativeWidth = rcwm.value
        deleteTmp()

        relativeWidths[iteration] = relativeWidth
        outputCi.append(";$relativeWidth")

        if (iteration >= 5) {
            val i1 = Math.abs(relativeWidths.getValue(iteration - 1) - relativeWidth)
            val i2 = Math.abs(relativeWidths.getValue(iteration - 2) - relativeWidth)
            val i3 = Math.abs(relativeWidths.getValue(iteration - 3) - relativeWidth)
            val i4 = Math.abs(relativeWidths.getValue(iteration - 4) - relativeWidth)

            val max = getMax(listOf(i1, i2, i3, i4))
            outputCiChange.append(";$max")
        }
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