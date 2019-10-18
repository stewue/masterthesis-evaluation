package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramConverter
import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramItem
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.CI
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import java.io.File

fun main() {
    val outputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-2-local.csv")
    //val outputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-2.csv")

    val list = mutableMapOf<Int, MutableList<HistogramItem>>()

    outputFile.reader().forEachLine {
        if (it == "project;commit;benchmark;params;instance;trial;fork;iteration;mode;unit;value_count;value") {
            return@forEachLine
        }

        val splitted = it.split(";")
        val iteration = splitted[7].toInt()
        val valueCount = splitted[10].toLong()
        val value = splitted[11].toDouble()

        if (list[iteration] == null) {
            list[iteration] = mutableListOf()
        }

        val iterationList = list.getValue(iteration)
        iterationList.add(HistogramItem(0, iteration, value, valueCount))
    }

    val allInlier = mutableListOf<Double>()
    val all = mutableListOf<HistogramItem>()
    list.forEach { (_, iterationList) ->
        val iterationArray = HistogramConverter.toArray(iterationList)
        val od = OutlierDetector(10.0, iterationArray)
        od.run()
        val inlier = od.inlier
        allInlier.addAll(inlier)
        all.addAll(iterationList)

        println(COV.of(inlier))
        val ci = CI(all)
        ci.run()
        //println("[" + ci.lower + ", " + ci.upper + "]")
    }
}

