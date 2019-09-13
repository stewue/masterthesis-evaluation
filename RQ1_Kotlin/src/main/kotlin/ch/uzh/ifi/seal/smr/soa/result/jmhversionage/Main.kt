package ch.uzh.ifi.seal.smr.soa.result.jmhversionage

import ch.uzh.ifi.seal.smr.soa.result.jmhDate
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import java.io.File
import kotlin.math.round

private const val yearInSeconds = 31536000.0

fun main() {
    val file = File("D:\\mp\\current-merged-isMain.csv")
    val items = CsvResultParser(file).getList()

    println("project,benchmarks,time")
    items.filter { it.jmhVersion != null }
            .groupBy { it.project }
            .map { (_, items) ->
                Pair(items.first(), items.size)
            }
            .forEach { (item, count) ->
                val jmhVersionDate = jmhDate.getValue(item.jmhVersion!!)
                val timeDiff = round((item.commitTime!! - jmhVersionDate) / yearInSeconds * 100) / 100.0

                println("${item.project},$count,$timeDiff")
            }
}