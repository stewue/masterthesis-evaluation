package ch.uzh.ifi.seal.smr.reconfigure.G_variability_total

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvLineParser
import org.openjdk.jmh.reconfigure.statistics.evaluation.CiPercentageEvaluation
import java.io.FileWriter
import java.nio.file.Paths

class CiPercentage(private val csvInput: String, outputDir: String, private val threshold: Double = 0.03) {
    private val inputReached = Paths.get(outputDir, "outputCiReached.csv").toFile()
    private val output = FileWriter(Paths.get(outputDir, "outputCiTotal.csv").toFile())

    fun run() {

        output.append("project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5\n")

        inputReached.forEachLine {
            if (it == "project;commit;benchmark;params;f1;f2;f3;f4;f5") {
                return@forEachLine
            }

            val parts = it.split(";")
            val project = parts[0]
            val commit = parts[1]
            val benchmark = parts[2]
            val params = parts[3]
            val reached = mapOf(
                    1 to parts[4].toInt(),
                    2 to parts[5].toInt(),
                    3 to parts[6].toInt(),
                    4 to parts[7].toInt(),
                    5 to parts[8].toInt()
            )

            output.append("$project;$commit;$benchmark;$params;$threshold")

            val file = Paths.get(csvInput, "$project#$benchmark#$params.csv").toFile()
            val list = CsvLineParser(file).getList()

            val evaluation = CiPercentageEvaluation(threshold)
            var thresholdReached = Int.MAX_VALUE
            for (f in 1 until 6) {
                var reachedFork = reached.getValue(f)
                if (reachedFork > 50) {
                    reachedFork = 50
                }

                val measurements = list.filter { it.fork == f && it.iteration > reachedFork && it.iteration <= reachedFork + 10 }.map { it.getHistogramItem() }
                evaluation.addIteration(measurements)
                evaluation.calculateVariability()
                val currentValue = evaluation.getCiPercentageOfIteration(f)

                if (f > 1) {
                    output.append(";$currentValue")

                    if (thresholdReached == Int.MAX_VALUE && currentValue < threshold) {
                        thresholdReached = f
                    }
                }
            }

            output.append(";$thresholdReached")

            reached.forEach { (_, iteration) ->
                output.append(";$iteration")
            }
            output.append("\n")
            output.flush()
        }
    }
}