package ch.uzh.ifi.seal.smr.soa.utils

import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CsvResultParser(file: File) {

    private var fileReader = BufferedReader(FileReader(file))

    private fun getBean(): CsvToBean<Result> {
        val mappingStrategy = CustomColumnPositionMappingStrategy<Result>()
        mappingStrategy.type = Result::class.java

        return CsvToBeanBuilder<Result>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .build()
    }

    fun getList(): Set<Result> {
        val result = getBean().toSet()

        fileReader.close()

        return result
    }
}