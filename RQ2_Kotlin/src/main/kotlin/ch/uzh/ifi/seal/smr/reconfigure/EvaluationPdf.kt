package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import smile.math.Math
import smile.stat.distribution.KernelDensity
import java.io.File
import java.io.FileWriter
import java.io.OutputStream
import java.io.PrintStream
import kotlin.streams.toList

private val output = FileWriter(File("D:\\outputString.csv"))
private val output2 = FileWriter(File("D:\\outputString2.csv"))

fun main() {
    disableSystemErr()
    val folder = File("D:\\rq2\\pre\\log4j2_10_iterations_10_seconds\\")

    folder.walk().forEach {
        if (it.isFile) {
            evalBenchmark(it)
        }
    }

    output.flush()
    output2.flush()
}

private fun evalBenchmark(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList().map { it.getHistogramItem() }
    output.append(key.output())
    output2.append(key.output())
    evaluation(list)
    output.appendln("")
    output2.appendln("")
}

private fun evaluation(list: List<HistogramItem>) {
    val sample = mutableMapOf<Int, List<Double>>()
    val range = mutableMapOf<Int, Pair<Double, Double>>()

    sample[1] = getSample(list.filter { it.iteration == 1 })
    range[1] = getRange(sample.getValue(1))

    val ps = mutableMapOf<Int, Double>()

    for (i in 2..10) {
        sample[i] = getSample(list.filter { it.iteration <= i })
        range[i] = getRange(sample.getValue(i))
        val sample1 = sample.getValue(i - 1)
        val sample2 = sample.getValue(i)

        val range1 = range.getValue(i - 1)
        val range2 = range.getValue(i)
        val min = Math.min(range1.first, range2.first)
        val max = Math.max(range1.second, range2.second)

        val p = getPValue(sample1, sample2, min, max)
        ps[i] = p

        output.append(";$p")

        if (i >= 6) {
            val i1 = Math.abs(ps.getValue(i - 1) - p)
            val i2 = Math.abs(ps.getValue(i - 2) - p)
            val i3 = Math.abs(ps.getValue(i - 3) - p)
            val i4 = Math.abs(ps.getValue(i - 4) - p)

            val max = getMax(listOf(i1, i2, i3, i4))
            output2.append(";$max")
        }
    }
}

private fun getSample(list: List<HistogramItem>): List<Double> {
    val distributionPairs = list.stream().map { org.apache.commons.math3.util.Pair(it.value, it.count.toDouble()) }.toList()
    val ed = EnumeratedDistribution<Double>(distributionPairs)
    val sample = ed.sample(1000).toList().toMutableList() as MutableList<Double>
    sample.sortBy { it }
    return sample
}

private fun getRange(list: List<Double>): Pair<Double, Double> {
    val outlierFactor = 1.5
    val ds = DescriptiveStatistics(list.toDoubleArray())
    val q1 = ds.getPercentile(25.0)
    val q3 = ds.getPercentile(75.0)
    val iqr = q3 - q1

    val max = q3 + outlierFactor * iqr
    var min = q1 - outlierFactor * iqr

    if (min < 0) {
        min = 0.0
    }
    return Pair(min, max)
}

private fun getPValue(sample1: List<Double>, sample2: List<Double>, min: Double, max: Double): Double {
    val y = mutableListOf<Double>()
    val points = 1000
    val step = (max - min) / (points - 1)
    for (i in 0..(points - 1)) {
        y.add(min + i * step)
    }
    val yArray = y.toDoubleArray()

    val pdf1 = getPDF(sample1, yArray)
    val pdf2 = getPDF(sample2, yArray)

    val kld1 = continuousKullbackLeiblerDivergence(pdf1, pdf2, step)
    val kld2 = continuousKullbackLeiblerDivergence(pdf2, pdf1, step)

    val product = Math.pow(2.0, -kld1) * Math.pow(2.0, -kld2)
    return product
}

private fun getPDF(sample: List<Double>, y: DoubleArray): List<Double> {
    val kd = KernelDensity(sample.toDoubleArray())

    return y.map {
        kd.p(it)
    }
}

private fun disableSystemErr() {
    System.setErr(PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    }))
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

private fun continuousKullbackLeiblerDivergence(p: List<Double>, q: List<Double>, width: Double): Double {
    var intersection = false
    var kl = 0.0

    for (i in p.indices) {
        if (p[i] != 0.0 && q[i] != 0.0) {
            intersection = true
            kl += p[i] * Math.log(p[i] / q[i]) * width
        }
    }

    return if (intersection) {
        kl
    } else {
        java.lang.Double.POSITIVE_INFINITY
    }
}