package ch.uzh.ifi.seal.smr.reconfigure.analysis.shorteriterations

import ch.uzh.ifi.seal.smr.reconfigure.utils.all
import ch.uzh.ifi.seal.smr.reconfigure.utils.getHistogramItems
import org.openjdk.jmh.reconfigure.helper.HistogramHelper
import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.helper.OutlierDetector
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.OUTLIER_FACTOR
import org.openjdk.jmh.reconfigure.statistics.Sampler
import java.io.File
import java.io.FileWriter
import java.nio.file.Paths

fun main() {
    val csvInput10 = File("D:\\rq2\\pre\\10_iterations_10_seconds\\")
    val csvInput100 = "D:\\rq2\\pre\\100_iterations_1_second"

    val output = FileWriter(File("D:\\rq2\\pre\\mean.csv"))

    output.appendln("project;commit;benchmark;params;percentage;mean")
    csvInput10.walk().forEach { file10 ->
        if (file10.isFile) {
            try {
                val name = file10.nameWithoutExtension

                val file100 = Paths.get(csvInput100, file10.name).toFile()

                val all10 = getHistogramItems(file10, all)
                val all100 = getHistogramItems(file100, all)

                val (project, commit, benchmark, params) = name.split(";")

                output.append("$project;;$benchmark;$params")

                val items10 = getItems(all10, 6)
                val items100 = getItems(all100, 51)

                // sample
                val sample10 = Sampler(items10).getSample(10000)
                val sample100 = Sampler(items100).getSample(10000)

                // outlier detector
                val od10 = OutlierDetector(OUTLIER_FACTOR, sample10)
                val od100 = OutlierDetector(OUTLIER_FACTOR, sample100)
                od10.run()
                od100.run()

                val sampleDouble10 = HistogramHelper.toArray(od10.inlier)
                val sampleDouble100 = HistogramHelper.toArray(od100.inlier)

                val mean100 = sampleDouble100.average()
                val percentage = mean100 / sampleDouble10.average()

                output.appendln(";$percentage;$mean100")
                output.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun getItems(items: Collection<HistogramItem>, iteration: Int): List<HistogramItem> {
    return items.filter { it.fork == 1 && it.iteration >= iteration }
}