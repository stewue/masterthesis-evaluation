package ch.uzh.ifi.seal.smr.soa.utils

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.evaluation.Status
import com.opencsv.bean.AbstractCsvConverter
import com.opencsv.bean.ColumnPositionMappingStrategy
import com.opencsv.bean.CsvConverter
import java.lang.reflect.Field


class CustomColumnPositionMappingStrategy<T> : ColumnPositionMappingStrategy<T>() {
    override fun determineConverter(field: Field?, elementType: Class<*>?, locale: String?, customConverter: Class<out AbstractCsvConverter>?): CsvConverter {
        if (elementType == JMHVersion::class.java) {
            return ConverterJmhVersion()
        } else if (elementType == Status::class.java) {
            return ConvertStatus()
        } else {
            return super.determineConverter(field, elementType, locale, customConverter)
        }
    }
}

private class ConverterJmhVersion : AbstractCsvConverter() {
    override fun convertToRead(value: String?): Any? {
        return if (value == null || value.isNullOrBlank()) {
            null
        } else {
            val list = value.split(".")
            val major = list[0].toInt()
            val minor = list[1].toInt()
            val patch = list.getOrNull(2)?.toInt() ?: 0
            JMHVersion(major, minor, patch)
        }
    }
}

private class ConvertStatus : AbstractCsvConverter() {
    override fun convertToRead(value: String?): Any? {
        return when (value) {
            "BOTH_SET" -> Status.BOTH_SET
            "BOTH_UNSET" -> Status.BOTH_UNSET
            "TIME_SET" -> Status.TIME_SET
            "UNIT_SET" -> Status.UNIT_SET
            else -> null
        }
    }
}