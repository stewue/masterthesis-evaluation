package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.bencher.jmhResults.JMHResultTransformer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val inputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-2-local.json")
    val outputFile = File("D:\\projects\\apache#logging-log4j2\\log4j-perf\\target\\out-log4j2-2-local.csv")

    val parser = JMHResultTransformer(inStream = FileInputStream(inputFile), outStream = FileOutputStream(outputFile), trial = 0, commit = "", project = "", instance = "")
    val error = parser.execute()
    if (error.isDefined()) {
        throw RuntimeException("Execution failed with '${error.get()}'")
    }
}

