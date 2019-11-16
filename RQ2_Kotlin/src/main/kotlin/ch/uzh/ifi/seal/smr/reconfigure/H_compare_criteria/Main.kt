package ch.uzh.ifi.seal.smr.reconfigure.H_compare_criteria

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.COV
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.CiPercentage
import org.openjdk.jmh.reconfigure.statistics.divergence.Divergence
import java.io.File
import java.util.*

private val header = "project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5"

fun main() {
    val folder = File("D:\\rq2\\results\\csv-jenetics")
    val inputCov = File("D:\\outputCovTotal.csv")
    val inputCi = File("D:\\outputCiTotal.csv")
    val inputDivergence = File("D:\\outputDivergenceTotal.csv")

    val reachedForkCov = mutableMapOf<String, Int>()
    val reachedIterationCov = mutableMapOf<String, MutableMap<Int, Int>>()
    val reachedForkCi = mutableMapOf<String, Int>()
    val reachedIterationCi = mutableMapOf<String, MutableMap<Int, Int>>()
    val reachedForkDivergence = mutableMapOf<String, Int>()
    val reachedIterationDivergence = mutableMapOf<String, MutableMap<Int, Int>>()

    inputCov.forEachLine {
        if (it == header) {
            return@forEachLine
        }

        val parts = it.split(";")
        val name = "${parts[0]}#${parts[2]}#${parts[3]}"
        reachedForkCov[name] = parts[9].toInt()
        reachedIterationCov[name] = mutableMapOf(
                1 to parts[10].toInt(),
                2 to parts[11].toInt(),
                3 to parts[12].toInt(),
                4 to parts[13].toInt(),
                5 to parts[14].toInt()
        )
    }

    inputCi.forEachLine {
        if (it == header) {
            return@forEachLine
        }

        val parts = it.split(";")
        val name = "${parts[0]}#${parts[2]}#${parts[3]}"
        reachedForkCi[name] = parts[9].toInt()
        reachedIterationCi[name] = mutableMapOf(
                1 to parts[10].toInt(),
                2 to parts[11].toInt(),
                3 to parts[12].toInt(),
                4 to parts[13].toInt(),
                5 to parts[14].toInt()
        )
    }

    inputDivergence.forEachLine {
        if (it == header) {
            return@forEachLine
        }

        val parts = it.split(";")
        val name = "${parts[0]}#${parts[2]}#${parts[3]}"
        reachedForkDivergence[name] = parts[9].toInt()
        reachedIterationDivergence[name] = mutableMapOf(
                1 to parts[10].toInt(),
                2 to parts[11].toInt(),
                3 to parts[12].toInt(),
                4 to parts[13].toInt(),
                5 to parts[14].toInt()
        )
    }

    folder.walk().forEach {
        if (it.isFile) {
            val name = it.nameWithoutExtension
            val all = CsvLineParser(it).getList().map { it.getHistogramItem() }

            val defaultItems = all.filter { it.iteration > 50 }
            val covItems = getItems(all, reachedForkCov.getValue(name), reachedIterationCov.getValue(name))
            val ciItems = getItems(all, reachedForkCi.getValue(name), reachedIterationCi.getValue(name))
            val kldItems = getItems(all, reachedForkDivergence.getValue(name), reachedIterationDivergence.getValue(name))

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

            println("${Math.abs(covCov - covDefault)};${Math.abs(covCi - covDefault)};${Math.abs(covKld - covDefault)};${ciCov / ciDefault};${ciCi / ciDefault};${ciKld / ciDefault};$kldCov;$kldCi;$kldKld")
        }
    }
}

private fun getItems(items: List<HistogramItem>, fork: Int, iterations: Map<Int, Int>): List<HistogramItem> {
    val all = mutableListOf<HistogramItem>()
    iterations.forEach { f, reached ->
        if (f <= fork) {
            var reachedFixed = reached
            if (reachedFixed > 50) {
                reachedFixed = 50
            }
            val filter = items.filter { it.fork == f && it.iteration > reachedFixed && it.iteration <= reachedFixed + 10 }
            all.addAll(filter)
        }
    }
    return all
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
    val sampleNew = Sampler(new).getSample(1000).map { it.value }
    val sampleDefault = Sampler(default).getSample(1000).map { it.value }

    return Divergence(sampleNew, sampleDefault).value
}