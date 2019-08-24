package ch.uzh.ifi.seal.smr.soa.csvmerger

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CsvResultParser(file: File) {

    private var fileReader = BufferedReader(FileReader(file))

    private fun getBean(): CsvToBean<Row> {
        val mappingStrategy = ColumnPositionMappingStrategy<Row>()
        mappingStrategy.type = Row::class.java

        return CsvToBeanBuilder<Row>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSkipLines(1)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .build()
    }

    fun getList(): Set<Row> {
        val result = mutableSetOf<Row>()
        for (line in getBean()) {
            result.add(line)
        }

        fileReader.close()

        return result
    }
}