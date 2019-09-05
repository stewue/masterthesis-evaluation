package ch.uzh.ifi.seal.smr.soa.utils

import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CsvProjectParser(file: File) {

    private var fileReader = BufferedReader(FileReader(file))

    private fun getBean(): CsvToBean<Row> {
        val mappingStrategy = ColumnPositionMappingStrategy<Row>()
        mappingStrategy.type = Row::class.java

        return CsvToBeanBuilder<Row>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .build()
    }

    fun getList(): Set<Row> {
        val result = getBean().toSet()

        fileReader.close()

        return result
    }
}