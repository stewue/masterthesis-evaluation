package ch.uzh.ifi.seal.smr.reconfigure.analysis.boxplotmeanchangerate

import java.io.File
import java.io.FileWriter
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")

    val mean = mutableMapOf<String, List<Double>>()
    val min = mutableMapOf<String, List<Double>>()
    val max = mutableMapOf<String, List<Double>>()
    file.walk().forEach {
        if (it.isFile && it.name == "variability.csv" && it.parent != file.absolutePath) {
            val name = it.parentFile.name
            val triple = eval(it)
            mean[name] = triple.first
            min[name] = triple.second
            max[name] = triple.third
        }
    }

    val path = "C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\"
    val type = "divergence"
    val outputFileMean = Paths.get(path, "boxplotciratio_${type}_mean.csv").toFile()
    writeToFile(mean, outputFileMean)
    val outputFileLower = Paths.get(path, "boxplotciratio_${type}_min.csv").toFile()
    writeToFile(min, outputFileLower)
    val outputFileUpper = Paths.get(path, "boxplotciratio_${type}_max.csv").toFile()
    writeToFile(max, outputFileUpper)
}

private fun eval(file: File): Triple<List<Double>, List<Double>, List<Double>> {
    val listMean = mutableListOf<Double>()
    val listMin = mutableListOf<Double>()
    val listMax = mutableListOf<Double>()
    file.forEachLine {
        if (it == "project;commit;benchmark;params;meanDefault;meanCov;meanCi;meanKld;ciPercentageDefault;ciPercentageCov;ciPercentageCi;ciPercentageKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;ratioMeanCov;ratioLowerCov;ratioUpperCov;ratioMeanCi;ratioLowerCi;ratioUpperCi;ratioMeanKld;ratioLowerKld;ratioUpperKld") {
            return@forEachLine
        }

        val parts = it.split(";")

        // COV
//        val ratioMean = parts[18].toDouble()
//        val ratioLower = parts[19].toDouble()
//        val ratioUpper = parts[20].toDouble()

        // CI
//        val ratioMean = parts[21].toDouble()
//        val ratioLower = parts[22].toDouble()
//        val ratioUpper = parts[23].toDouble()

        // divergence
        val ratioMean = parts[24].toDouble()
        val ratioLower = parts[25].toDouble()
        val ratioUpper = parts[26].toDouble()

        val lowerAbs = abs(1- ratioLower)
        val upperAbs = abs(1- ratioUpper)

        val min = if(ratioLower <= 1 && ratioUpper >= 1){
            0.0
        }else{
            kotlin.math.min(lowerAbs, upperAbs)
        }
        val max = kotlin.math.max(lowerAbs, upperAbs)

        listMean.add(abs(1 - ratioMean))
        listMin.add(min)
        listMax.add(max)
    }

    return Triple(listMean, listMin, listMax)
}

private fun writeToFile(map: Map<String, List<Double>>, outputFile: File){
    val output = FileWriter(outputFile)

    val lastKey = map.keys.last()

    map.keys.forEach { key ->
        output.append(key)
        if (key != lastKey) {
            output.append(";")
        }
    }
    output.appendln()

    val maxLength = map.values.map { it.size }.max()!!
    for (line in 0 until maxLength) {
        map.keys.forEach { key ->
            val list = map.getValue(key)
            if (line < list.size) {
                output.append("${list[line]}")
            }

            if (key != lastKey) {
                output.append(";")
            }
        }
        output.appendln()
    }

    output.flush()
}