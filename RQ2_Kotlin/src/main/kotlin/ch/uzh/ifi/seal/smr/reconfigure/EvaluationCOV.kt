package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import ch.uzh.ifi.seal.smr.reconfigure.statistics.Sampler
import java.io.File
import kotlin.math.abs
import kotlin.math.round

//fun main(){
//    val folder = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\log4j2\\")
//
//    folder.walk().forEach {
//        if(it.isFile){
//            evalBenchmark(it)
//        }
//    }
//}

fun main() {
    val file = File("D:\\rq2\\out100.csv")
    val list = CsvLineParser(file).getList()
    evaluation(list)
}

//private fun evalBenchmark(file: File){
//    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
//    val key = CsvLineKey(project,commit, benchmark, params)
//    val list = CsvLineParser(file).getList()
//    println(key.output())
//    evaluation(list)
//}

private fun evaluation(list: Collection<CsvLine>) {
    val histogram = mutableMapOf<Int, MutableList<HistogramItem>>()

    list.forEach {
        if (histogram[it.iteration] == null) {
            histogram[it.iteration] = mutableListOf()
        }

        val iterationList = histogram.getValue(it.iteration)
        iterationList.add(it.getHistogramItem())
    }

    val sampleSize = 1000

    val sampledHistogram = histogram.map { (iteration, list) ->
        val od = OutlierDetector(10.0, list)
        od.run()
        val sample = Sampler(od.inlier).getSample(sampleSize)
        Pair(iteration, sample)
    }.toMap()

    val all = mutableListOf<HistogramItem>()

    val covs = mutableMapOf<Int, Double>()
    sampledHistogram.forEach { (iteration, iterationList) ->
        all.addAll(iterationList)
        val cov = COV(all, 0.0)
        covs[iteration] = cov.value

        if (iteration >= 5) {
            val i1 = covs.getValue(iteration - 1) - cov.value
            val i2 = covs.getValue(iteration - 2) - cov.value
            val i3 = covs.getValue(iteration - 3) - cov.value
            val i4 = covs.getValue(iteration - 4) - cov.value

            println("${roundDelta(i1)} / ${roundDelta(i2)} / ${roundDelta(i3)} / ${roundDelta(i4)}")

            // TODO absolut or relative change rate?
            if(abs(i1) < 0.005 && abs(i2) < 0.005 && abs(i3) < 0.005 && abs(i4) < 0.005){
                println("iteration: $iteration -> ${cov.value}")
                return
            }
        }
    }
}

private fun roundDelta(value: Double): Double {
    return round(value * 10000) / 10000
}