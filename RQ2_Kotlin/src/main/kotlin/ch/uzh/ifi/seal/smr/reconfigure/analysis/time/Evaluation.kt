package ch.uzh.ifi.seal.smr.reconfigure.analysis.time

import ch.uzh.ifi.seal.smr.reconfigure.utils.std
import java.io.File
import java.io.FileWriter

private val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\projects.csv")
private val output = FileWriter(outputFile)

fun main() {
    val cov = getTimeSaved(File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\cov.csv"))
    val ci = getTimeSaved(File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\ci.csv"))
    val divergence = getTimeSaved(File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\time\\divergence.csv"))

    output.appendln("project;covMean;covStd;ciMean;ciStd;divergenceMean;divergenceStd")
    cov.keys.forEach { project ->
        output.append(project)
        output.append(";${cov.getValue(project).average()}")
        output.append(";${cov.getValue(project).std()}")
        output.append(";${ci.getValue(project).average()}")
        output.append(";${ci.getValue(project).std()}")
        output.append(";${divergence.getValue(project).average()}")
        output.append(";${divergence.getValue(project).std()}")
        output.appendln("")
    }

    output.append("total")
    output.append(";${cov.values.flatten().average()}")
    output.append(";${cov.values.flatten().std()}")
    output.append(";${ci.values.flatten().average()}")
    output.append(";${ci.values.flatten().std()}")
    output.append(";${divergence.values.flatten().average()}")
    output.append(";${divergence.values.flatten().std()}")
    output.appendln("")
    output.flush()
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