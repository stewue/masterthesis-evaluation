package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest
import umontreal.iro.lecuyer.gof.KernelDensity
import umontreal.iro.lecuyer.probdist.EmpiricalDist
import umontreal.iro.lecuyer.probdist.NormalDist
import java.io.File
import kotlin.streams.toList


fun main() {
    val file = File("D:\\rq2\\out100.csv")
    val list = CsvLineParser(file).getList().map { it.getHistogramItem() }

    val sample = mutableMapOf<Int, List<Double>>()
    val range = mutableMapOf<Int, Pair<Double, Double>>()

    sample[1] = getSample(list.filter { it.iteration == 1 })
    range[1] = getRange(sample.getValue(1))

    for (i in 2..50) {
        sample[i] = getSample(list.filter { it.iteration <= i })
        range[i] = getRange(sample.getValue(i))

        val sample1 = sample.getValue(i - 1)
        val sample2 = sample.getValue(i)

        val range1 = range.getValue(i - 1)
        val range2 = range.getValue(i)
        val min = Math.min(range1.first, range2.first)
        val max = Math.max(range1.second, range2.second)

        val p = getPValue(sample1, sample2, min, max)
        println(p)
//        if(p > 0.9){
//            println("iteration: $i")
//            return
//        }
    }
}

private fun getSample(list: List<HistogramItem>): List<Double> {
    val distributionPairs = list.stream().map { org.apache.commons.math3.util.Pair(it.value, it.count.toDouble()) }.toList()
    val ed = EnumeratedDistribution<Double>(distributionPairs)
    val sample = ed.sample(10000).toList().toMutableList() as MutableList<Double>
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
    val min = q1 - outlierFactor * iqr
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

    val l12 = Math.pow(2.0, -1 * KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(pdf1, pdf2))
    val l21 = Math.pow(2.0, -1 * KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(pdf2, pdf1))

    return l12 * l21
}

private fun getPDF(sample: List<Double>, y: DoubleArray): DoubleArray {
    val array = sample.toDoubleArray()
    return KernelDensity.computeDensity(EmpiricalDist(array), NormalDist(), y)
}