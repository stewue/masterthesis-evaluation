package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.statistics.pdf.PDF
import org.apache.commons.math3.distribution.EnumeratedDistribution
import java.io.File
import java.io.FileWriter
import java.io.OutputStream
import java.io.PrintStream
import kotlin.streams.toList

private val outputPdf = FileWriter(File("/home/user/stefan-masterthesis/outputPdf.csv"))
private val outputPdfMin = FileWriter(File("/home/user/stefan-masterthesis/outputPdfMin.csv"))

fun evalBenchmarkPdf(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList().map { it.getHistogramItem() }
    outputPdf.append(key.output())
    outputPdfMin.append(key.output())
    evaluation(list)
    outputPdf.appendln("")
    outputPdfMin.appendln("")
    outputPdf.flush()
    outputPdfMin.flush()
}

private fun evaluation(list: List<HistogramItem>) {
    val sample = mutableMapOf<Int, List<Double>>()

    sample[1] = getSample(list.filter { it.iteration == 1 })

    val ps = mutableMapOf<Int, Double>()

    val count = list.map{it.iteration}.distinct().size

    for (i in 2..count) {
        sample[i] = getSample(list.filter { it.iteration <= i })
        val sample1 = sample.getValue(i - 1)
        val sample2 = sample.getValue(i)

        val pdf = PDF(sample1, sample2, 0.99)
        val p = pdf.value
        ps[i] = p

        outputPdf.append(";$p")

        if (i >= 6) {
            val p1 = ps.getValue(i - 1)
            val p2 = ps.getValue(i - 2)
            val p3 = ps.getValue(i - 3)
            val p4 = ps.getValue(i - 4)

            val max = getMin(listOf(p1, p2, p3, p4, p))
            outputPdfMin.append(";$max")
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

fun disableSystemErr() {
    System.setErr(PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    }))
}