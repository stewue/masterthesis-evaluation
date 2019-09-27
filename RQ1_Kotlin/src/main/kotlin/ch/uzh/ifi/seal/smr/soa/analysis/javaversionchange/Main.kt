package ch.uzh.ifi.seal.smr.soa.analysis.javaversionchange

import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import java.io.File

fun main() {
    val fileHistory = File("D:\\mp\\history-all.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()

    val items = CsvResultParser(fileHistory).getList()
    val output = mutableListOf<ResJavaVersionChange>()

    val changed = items.filter{!it.javaTarget.isNullOrBlank() || !it.javaTarget.isNullOrBlank()}
            .map { it.project to if(it.javaTarget == null){
                it.javaSource
            }else{
                it.javaTarget
            }
            }
            .distinct()
            .groupBy { it.first }

    changed.forEach { (project, list) ->
        output.add(ResJavaVersionChange(project, list.size - 1))
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResJavaVersionChange::class.java))
}