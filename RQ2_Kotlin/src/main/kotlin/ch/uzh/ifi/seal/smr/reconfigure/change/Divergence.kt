package ch.uzh.ifi.seal.smr.reconfigure.change

import ch.uzh.ifi.seal.smr.reconfigure.CsvResultItemParser
import java.io.File
import java.io.FileWriter
import java.util.*

private val input = File("D:\\outputDivergence.csv")
private val output = FileWriter(File("D:\\outputDivergenceChange.csv"))

private fun main() {
    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for (i in 6 until 101) {
            val delta0 = map.getValue(i)
            val delta1 = map.getValue(i - 1)
            val delta2 = map.getValue(i - 2)
            val delta3 = map.getValue(i - 3)
            val delta4 = map.getValue(i - 4)
            val value = Collections.min(mutableListOf(delta0, delta1, delta2, delta3, delta4))
            output.append(";$value")
        }
        output.append("\n")
        output.flush()
    }
}