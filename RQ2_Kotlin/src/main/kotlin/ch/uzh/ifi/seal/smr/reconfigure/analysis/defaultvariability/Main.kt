package ch.uzh.ifi.seal.smr.reconfigure.analysis.defaultvariability

import ch.uzh.ifi.seal.smr.reconfigure.utils.firstFork
import ch.uzh.ifi.seal.smr.reconfigure.utils.getHistogramItems
import org.openjdk.jmh.reconfigure.statistics.evaluation.CiPercentageEvaluation
import org.openjdk.jmh.reconfigure.statistics.evaluation.CovEvaluation
import org.openjdk.jmh.reconfigure.statistics.evaluation.DivergenceEvaluation
import org.openjdk.jmh.runner.Defaults
import java.io.File
import java.io.FileWriter

fun main() {
    val csvInput = File("D:\\rq2\\results\\csv-log4j2")
    val output = FileWriter(File("D:\\defaultvariability.csv"))

    output.appendln("project;commit;benchmark;params;i96Cov;i97Cov;i98Cov;i99Cov;i100Cov;maxDeltaCov;i96Ci;i97Ci;i98Ci;i99Ci;i100Ci;maxDeltaCi;maxCi;i96Divergence;i97Divergence;i98Divergence;i99Divergence;i100Divergence;averageDivergence")

    csvInput.walk().forEach {
        if (it.isFile) {
            try {
                val name = it.nameWithoutExtension
                val all = getHistogramItems(it, firstFork)

                val (project, benchmark, params) = name.split("#")

                output.append("$project;;$benchmark;$params")

                val evaluationCov = CovEvaluation.getIterationInstance(Defaults.RECONFIGURE_COV_THRESHOLD)
                val evaluationCi = CiPercentageEvaluation.getIterationInstance(Defaults.RECONFIGURE_CI_THRESHOLD)
                val evaluationDivergence = DivergenceEvaluation.getIterationInstance(Defaults.RECONFIGURE_KLD_THRESHOLD)

                for (iteration in 1..100) {
                    val list = all.filter { it.iteration <= iteration }

                    evaluationCov.addIteration(list)
                    evaluationCi.addIteration(list)
                    evaluationDivergence.addIteration(list)
                }

                for (iteration in 96..100) {
                    val current = evaluationCov.getCovOfIteration(iteration)
                    output.append(";$current")
                }

                val variabilityCov = evaluationCov.calculateVariability()
                output.append(";$variabilityCov")

                val ci = mutableListOf<Double>()
                for (iteration in 96..100) {
                    val current = evaluationCi.getCiPercentageOfIteration(iteration)
                    ci.add(current)
                    output.append(";$current")
                }

                val variabilityCi = evaluationCi.calculateVariability()
                output.append(";$variabilityCi;${ci.max()}")

                for (iteration in 96..100) {
                    val current = evaluationDivergence.getPValueOfIteration(iteration)
                    output.append(";$current")
                }

                val variabilityDivergence = evaluationDivergence.calculateVariability()
                output.appendln(";$variabilityDivergence")
                output.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}