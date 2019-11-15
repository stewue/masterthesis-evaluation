package ch.uzh.ifi.seal.smr.reconfigure.F_variability_reached

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItemParser
import java.io.File
import java.io.FileWriter

private val input = File("D:\\outputDivergenceHistory.csv")
private val output = FileWriter(File("D:\\outputDivergenceReached.csv"))

private fun main() {
    val threshold = 0.97
    output.append("project;commit;benchmark;params;f1;f2;f3;f4;f5\n")
    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for (f in 1 until 6) {
            var reached = Int.MAX_VALUE
            val iterationMap = map.getValue(f)
            for (i in 6 until 101) {
                val value = iterationMap.getValue(i)
                if (value > threshold) {
                    reached = i
                    break
                }
            }
            output.append(";" + reached)
        }
        output.append("\n")
        output.flush()
    }
}