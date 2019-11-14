package ch.uzh.ifi.seal.smr.reconfigure.analysis.reached

import ch.uzh.ifi.seal.smr.reconfigure.CsvResultItemParser
import java.io.File
import java.io.FileWriter

private val input = File("D:\\outputPdfChange.csv")
private val output = FileWriter(File("D:\\outputPdfReached.csv"))

private fun main() {
    val threshold = 0.97
    output.append("project;commit;benchmark;params;reached\n")
    CsvResultItemParser(input).getList().forEach {
        val map = it.getMap()

        var reached = Int.MAX_VALUE
        for (i in 6 until 101) {
            val value = map.getValue(i)
            if(value > threshold){
                reached = i
                break
            }
        }
        output.append(it.getKey().output() + ";" + reached + "\n")
        output.flush()
    }
}