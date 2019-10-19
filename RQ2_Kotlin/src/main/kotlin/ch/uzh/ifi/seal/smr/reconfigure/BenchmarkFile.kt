package ch.uzh.ifi.seal.smr.reconfigure

import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.reconfigure.statistics.ci.OpenCSVWriter
import java.io.File

fun main(){
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\log4j2.csv")
    val list = CsvLineParser(file).getList()
    val grouped = list.groupBy { it.getKey() }

    grouped.forEach { (key, list) ->
        val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\pre\\log4j2\\${key.output()}.csv")
        OpenCSVWriter.write(file,list, CustomMappingStrategy<CsvLine>(CsvLine::class.java))
    }
}