package ch.uzh.ifi.seal.smr.reconfigure.E_variability_history

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem.Companion.header
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItemParser
import java.io.File
import java.io.FileWriter
import java.util.*

private val input = File("D:\\outputCovSingle.csv")
private val output = FileWriter(File("D:\\outputCovHistory.csv"))

private fun main() {
    output.append(header)

    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for(f in 1 until 6) {
            val iterationMap = map.getValue(f)
            output.append(";;;;")
            for (i in 5 until 101) {
                val current = iterationMap.getValue(i)
                val delta1 = iterationMap.getValue(i - 1) - current
                val delta2 = iterationMap.getValue(i - 2) - current
                val delta3 = iterationMap.getValue(i - 3) - current
                val delta4 = iterationMap.getValue(i - 4) - current
                val value = Collections.max(mutableListOf(delta1, delta2, delta3, delta4))
                output.append(";$value")
            }
        }
        output.append("\n")
        output.flush()
    }
}