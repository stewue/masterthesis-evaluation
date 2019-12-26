package ch.uzh.ifi.seal.smr.reconfigure.analysis.shorteriterations

import ch.uzh.ifi.seal.smr.reconfigure.utils.all
import ch.uzh.ifi.seal.smr.reconfigure.utils.getHistogramItems
import org.openjdk.jmh.reconfigure.helper.HistogramHelper
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.COV
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import org.openjdk.jmh.reconfigure.statistics.ci.CiPercentage
import org.openjdk.jmh.reconfigure.statistics.divergence.Divergence
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

fun main() {
    val csvInput10 = File("D:\\rq2\\pre\\10_iterations_10_seconds\\")
    val csvInput100 = "D:\\rq2\\pre\\100_iterations_1_second"

    val output = FileWriter(File("D:\\rq2\\pre\\iterations.csv"))

    csvInput10.walk().forEach { file10 ->
        if (file10.isFile) {
            try {
                val name = file10.nameWithoutExtension

                val file100 = Paths.get(csvInput100, file10.name).toFile()

                val all10 = getHistogramItems(file10, all)
                val all100 = getHistogramItems(file100, all)

                val (project, _, benchmark, params) = name.split(";")

                val items10 = getItems(all10, 5, 10)
                val items10Pre = getItems(all10, 5, 9)
                val items100 = getItems(all100, 50, 100)
                val items100Pre = getItems(all100, 50, 90)

                // sample
                val sample10 = Sampler(items10).getSample(10000)
                val sample10Pre = Sampler(items10Pre).getSample(10000)
                val sample100 = Sampler(items100).getSample(10000)
                val sample100Pre = Sampler(items100Pre).getSample(10000)

                // outlier detector
                val od10 = OutlierDetector(OUTLIER_FACTOR, sample10)
                val od10Pre = OutlierDetector(OUTLIER_FACTOR, sample10Pre)
                val od100 = OutlierDetector(OUTLIER_FACTOR, sample100)
                val od100Pre = OutlierDetector(OUTLIER_FACTOR, sample100Pre)
                od10.run()
                od10Pre.run()
                od100.run()
                od100Pre.run()

                val sampleDouble10 = HistogramHelper.toArray(od10.inlier)
                val sampleDouble10Pre = HistogramHelper.toArray(od10Pre.inlier)
                val sampleDouble100 = HistogramHelper.toArray(od100.inlier)
                val sampleDouble100Pre = HistogramHelper.toArray(od100Pre.inlier)

                val cov10 = COV(od10.inlier).value
                val cov100 = COV(od100.inlier).value

                val ci10 = CiPercentage(od10.inlier, 10000).value
                val ci100 = CiPercentage(od100.inlier, 10000).value

                val divergence10 = Divergence(sampleDouble10, sampleDouble10Pre).value
                val divergence100 = Divergence(sampleDouble100, sampleDouble100Pre).value

                val avgInvocations = items100.map { it.count }.sum() / 50.0
                output.appendln("$project;;$benchmark;$params;$avgInvocations;$cov10;$cov100;$ci10;$ci100;$divergence10;$divergence100")
                output.flush()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun getItems(items: Collection<HistogramItem>, min: Int, max: Int): List<HistogramItem> {
    return items.filter { it.fork == 1 && it.iteration > min && it.iteration <= max }
}

private fun ciRatioPercentage(lower: Double, upper: Double): Double {
    return when {
        lower > 1 -> lower - 1
        upper < 1 -> 1 - upper
        else -> 0.0
    }
}