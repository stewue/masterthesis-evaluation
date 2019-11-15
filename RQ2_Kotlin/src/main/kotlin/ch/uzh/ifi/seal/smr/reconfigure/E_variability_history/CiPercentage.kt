package ch.uzh.ifi.seal.smr.reconfigure.E_variability_history

import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItem.Companion.header
import ch.uzh.ifi.seal.smr.reconfigure.utils.CsvResultItemParser
import java.io.File
import java.io.FileWriter
import java.util.*

private val input = File("D:\\outputCiSingle.csv")
private val output = FileWriter(File("D:\\outputCiHistory.csv"))

private fun main() {
    output.append(header)

    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for(f in 1 until 6) {
            val iterationMap = map.getValue(f)
            output.append(";;;;")
            for (i in 5 until 101) {
                val width0 = iterationMap.getValue(i)
                val width1 = iterationMap.getValue(i - 1)
                val width2 = iterationMap.getValue(i - 2)
                val width3 = iterationMap.getValue(i - 3)
                val width4 = iterationMap.getValue(i - 4)
                val value = Collections.max(mutableListOf(width0, width1, width2, width3, width4))
                output.append(";$value")
            }
        }
        output.append("\n")
        output.flush()
    }
}