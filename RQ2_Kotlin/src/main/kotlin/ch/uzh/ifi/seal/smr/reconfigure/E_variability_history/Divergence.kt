package ch.uzh.ifi.seal.smr.reconfigure.E_variability_history

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItemParser
import java.io.File
import java.io.FileWriter
import java.util.*

private val input = File("D:\\outputDivergenceSingle.csv")
private val output = FileWriter(File("D:\\outputDivergenceHistory.csv"))

private fun main() {
    output.append(CsvResultItem.header)

    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for(f in 1 until 6) {
            val iterationMap = map.getValue(f)
            output.append(";;;;;")
            for (i in 6 until 101) {
                val delta0 = iterationMap.getValue(i)
                val delta1 = iterationMap.getValue(i - 1)
                val delta2 = iterationMap.getValue(i - 2)
                val delta3 = iterationMap.getValue(i - 3)
                val delta4 = iterationMap.getValue(i - 4)
                val value = Collections.min(mutableListOf(delta0, delta1, delta2, delta3, delta4))
                output.append(";$value")
            }
        }

        output.append("\n")
        output.flush()
    }
}