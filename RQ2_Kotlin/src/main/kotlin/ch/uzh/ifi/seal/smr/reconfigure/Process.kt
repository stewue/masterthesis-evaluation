package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.D_variability_single.D_All
import ch.uzh.ifi.seal.smr.reconfigure.E_variability_history.E_All
import ch.uzh.ifi.seal.smr.reconfigure.F_variability_reached.F_All
import ch.uzh.ifi.seal.smr.reconfigure.G_variability_total.G_All
import ch.uzh.ifi.seal.smr.reconfigure.H_compare_criteria.Evaluation
import java.io.File

fun main() {
//    val csvInput = File("D:\\rq2\\results\\csv")
//    val outputDir = "D:\\tmp"
        val csvInput = File("/home/user/stefan-masterthesis/statistics/csv-jenetics")
    val outputDir = "/home/user/stefan-masterthesis/statistics/tmp"

    val d = D_All(csvInput, outputDir)
    d.run()
    println("End step D")

    val e = E_All(outputDir)
    e.run()
    println("End step E")

    val f = F_All(outputDir)
    f.run()
    println("End step F")

    val g = G_All(csvInput.absolutePath, outputDir)
    g.run()
    println("End step G")

    val h = Evaluation(csvInput, outputDir)
    h.run()
    println("End step H")
}