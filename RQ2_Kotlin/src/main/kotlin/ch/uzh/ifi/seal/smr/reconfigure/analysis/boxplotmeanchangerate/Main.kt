package ch.uzh.ifi.seal.smr.reconfigure.analysis.boxplotmeanchangerate

import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")

    val map = mutableMapOf<String, List<Double>>()
    file.walk().forEach {
        if (it.isFile && it.name == "variability.csv" && it.parent != file.absolutePath) {
            val name = it.parentFile.name
            val list = eval(it)
            map[name] = list
        }
    }

    val lastKey = map.keys.last()

    map.keys.forEach { key ->
        print(key)
        if (key != lastKey) {
            print(";")
        }
    }
    println("")

    val maxLength = map.values.map { it.size }.max()!!
    for (line in 0 until maxLength) {
        map.keys.forEach { key ->
            val list = map.getValue(key)
            if (line < list.size) {
                print("${list[line]}")
            }

            if (key != lastKey) {
                print(";")
            }
        }
        println("")
    }
}

private fun eval(file: File): List<Double> {
    val ret = mutableListOf<Double>()
    file.forEachLine {
        if (it == "project;commit;benchmark;params;meanDefault;meanCov;meanCi;meanKld;ciPercentageDefault;ciPercentageCov;ciPercentageCi;ciPercentageKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;ratioLowerCov;ratioUpperCov;ratioLowerCi;ratioUpperCi;ratioLowerKld;ratioUpperKld") {
            return@forEachLine
        }

        val parts = it.split(";")
        val meanDefault = parts[4].toDouble()
        val meanKld = parts[7].toDouble()

        ret.add(meanChangeRate(meanDefault, meanKld))
    }

    return ret
}


private fun meanChangeRate(before: Double, after: Double): Double {
    return if ((after / before).isNaN()) {
        0.0
    } else {
        abs((after / before) - 1)
    }
}