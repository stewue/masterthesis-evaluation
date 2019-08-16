package ch.uzh.ifi.seal.smr.soa.evaluation

import com.opencsv.CSVWriter
import com.opencsv.bean.StatefulBeanToCsvBuilder

import java.nio.file.Files
import java.nio.file.Paths

object OpenCSVWriter {
    fun <T> write(fileName: String, input: Iterable<T>) {

        Files.newBufferedWriter(Paths.get(fileName)).use { writer ->
            val beanToCsv = StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build()

            beanToCsv.write(input.toList())
        }
    }
}