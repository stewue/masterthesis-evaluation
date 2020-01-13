package ch.uzh.ifi.seal.smr.reconfigure.analysis.shorteriterations

import ch.uzh.ifi.seal.smr.reconfigure.utils.std
import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\shorteriterations\\variability.csv")
    val nrDatapointsThreshold = 25

    val covDiffs = mutableListOf<Double>()
    val ciDiffs = mutableListOf<Double>()
    val divergenceDiffs = mutableListOf<Double>()
    var divergenceBothSmallChange = 0
    var counter = 0.0

    input.forEachLine { line ->
        val parts = line.split(';')
        val nrDatapoints = parts[4].toDouble()
        val cov10 = parts[5].toDouble()
        val cov100 = parts[6].toDouble()
        val ci10 = parts[7].toDouble()
        val ci100 = parts[8].toDouble()
        val divergence10 = parts[9].toDouble()
        val divergence100 = parts[10].toDouble()

        if (nrDatapoints >= nrDatapointsThreshold) {
            covDiffs.add(cov10 - cov100)
            ciDiffs.add(ci10 - ci100)
            divergenceDiffs.add(divergence10 - divergence100)
            counter++
            if (divergence10 > 0.99 && divergence100 > 0.99) {
                divergenceBothSmallChange++
            }
        }
    }

    println("$nrDatapointsThreshold per iteration necessary")
    println("~~COV~~")
    println("difference: ${covDiffs.average()} +/- ${covDiffs.std()}")
    println("")
    println("~~CI~~~")
    println("difference: ${ciDiffs.average()} +/- ${ciDiffs.std()}")
    println("difference larger is larger than 0.01 with 100 iterations: ${ciDiffs.filter { it < -0.01 }.size / counter}")
    println("")
    println("~~Divergence~~")
    println("difference: ${divergenceBothSmallChange / counter}")
    println("p-value difference < 0.01: ${divergenceDiffs.filter { abs(it) < 0.01 }.size / counter}")
}