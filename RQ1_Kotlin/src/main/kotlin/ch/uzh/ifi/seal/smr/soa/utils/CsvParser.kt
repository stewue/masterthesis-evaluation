package ch.uzh.ifi.seal.smr.soa.utils

import ch.uzh.ifi.seal.smr.soa.analysis.executiontime.ResExecutionTime
import ch.uzh.ifi.seal.smr.soa.analysis.jmh121update.ResJmh121Update
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class CsvProjectParser(file: File) : CsvParser<Row>(file, Row::class.java, true)
class CsvResultParser(file: File) : CsvParser<Result>(file, Result::class.java, false)
class CsvResExecutionTimeParser(file: File) : CsvParser<ResExecutionTime>(file, ResExecutionTime::class.java, true)
class CsvResJmh121Update(file: File) : CsvParser<ResJmh121Update>(file, ResJmh121Update::class.java, true)

open class CsvParser<T>(file: File, private val clazz: Class<T>, private val skipHeader: Boolean) {

    private var fileReader = BufferedReader(FileReader(file))

    private fun getBean(): CsvToBean<T> {
        val mappingStrategy = CustomColumnPositionMappingStrategy<T>()
        mappingStrategy.type = clazz

        val builder = CsvToBeanBuilder<T>(fileReader)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)

        if (skipHeader) {
            builder.withSkipLines(1)
        }

        return builder.build()
    }

    fun getList(): Set<T> {
        val result = getBean().toSet()

        fileReader.close()

        return result
    }
}