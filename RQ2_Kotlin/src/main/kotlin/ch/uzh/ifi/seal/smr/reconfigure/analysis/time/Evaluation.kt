package ch.uzh.ifi.seal.smr.reconfigure.analysis.time

import ch.uzh.ifi.seal.smr.reconfigure.utils.std
import java.io.File

fun main() {
    val cov = getTimeSaved(File("D:\\cov.csv"))
    val ci = getTimeSaved(File("D:\\ci.csv"))
    val divergence = getTimeSaved(File("D:\\divergence.csv"))

    println("project;covMean;covStd;ciMean;ciStd;divergenceMean;divergenceStd")
    cov.keys.forEach { project ->
        print(project)
        print(";${cov.getValue(project).average()}")
        print(";${cov.getValue(project).std()}")
        print(";${ci.getValue(project).average()}")
        print(";${ci.getValue(project).std()}")
        print(";${divergence.getValue(project).average()}")
        print(";${divergence.getValue(project).std()}")
        println("")
    }

    print("total")
    print(";${cov.values.flatten().average()}")
    print(";${cov.values.flatten().std()}")
    print(";${ci.values.flatten().average()}")
    print(";${ci.values.flatten().std()}")
    print(";${divergence.values.flatten().average()}")
    print(";${divergence.values.flatten().std()}")
    println("")
}

private fun getTimeSaved(file: File): Map<String, List<Double>> {
    val ret = mutableMapOf<String, MutableList<Double>>()

    file.forEachLine {
        if (it == "project;commits;benchmarks;params;forks;iteration1;iteration2;iteration3;iteration4;iteration5;time") {
            return@forEachLine
        }

        val parts = it.split(";")
        val project = parts[0]
        val time = parts[10].toDouble()

        if (ret[project] == null) {
            ret[project] = mutableListOf()
        }

        val timeSaved = (500 - time) / 500
        ret.getValue(project).add(timeSaved)
    }

    return ret
}