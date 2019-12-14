package ch.uzh.ifi.seal.smr.reconfigure.analysis.forkcorrelation

import java.io.File
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")

    println("project;benchmark;params;changeRateCov;forksCov;changeRateCi;forksCi;changeRateDivergence;forksDivergence")

    file.walk().forEach {
        if(it.parent == file.absolutePath && it.isDirectory){
            val totalCov = Paths.get(it.absolutePath, "outputCovTotal.csv").toFile()
            val forkCov = parseTotal(totalCov)

            val totalCi = Paths.get(it.absolutePath, "outputCiTotal.csv").toFile()
            val forkCi = parseTotal(totalCi)

            val totalDivergence = Paths.get(it.absolutePath, "outputDivergenceTotal.csv").toFile()
            val forkDivergence = parseTotal(totalDivergence)

            val variability = Paths.get(it.absolutePath, "variability.csv").toFile()
            variability.forEachLine {
                if (it == "project;commit;benchmark;params;meanDefault;meanCov;meanCi;meanKld;ciPercentageDefault;ciPercentageCov;ciPercentageCi;ciPercentageKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;ratioLowerCov;ratioUpperCov;ratioLowerCi;ratioUpperCi;ratioLowerKld;ratioUpperKld") {
                    return@forEachLine
                }

                val parts = it.split(";")
                val project = parts[0]
                val benchmark = parts[2]
                val params = parts[3]
                val meanDefault = parts[4].toDouble()
                val meanCov = parts[5].toDouble()
                val meanCi = parts[6].toDouble()
                val meanKld = parts[7].toDouble()

                val key = "$project;$benchmark;$params"

                println("$project;$benchmark;$params;${meanChangeRate(meanDefault, meanCov)};${forkCov[key]};${meanChangeRate(meanDefault, meanCi)};${forkCi[key]};${meanChangeRate(meanDefault, meanKld)};${forkDivergence[key]}")
            }
        }
    }
}

private fun parseTotal(file: File): Map<String, Int>{
    val ret = mutableMapOf<String, Int>()
    file.forEachLine {
        if (it == "project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5") {
            return@forEachLine
        }

        val parts = it.split(";")
        val project = parts[0]
        val benchmark = parts[2]
        val params = parts[3]
        val reachedForks = getForkValue(parts[9].toInt())

        ret["$project;$benchmark;$params"] = reachedForks
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

private fun getForkValue(value: Int): Int {
    return if (value == Int.MAX_VALUE) {
        5
    } else {
        value
    }
}