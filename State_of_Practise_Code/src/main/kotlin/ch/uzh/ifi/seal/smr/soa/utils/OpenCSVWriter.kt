package ch.uzh.ifi.seal.smr.soa.utils

import com.opencsv.CSVWriter
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.StatefulBeanToCsvBuilder

import java.nio.file.Files
import java.nio.file.Path

object OpenCSVWriter {
    inline fun <reified T> write(outputFile: Path, input: Iterable<T>, mapping: ColumnPositionMappingStrategy<T> = ColumnPositionMappingStrategy()) {
        if (!outputFile.toFile().exists()) {
            outputFile.toFile().createNewFile()
        }

        Files.newBufferedWriter(outputFile).use { writer ->
            val beanToCsv = StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withMappingStrategy(mapping)
                    .build()

            beanToCsv.write(input.toList())
        }
    }
}