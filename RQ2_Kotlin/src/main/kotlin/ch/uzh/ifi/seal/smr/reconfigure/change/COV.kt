package ch.uzh.ifi.seal.smr.reconfigure.change

import ch.uzh.ifi.seal.smr.reconfigure.CsvResultItemParser
import java.io.File
import java.io.FileWriter
import java.util.*

private val input = File("D:\\outputCov.csv")
private val output = FileWriter(File("D:\\outputCovChange.csv"))

private fun main() {
    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        output.append(it.getKey().output())
        for (i in 5 until 101) {
            val current = map.getValue(i)
            val delta1 = map.getValue(i - 1) - current
            val delta2 = map.getValue(i - 2) - current
            val delta3 = map.getValue(i - 3) - current
            val delta4 = map.getValue(i - 4) - current
            val value = Collections.max(mutableListOf(delta1, delta2, delta3, delta4))
            output.append(";$value")
        }
        output.append("\n")
        output.flush()
    }
}