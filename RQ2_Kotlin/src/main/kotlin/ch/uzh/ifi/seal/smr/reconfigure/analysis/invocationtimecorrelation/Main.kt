package ch.uzh.ifi.seal.smr.reconfigure.analysis.invocationtimecorrelation

import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")

    println("project;benchmark;params;changeRateCov;meanCov;changeRateCi;meanCi;changeRateDivergence;meanDivergence")
    file.walk().forEach {
        if (it.isFile && it.name == "variability.csv" && it.parent != file.absolutePath) {
            it.forEachLine {
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

                println("$project;$benchmark;$params;${meanChangeRate(meanDefault, meanCov)};$meanCov ;${meanChangeRate(meanDefault, meanCi)};$meanCi;${meanChangeRate(meanDefault, meanKld)};$meanKld")
            }
        }
    }
}

private fun meanChangeRate(before: Double, after: Double): Double {
    return if ((after / before).isNaN()) {
        0.0
    } else {
        abs((after / before) - 1)
    }
}