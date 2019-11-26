package ch.uzh.ifi.seal.smr.reconfigure.process.C_json_to_csv

import ch.uzh.ifi.seal.bencher.jmhResults.JMHResultTransformer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Paths

fun main() {
    val inputFolder = File("D:\\rq2\\results\\results-jenetics")
    val outputFolder = File("D:\\rq2\\results\\csv-jenetics")
    inputFolder.walk().forEach { inputFile ->
        if (inputFile.isFile) {
            val outputFile = Paths.get(outputFolder.absolutePath, inputFile.nameWithoutExtension + ".csv").toFile()

            val parser = JMHResultTransformer(inStream = FileInputStream(inputFile), outStream = FileOutputStream(outputFile), trial = 0, commit = "", project = "", instance = "")
            val error = parser.execute()
            if (error.isDefined()) {
                throw RuntimeException("Execution failed with '${error.get()}'")
            }
        }
    }
}

