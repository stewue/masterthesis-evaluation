package ch.uzh.ifi.seal.smr.reconfigure.analysis.measurementsize

import ch.uzh.ifi.seal.smr.reconfigure.process.H_compare_criteria.CliffDeltaTest
import ch.uzh.ifi.seal.smr.reconfigure.process.H_compare_criteria.WilcoxonRankSumTest
import ch.uzh.ifi.seal.smr.reconfigure.utils.all
import ch.uzh.ifi.seal.smr.reconfigure.utils.getHistogramItems
import org.openjdk.jmh.reconfigure.helper.HistogramHelper
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.CiRatio
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
//    val csvInput = File("D:\\rq2\\results\\csv-log4j2")
//    val outputDir = "D:\\tmp"
        val csvInput = File("/home/user/stefan-masterthesis/statistics/csv-log4j2")
        val outputDir = "/home/user/stefan-masterthesis/stop/tmp"

    val inputDivergence = Paths.get(outputDir, "outputDivergenceTotal.csv").toFile()
    val output = FileWriter(Paths.get(outputDir, "tests.csv").toFile())

    output.append("project;commit;benchmark;params;effectSize5;effectSize10;effectSize15;effectSize20;effectSize25;wilcoxon5;wilcoxon10;wilcoxon15;wilcoxon20;wilcoxon25;ciRatio5;ciRatio10;ciRatio15;ciRatio20;ciRatio25\n")

    val reachedIterationDivergence = mutableMapOf<String, Int>()

    parseInput(inputDivergence, reachedIterationDivergence)

    csvInput.walk().forEach {
        if (it.isFile) {
            try {
                val name = it.nameWithoutExtension
                val all = getHistogramItems(it, all)

                val (project, benchmark, params) = name.split("#")

                output.append("$project;;$benchmark;$params")

                // construct dynamic stopping dataset
                val items5 = getItems(all, reachedIterationDivergence.getValue(name), 5)
                val items10 = getItems(all, reachedIterationDivergence.getValue(name), 10)
                val items15 = getItems(all, reachedIterationDivergence.getValue(name), 15)
                val items20 = getItems(all, reachedIterationDivergence.getValue(name), 20)
                val items25 = getItems(all, reachedIterationDivergence.getValue(name), 25)
                val items50 = getItems(all, reachedIterationDivergence.getValue(name), 50)

                // sample
                val sample5 = Sampler(items5).getSample(10000)
                val sample10 = Sampler(items10).getSample(10000)
                val sample15 = Sampler(items15).getSample(10000)
                val sample20 = Sampler(items20).getSample(10000)
                val sample25 = Sampler(items25).getSample(10000)
                val sample50 = Sampler(items50).getSample(10000)

                // outlier detector
                val od5 = OutlierDetector(OUTLIER_FACTOR, sample5)
                val od10 = OutlierDetector(OUTLIER_FACTOR, sample10)
                val od15 = OutlierDetector(OUTLIER_FACTOR, sample15)
                val od20 = OutlierDetector(OUTLIER_FACTOR, sample20)
                val od25 = OutlierDetector(OUTLIER_FACTOR, sample25)
                val od50 = OutlierDetector(OUTLIER_FACTOR, sample50)
                od5.run()
                od10.run()
                od15.run()
                od20.run()
                od25.run()
                od50.run()

                val sampleDouble5 = HistogramHelper.toArray(od5.inlier)
                val sampleDouble10 = HistogramHelper.toArray(od10.inlier)
                val sampleDouble15 = HistogramHelper.toArray(od15.inlier)
                val sampleDouble20 = HistogramHelper.toArray(od20.inlier)
                val sampleDouble25 = HistogramHelper.toArray(od25.inlier)
                val sampleDouble50 = HistogramHelper.toArray(od50.inlier)

                val effectSize5 = abs(CliffDeltaTest(sampleDouble50, sampleDouble5).effectSize)
                val effectSize10 = abs(CliffDeltaTest(sampleDouble50, sampleDouble10).effectSize)
                val effectSize15 = abs(CliffDeltaTest(sampleDouble50, sampleDouble15).effectSize)
                val effectSize20 = abs(CliffDeltaTest(sampleDouble50, sampleDouble20).effectSize)
                val effectSize25 = abs(CliffDeltaTest(sampleDouble50, sampleDouble25).effectSize)

                val wilcoxon5 = WilcoxonRankSumTest(sampleDouble50, sampleDouble5).pValue
                val wilcoxon10 = WilcoxonRankSumTest(sampleDouble50, sampleDouble10).pValue
                val wilcoxon15 = WilcoxonRankSumTest(sampleDouble50, sampleDouble15).pValue
                val wilcoxon20 = WilcoxonRankSumTest(sampleDouble50, sampleDouble20).pValue
                val wilcoxon25 = WilcoxonRankSumTest(sampleDouble50, sampleDouble25).pValue

                val ciRatio5 = CiRatio(od50.inlier, od5.inlier, 10000, 0.01, "mean", -1)
                val ciRatio10 = CiRatio(od50.inlier, od10.inlier, 10000, 0.01, "mean", -1)
                val ciRatio15 = CiRatio(od50.inlier, od15.inlier, 10000, 0.01, "mean", -1)
                val ciRatio20 = CiRatio(od50.inlier, od20.inlier, 10000, 0.01, "mean", -1)
                val ciRatio25 = CiRatio(od50.inlier, od25.inlier, 10000, 0.01, "mean", -1)

                ciRatio5.run()
                ciRatio10.run()
                ciRatio15.run()
                ciRatio20.run()
                ciRatio25.run()

                output.append(";$effectSize5;$effectSize10;$effectSize15;$effectSize20;$effectSize25;$wilcoxon5;$wilcoxon10;$wilcoxon15;$wilcoxon20;$wilcoxon25;${ciRatioPercentage(ciRatio5.lower, ciRatio5.upper)};${ciRatioPercentage(ciRatio10.lower, ciRatio10.upper)};${ciRatioPercentage(ciRatio15.lower, ciRatio15.upper)};${ciRatioPercentage(ciRatio20.lower, ciRatio20.upper)};${ciRatioPercentage(ciRatio25.lower, ciRatio25.upper)}")
                output.append("\n")
                output.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun getItems(items: Collection<HistogramItem>, reached: Int, iter: Int): List<HistogramItem> {
    var reachedFixed = reached
    if (reachedFixed > 50) {
        reachedFixed = 50
    }
    return items.filter { it.fork == 1 && it.iteration > reachedFixed && it.iteration <= reachedFixed + iter }
}

private fun parseInput(input: File, reachedIteration: MutableMap<String, Int>) {
    input.forEachLine {
        if (it == "project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5") {
            return@forEachLine
        }

        val parts = it.split(";")
        val name = "${parts[0]}#${parts[2]}#${parts[3]}"
        reachedIteration[name] = parts[10].toInt()
    }
}

private fun ciRatioPercentage(lower: Double, upper: Double): Double {
    return when {
        lower > 1 -> lower - 1
        upper < 1 -> 1 - upper
        else -> 0.0
    }
}