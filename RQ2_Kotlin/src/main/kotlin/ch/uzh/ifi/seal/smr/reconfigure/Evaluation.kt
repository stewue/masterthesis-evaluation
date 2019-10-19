package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramConverter
import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.CI
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import java.io.File

fun main(){
    val folder = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\log4j2\\")

    folder.walk().forEach {
        if(it.isFile){
            evalBenchmark(it)
        }
    }
}

//fun main(){
//    //evalBenchmark(File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\log4j2\\;;org.apache.logging.log4j.perf.jmh.AsyncAppenderLog4j1Benchmark.throughput9Params;.csv"))
//    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\out500.csv")
//    val list = CsvLineParser(file).getList()
//    evaluation(list)
//    println("")
//}

private fun evalBenchmark(file: File){
    val (project, commit, benchmark, params) = file.nameWithoutExtension.split(";")
    val key = CsvLineKey(project,commit, benchmark, params)
    val list = CsvLineParser(file).getList()
    print(key.output())
    evaluation(list)
    println("")
}

private fun evaluation(list: Collection<CsvLine>){
    val histogram = mutableMapOf<Int, MutableList<HistogramItem>>()

    list.forEach {
        if (histogram[it.iteration] == null) {
            histogram[it.iteration] = mutableListOf()
        }

        val iterationList = histogram.getValue(it.iteration)
        iterationList.add(it.getHistogramItem())
    }

    val allInlier = mutableListOf<Double>()
    val all = mutableListOf<HistogramItem>()
    histogram.forEach { (_, iterationList) ->
        all.addAll(iterationList)
        val iterationArray = HistogramConverter.toArray(iterationList)
        val od = OutlierDetector(10.0, iterationArray)
        od.run()
        val inlier = od.inlier
        allInlier.addAll(inlier)
        //print(";" + COV.of(allInlier))
        //println("")

        val median = HistogramConverter.toArray(all).median()
        val filtered = all.filter { it.value < median * 10 }
        val ci = CI(filtered)
        ci.run()
        print(";${(ci.upper - ci.lower)/HistogramConverter.toArray(filtered).median()}")
        //println(";${ci.lower};${ci.upper};${HistogramConverter.toArray(filtered).median()}")
    }
    //print(";" + allInlier.median())
}

fun List<Double>.median() = sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }