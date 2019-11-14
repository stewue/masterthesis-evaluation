package ch.uzh.ifi.seal.smr.reconfigure.analysis.variability

import ch.uzh.ifi.seal.smr.reconfigure.CsvLineParser
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.COV
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.CiPercentage
import org.openjdk.jmh.reconfigure.statistics.divergence.Divergence
import java.io.File
import java.util.*

fun main(){
    val file = File("")
    val all = CsvLineParser(file).getList().map{ it.getHistogramItem() }

    all.groupBy { it.fork }.forEach{ fork, list ->
        val reachedCov = 30
        val reachedCi = 30
        val reachedKld = 30

        val defaultItems = list.filter { it.iteration <= 50 }
        val covItems = list.filter { it.iteration <= reachedCov }
        val ciItems = list.filter { it.iteration <= reachedCi }
        val kldItems = list.filter { it.iteration <= reachedKld }

        val covDefault = cov(defaultItems)
        val covCov = cov(covItems)
        val covCi = cov(ciItems)
        val covKld = cov(kldItems)

        val ciDefault = ci(defaultItems)
        val ciCov = ci(covItems)
        val ciCi = ci(ciItems)
        val ciKld = ci(kldItems)

        val kldCov = kld(covItems, defaultItems)
        val kldCi = kld(ciItems, defaultItems)
        val kldKld = kld(kldItems, defaultItems)

        println("a")
    }


}

private fun cov(items: List<HistogramItem>): Double {
    val od = OutlierDetector(OUTLIER_FACTOR, items)
    od.run()
    return COV(od.inlier).value
}

private fun ci(items: List<HistogramItem>): Double {
    val sample = Sampler(items).getSample(10000)

    Collections.sort(sample) { item1, item2 ->
        val diffFork = item1.fork - item2.fork
        if (diffFork > 0) {
            1
        } else if (diffFork < 0) {
            -1
        } else {
            val diffIteration = item1.iteration - item2.iteration
            if (diffIteration > 0) {
                1
            } else if (diffIteration < 0) {
                -1
            } else {
                0
            }
        }
    }

    return CiPercentage(sample).value
}

private fun kld(new: List<HistogramItem>, default: List<HistogramItem>): Double {
    val sampleNew = Sampler(new).getSample(1000).map{ it.value }
    val sampleDefault = Sampler(default).getSample(1000).map{ it.value }

    return Divergence(sampleNew, sampleDefault).value
}