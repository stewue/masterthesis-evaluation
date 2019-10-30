package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.statistics.pdf.PDF
import org.apache.commons.math3.distribution.EnumeratedDistribution
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

    val ps = mutableMapOf<Int, Double>()

    for (i in 2..10) {
        sample[i] = getSample(list.filter { it.iteration <= i })
        val sample1 = sample.getValue(i - 1)
        val sample2 = sample.getValue(i)

        val pdf = PDF(sample1, sample2, 0.99)
        val p = pdf.value
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

private fun disableSystemErr() {
    System.setErr(PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    }))
}