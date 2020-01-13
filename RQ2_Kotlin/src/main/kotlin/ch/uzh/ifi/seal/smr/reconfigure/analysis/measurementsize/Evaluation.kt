package ch.uzh.ifi.seal.smr.reconfigure.analysis.measurementsize

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\measurementsize\\measurementsize_allfork.csv")

    var counter = 0.0
    var combination5significant = 0
    var combination10significant = 0
    var combination15significant = 0
    var combination20significant = 0
    var combination25significant = 0
    var ciRatio5significant = 0
    var ciRatio10significant = 0
    var ciRatio15significant = 0
    var ciRatio20significant = 0
    var ciRatio25significant = 0

    input.forEachLine { line ->
        if (line == "project;commit;benchmark;params;effectSize5;effectSize10;effectSize15;effectSize20;effectSize25;wilcoxon5;wilcoxon10;wilcoxon15;wilcoxon20;wilcoxon25;ciRatio5;ciRatio10;ciRatio15;ciRatio20;ciRatio25") {
            return@forEachLine
        }

        val parts = line.split(';')
        val effectSize5 = parts[4].toDouble()
        val effectSize10 = parts[5].toDouble()
        val effectSize15 = parts[6].toDouble()
        val effectSize20 = parts[7].toDouble()
        val effectSize25 = parts[8].toDouble()
        val wilcoxon5 = parts[9].toDouble()
        val wilcoxon10 = parts[10].toDouble()
        val wilcoxon15 = parts[11].toDouble()
        val wilcoxon20 = parts[12].toDouble()
        val wilcoxon25 = parts[13].toDouble()
        val ciRatio5 = parts[14].toDouble()
        val ciRatio10 = parts[15].toDouble()
        val ciRatio15 = parts[16].toDouble()
        val ciRatio20 = parts[17].toDouble()
        val ciRatio25 = parts[18].toDouble()

        if (checkCombination(effectSize5, wilcoxon5)) {
            combination5significant++
        }
        if (checkCombination(effectSize10, wilcoxon10)) {
            combination10significant++
        }
        if (checkCombination(effectSize15, wilcoxon15)) {
            combination15significant++
        }
        if (checkCombination(effectSize20, wilcoxon20)) {
            combination20significant++
        }
        if (checkCombination(effectSize25, wilcoxon25)) {
            combination25significant++
        }

        if (checkCIRatio(ciRatio5)) {
            ciRatio5significant++
        }
        if (checkCIRatio(ciRatio10)) {
            ciRatio10significant++
        }
        if (checkCIRatio(ciRatio15)) {
            ciRatio15significant++
        }
        if (checkCIRatio(ciRatio20)) {
            ciRatio20significant++
        }
        if (checkCIRatio(ciRatio25)) {
            ciRatio25significant++
        }

        counter++
    }

    println("combination5significant: ${combination5significant / counter}")
    println("combination10significant: ${combination10significant / counter}")
    println("combination15significant: ${combination15significant / counter}")
    println("combination20significant: ${combination20significant / counter}")
    println("combination25significant: ${combination25significant / counter}")
    println("ciRatio5significant: ${ciRatio5significant / counter}")
    println("ciRatio10significant: ${ciRatio10significant / counter}")
    println("ciRatio15significant: ${ciRatio15significant / counter}")
    println("ciRatio20significant: ${ciRatio20significant / counter}")
    println("ciRatio25significant: ${ciRatio25significant / counter}")
}

private fun checkCombination(effectSize: Double, wilcoxon: Double): Boolean {
    return abs(effectSize) > 0.147 && wilcoxon < 0.01
}

private fun checkCIRatio(ratio: Double): Boolean {
    return ratio > 0.01
}