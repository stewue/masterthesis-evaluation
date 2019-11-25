package ch.uzh.ifi.seal.smr.reconfigure.I_variability_threshold

import java.io.File
import kotlin.math.abs

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\log4j2\\variability.csv")

    println("project;commit;benchmark;params;absMeanShiftCov;absMeanShiftCi;absMeanShiftKld;ciWidthDefault;ciWidthCov;ciWidthCi;ciWidthKld;ciChangeCov;ciChangeCi;ciChangeKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;effectSizeAndWilcoxonCov;effectSizeAndWilcoxonCi;effectSizeAndWilcoxonKld;ciRatioCov;ciRatioCi;ciRatioKld")
    file.forEachLine {
        if (it == "project;commit;benchmark;params;meanDefault;meanCov;meanCi;meanKld;ciPercentageDefault;ciPercentageCov;ciPercentageCi;ciPercentageKld;effectSizeCov;effectSizeCi;effectSizeKld;wilcoxonCov;wilcoxonCi;wilcoxonKld;ratioLowerCov;ratioUpperCov;ratioLowerCi;ratioUpperCi;ratioLowerKld;ratioUpperKld") {
            return@forEachLine
        }

        val parts = it.split(";")
        val project = parts[0]
        val commit = parts[1]
        val benchmark = parts[2]
        val params = parts[3]
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

        print("$project;$commit;$benchmark;$params")

        print(";${abs((meanCov/meanDefault)-1)}")
        print(";${abs((meanCi/meanDefault)-1)}")
        print(";${abs((meanKld/meanDefault)-1)}")

        val ciPrecentageThreshold = 0.03

        print(";${ciPercentageDefault < ciPrecentageThreshold}")
        print(";${ciPercentageCov < ciPrecentageThreshold}")
        print(";${ciPercentageCi < ciPrecentageThreshold}")
        print(";${ciPercentageKld < ciPrecentageThreshold}")

        print(";${ciPercentageCov / ciPercentageDefault}")
        print(";${ciPercentageCi / ciPercentageDefault}")
        print(";${ciPercentageKld / ciPercentageDefault}")

        val effectSizeThreshold = 0.147

        print(";${effectSizeCov > effectSizeThreshold}")
        print(";${effectSizeCi > effectSizeThreshold}")
        print(";${effectSizeKld > effectSizeThreshold}")

        val pvalue = 0.01

        print(";${wilcoxonCov < pvalue}")
        print(";${wilcoxonCi < pvalue}")
        print(";${wilcoxonKld < pvalue}")

        print(";${effectSizeCov > effectSizeThreshold && wilcoxonCov < pvalue}")
        print(";${effectSizeCi > effectSizeThreshold && wilcoxonCi < pvalue}")
        print(";${effectSizeKld > effectSizeThreshold && wilcoxonKld < pvalue}")

        print(";${ratioLowerCov > 1 || ratioUpperCov < 1}")
        print(";${ratioLowerCi > 1 || ratioUpperCi < 1}")
        print(";${ratioLowerKld > 1 || ratioUpperKld < 1}")

        println("")
    }
}