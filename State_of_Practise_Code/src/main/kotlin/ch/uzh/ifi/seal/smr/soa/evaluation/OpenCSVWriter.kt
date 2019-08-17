package ch.uzh.ifi.seal.smr.soa.evaluation

import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder

import java.nio.file.Files
import java.nio.file.Path

object OpenCSVWriter {
    fun <T> write(outputFile: Path, input: Iterable<T>) {
        if (!outputFile.toFile().exists()) {
            outputFile.toFile().createNewFile()
        }

        Files.newBufferedWriter(outputFile).use { writer ->
            val beanToCsv = StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build()

            beanToCsv.write(input.toList())
        }
    }
}