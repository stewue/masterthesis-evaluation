package ch.uzh.ifi.seal.smr.reconfigure


import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.statistics.Sampler
import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.CI
import java.io.File
import kotlin.math.abs
import kotlin.math.round

fun main() {
    val file = File("D:\\rq2\\out100.csv")
    val list = CsvLineParser(file).getList()
    evaluation(list)
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

        println(relativeWidth)
        if (iteration >= 5) {
            val i1 = relativeWidths.getValue(iteration - 1) - relativeWidth
            val i2 = relativeWidths.getValue(iteration - 2) - relativeWidth
            val i3 = relativeWidths.getValue(iteration - 3) - relativeWidth
            val i4 = relativeWidths.getValue(iteration - 4) - relativeWidth

            println("${roundDelta(i1)} / ${roundDelta(i2)} / ${roundDelta(i3)} / ${roundDelta(i4)}")

            // TODO absolut or relative change rate?
            if(abs(i1) < 0.02 && abs(i2) < 0.02 && abs(i3) < 0.02 && abs(i4) < 0.02){
                println("iteration: $iteration -> $relativeWidth")
                return
            }
        }
    }
}

private fun roundDelta(value: Double): Double {
    return round(value * 10000) / 10000
}