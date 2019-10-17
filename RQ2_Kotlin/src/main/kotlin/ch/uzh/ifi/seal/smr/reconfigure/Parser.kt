package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.helper.HistogramConverter
import ch.uzh.ifi.seal.smr.reconfigure.helper.OutlierDetector
import ch.uzh.ifi.seal.smr.reconfigure.statistics.COV
import java.io.File

fun main() {
    val inputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out.json")
    val outputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-5.csv")

//    val parser = JMHResultTransformer(inStream = FileInputStream(inputFile), outStream = FileOutputStream(outputFile), trial = 0, commit = "", project = "", instance = "")
//    val error = parser.execute()
//    if (error.isDefined()) {
//        throw RuntimeException("Execution failed with '${error.get()}'")
//    }

    val list = mutableMapOf<Int, MutableList<org.apache.commons.math3.util.Pair<Double, Long>>>()

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
        iterationList.add(org.apache.commons.math3.util.Pair(value, valueCount))
    }

    val all = mutableListOf<Double>()
    list.forEach { (_, iterationList) ->
        val iterationArray = HistogramConverter.toArray(iterationList)
        val od = OutlierDetector(10.0, iterationArray)
        od.run()
        val inlier = od.inlier
        all.addAll(inlier)
        println(COV.of(inlier))
    }
}

