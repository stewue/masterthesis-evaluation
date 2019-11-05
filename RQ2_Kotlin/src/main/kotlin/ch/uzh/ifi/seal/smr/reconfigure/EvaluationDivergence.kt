package ch.uzh.ifi.seal.smr.reconfigure

import org.openjdk.jmh.reconfigure.helper.HistogramItem
import org.openjdk.jmh.reconfigure.statistics.divergence.Divergence
import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.SAMPLE_SIZE
import java.io.File
import java.io.FileWriter
import java.io.OutputStream
import java.io.PrintStream
import kotlin.streams.toList

private val outputDivergence = FileWriter(File("/home/user/stefan-masterthesis/outputDivergence.csv"))
private val outputDivergenceMin = FileWriter(File("/home/user/stefan-masterthesis/outputDivergenceMin.csv"))

fun evalBenchmarkDivergence(file: File) {
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project, commit, benchmark, params)
    val list = CsvLineParser(file).getList().map { it.getHistogramItem() }
    outputDivergence.append(key.output())
    outputDivergenceMin.append(key.output())
    evaluation(list)
    outputDivergence.appendln("")
    outputDivergenceMin.appendln("")
    outputDivergence.flush()
    outputDivergenceMin.flush()
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

        val p = Divergence(sample1, sample2).value
        ps[i] = p

        outputDivergence.append(";$p")

        if (i >= 6) {
            val p1 = ps.getValue(i - 1)
            val p2 = ps.getValue(i - 2)
            val p3 = ps.getValue(i - 3)
            val p4 = ps.getValue(i - 4)

            val max = getMin(listOf(p1, p2, p3, p4, p))
            outputDivergenceMin.append(";$max")
        }
    }
}

private fun getSample(list: List<HistogramItem>): List<Double> {
    val distributionPairs = list.stream().map { org.apache.commons.math3.util.Pair(it.value, it.count.toDouble()) }.toList()
    val ed = EnumeratedDistribution<Double>(distributionPairs)
    val sample = ed.sample(SAMPLE_SIZE).toList().toMutableList() as MutableList<Double>
    sample.sortBy { it }
    return sample
}

fun disableSystemErr() {
    System.setErr(PrintStream(object : OutputStream() {
        override fun write(b: Int) {}
    }))
}