package ch.uzh.ifi.seal.smr.reconfigure.H_compare_criteria

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import org.openjdk.jmh.reconfigure.helper.HistogramHelper
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.CiPercentage
import org.openjdk.jmh.reconfigure.statistics.ci.CiRatio
import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.nio.file.Paths
import java.util.*

class Evaluation(private val csvInput: File, private val outputDir: String) {

    private val header = "project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5"

    fun run() {
        disableSystemErr()

        val inputCov = Paths.get(outputDir, "outputCovTotal.csv").toFile()
        val inputCi = Paths.get(outputDir, "outputCiTotal.csv").toFile()
        val inputDivergence = Paths.get(outputDir, "outputDivergenceTotal.csv").toFile()

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

        csvInput.walk().forEach {
            if (it.isFile) {
                try {
                    val name = it.nameWithoutExtension
                    val all = CsvLineParser(it).getList().map { it.getHistogramItem() }

                    val defaultItems = all.filter { it.iteration > 50 }
                    val covItems = getItems(all, reachedForkCov.getValue(name), reachedIterationCov.getValue(name))
                    val ciItems = getItems(all, reachedForkCi.getValue(name), reachedIterationCi.getValue(name))
                    val kldItems = getItems(all, reachedForkDivergence.getValue(name), reachedIterationDivergence.getValue(name))

                    val ciDefault = ci(defaultItems)
                    val ciCov = ci(covItems)
                    val ciCi = ci(ciItems)
                    val ciKld = ci(kldItems)

                    println("${ciCov / ciDefault};${ciCi / ciDefault};${ciKld / ciDefault}")

                    val defaultSample = Sampler(defaultItems).getSample(10000)
                    val covSample = Sampler(covItems).getSample(10000)
                    val ciSample = Sampler(ciItems).getSample(10000)
                    val kldSample = Sampler(kldItems).getSample(10000)

                    val defaultSampleDouble = HistogramHelper.toArray(defaultSample)
                    val covSampleDouble = HistogramHelper.toArray(covSample)
                    val ciSampleDouble = HistogramHelper.toArray(ciSample)
                    val kldSampleDouble = HistogramHelper.toArray(kldSample)

                    val effectSizeCov = CliffDeltaTest(defaultSampleDouble, covSampleDouble).effectSize
                    val effectSizeCi = CliffDeltaTest(defaultSampleDouble, ciSampleDouble).effectSize
                    val effectSizeKld = CliffDeltaTest(defaultSampleDouble, kldSampleDouble).effectSize

                    println("${Math.abs(effectSizeCov)};${Math.abs(effectSizeCi)};${Math.abs(effectSizeKld)}")

                    val wilcoxonCov = WilcoxonRankSumTest(defaultSampleDouble, covSampleDouble).pValue
                    val wilcoxonCi = WilcoxonRankSumTest(defaultSampleDouble, ciSampleDouble).pValue
                    val wilcoxonKld = WilcoxonRankSumTest(defaultSampleDouble, kldSampleDouble).pValue

                    println("$wilcoxonCov;$wilcoxonCi;$wilcoxonKld")

                    val odDefault = OutlierDetector(OUTLIER_FACTOR, defaultSample)
                    val odCov = OutlierDetector(OUTLIER_FACTOR, covSample)
                    val odCi = OutlierDetector(OUTLIER_FACTOR, ciSample)
                    val odKld = OutlierDetector(OUTLIER_FACTOR, kldSample)
                    odDefault.run()
                    odCov.run()
                    odCi.run()
                    odKld.run()

                    val ciRatioCov = CiRatio(odDefault.inlier, odCov.inlier, 10000, 0.01, "mean", -1)
                    val ciRatioCi = CiRatio(odDefault.inlier, odCi.inlier, 10000, 0.01, "mean", -1)
                    val ciRatioKld = CiRatio(odDefault.inlier, odKld.inlier, 10000, 0.01, "mean", -1)
                    ciRatioCov.run()
                    ciRatioCi.run()
                    ciRatioKld.run()

                    println("${ciRatioCov.lower > 1 || ciRatioCov.upper < 1};${ciRatioCi.lower > 1 || ciRatioCi.upper < 1};${ciRatioKld.lower > 1 || ciRatioKld.upper < 1}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

        return CiPercentage(sample, 10000).value
    }

    private fun disableSystemErr() {
        System.setErr(PrintStream(object : OutputStream() {
            override fun write(b: Int) {}
        }))
    }
}