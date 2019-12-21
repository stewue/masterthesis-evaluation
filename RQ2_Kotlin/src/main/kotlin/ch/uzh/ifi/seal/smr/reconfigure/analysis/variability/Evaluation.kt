package ch.uzh.ifi.seal.smr.reconfigure.analysis.variability

import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")

    println("project;benchmarks;meanChangeRateCov;meanChangeRateCi;meanChangeRateDivergence;CiWidthSmallDefault;combinationSignificantCov;combinationSignificantCi;combinationSignificantDivergence;ciRatioSignificantCov;ciRatioSignificantCi;ciRatioSignificantDivergence;ciRatioShiftCov;ciRatioShiftCi;ciRatioShiftDivergence")

    file.walk().forEach {
        if (it.isFile && it.name == "variability.csv" && it.parent != file.absolutePath) {
            val name = it.parentFile.name
            eval(name, it)
        }
    }

}

private fun eval(name: String, file: File) {
    var counter = 0.0

    val meanChangeRateCov = mutableListOf<Double>()
    val meanChangeRateCi = mutableListOf<Double>()
    val meanChangeRateDivergence = mutableListOf<Double>()

    var ciWidthSmallerDefault = 0

    var combinationCov = 0
    var combinationCi = 0
    var combinationDivergence = 0

    var ciRatioCov = 0
    var ciRatioCi = 0
    var ciRatioDivergence = 0

    val ciRatioListCov = mutableListOf<Double>()
    val ciRatioListCi = mutableListOf<Double>()
    val ciRatioListDivergence = mutableListOf<Double>()

    file.forEachLine {
        if (it == "project;commit;benchmark;params;meanDefault;meanCov;meanCi;meanKld;ciPercentageDefault;ciPercentageCov;ciPercentageCi;ciPercentageKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;ratioLowerCov;ratioUpperCov;ratioLowerCi;ratioUpperCi;ratioLowerKld;ratioUpperKld") {
            return@forEachLine
        }

        val parts = it.split(";")
        val meanDefault = parts[4].toDouble()
        val meanCov = parts[5].toDouble()
        val meanCi = parts[6].toDouble()
        val meanKld = parts[7].toDouble()
        val ciPercentageDefault = parts[8].toDouble()
        val ciPercentageCov = parts[9].toDouble()
        val ciPercentageCi = parts[10].toDouble()
        val ciPercentageKld = parts[11].toDouble()
        val effectSizeCov = parts[12].toDouble()
        val effectSizeCi = parts[13].toDouble()
        val effectSizeKld = parts[14].toDouble()
        val wilcoxonCov = parts[15].toDouble()
        val wilcoxonCi = parts[16].toDouble()
        val wilcoxonKld = parts[17].toDouble()
        val ratioLowerCov = parts[18].toDouble()
        val ratioUpperCov = parts[19].toDouble()
        val ratioLowerCi = parts[20].toDouble()
        val ratioUpperCi = parts[21].toDouble()
        val ratioLowerKld = parts[22].toDouble()
        val ratioUpperKld = parts[23].toDouble()

        meanChangeRateCov.add(meanChangeRate(meanDefault, meanCov))
        meanChangeRateCi.add(meanChangeRate(meanDefault, meanCi))
        meanChangeRateDivergence.add(meanChangeRate(meanDefault, meanKld))

        val ciPrecentageThreshold = 0.03
        if (ciPercentageDefault < ciPrecentageThreshold) {
            ciWidthSmallerDefault++
        }

        val effectSizeThreshold = 0.147
        val pvalue = 0.01
        if (effectSizeCov > effectSizeThreshold && wilcoxonCov < pvalue) {
            combinationCov++
        }
        if (effectSizeCi > effectSizeThreshold && wilcoxonCi < pvalue) {
            combinationCi++
        }
        if (effectSizeKld > effectSizeThreshold && wilcoxonKld < pvalue) {
            combinationDivergence++
        }

        ciRatioListCov.add(ciRatioPercentage(ratioLowerCov, ratioUpperCov))
        if (ciRatioPercentage(ratioLowerCov, ratioUpperCov) > 0.01) {
            ciRatioCov++
        }
        ciRatioListCi.add(ciRatioPercentage(ratioLowerCi, ratioUpperCi))
        if (ciRatioPercentage(ratioLowerCi, ratioUpperCi) > 0.01) {
            ciRatioCi++
        }
        ciRatioListDivergence.add(ciRatioPercentage(ratioLowerKld, ratioUpperKld))
        if (ciRatioPercentage(ratioLowerKld, ratioUpperKld) > 0.01) {
            ciRatioDivergence++
        }

        counter++
    }

    print("$name;$counter")
    print(";${meanChangeRateCov.average()};${meanChangeRateCi.average()};${meanChangeRateDivergence.average()}")
    print(";${ciWidthSmallerDefault / counter}")
    print(";${combinationCov / counter};${combinationCi / counter};${combinationDivergence / counter}")
    print(";${ciRatioCov / counter};${ciRatioCi / counter};${ciRatioDivergence / counter}")
    println(";${ciRatioListCov.average()};${ciRatioListCi.average()};${ciRatioListDivergence.average()}")
}


private fun meanChangeRate(before: Double, after: Double): Double {
    return if ((after / before).isNaN()) {
        0.0
    } else {
        abs((after / before) - 1)
    }
}

private fun ciRatioPercentage(lower: Double, upper: Double): Double {
    return when {
        lower > 1 -> lower - 1
        upper < 1 -> 1 - upper
        else -> 0.0
    }
}