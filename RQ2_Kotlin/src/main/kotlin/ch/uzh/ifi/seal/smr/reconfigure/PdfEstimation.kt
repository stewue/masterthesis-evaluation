package ch.uzh.ifi.seal.smr.reconfigure

import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest
import umontreal.iro.lecuyer.gof.KernelDensity
import umontreal.iro.lecuyer.probdist.EmpiricalDist
import umontreal.iro.lecuyer.probdist.NormalDist
import java.io.File
import kotlin.streams.toList


fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\unstable200.csv")
    val list = CsvLineParser(file).getList()

    for (i in 2..50) {
        val i1 = getPDF(list.filter { it.iteration <= i - 1 })

        val i2 = getPDF(list.filter { it.iteration <= i })

        val l12 = Math.pow(2.0, -1 * KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(i1.toDoubleArray(), i2.toDoubleArray()))
        val l21 = Math.pow(2.0, -1 * KolmogorovSmirnovTest().kolmogorovSmirnovStatistic(i2.toDoubleArray(), i1.toDoubleArray()))

        println("${l12 * l21}, ")
    }
}

private fun getPDF(list: List<CsvLine>): List<Double> {
    val distributionPairs = list.stream().map { org.apache.commons.math3.util.Pair(it, it.value_count.toDouble()) }.toList()
    val ed = EnumeratedDistribution<CsvLine>(distributionPairs)
    val sample = ed.sample(10000).toList() as List<CsvLine>

    var dist = sample.map { it.value }.toMutableList()

    dist.sortBy { it }
    val array = dist.toDoubleArray()
    val y = mutableListOf<Double>() //listOf(2.14E-07..500).flatten().map{ it.toDouble() }
    for (i in 0..1000) {
        y.add(i / 1000000000.0)
    }

    val kd = KernelDensity.computeDensity(EmpiricalDist(array), NormalDist(), y.toDoubleArray())

    return kd.toList()
}