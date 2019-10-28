package ch.uzh.ifi.seal.smr.reconfigure

import java.io.File

private val header = "project;commit;benchmark;params;instance;trial;fork;iteration;mode;unit;value_count;value"

fun main() {
    val file = File("D:\\rq2\\pre\\log4j2_100_iterations_1_second.csv")

    var keyLast: String? = null
    var list = mutableListOf<String>()
    file.forEachLine {
        if (it == header) {
            return@forEachLine
        }
        val splits = it.split(";")

        val project = splits[0]
        val commit = splits[1]
        val benchmark = splits[2]
        val params = splits[3]
        val key = "$project;$commit;$benchmark;$params"

        if (key != keyLast && keyLast != null) {
            val file = File("D:\\rq2\\pre\\rxjava_100_iterations_1_second\\${keyLast}.csv")
            file.writeText("$header\n")
            file.appendText(list.joinToString(separator = "\n"))
            list = mutableListOf()
        }
        list.add(it)
        keyLast = key
    }

    val lastFile = File("D:\\rq2\\pre\\rxjava_100_iterations_1_second\\${keyLast}.csv")
    lastFile.writeText("$header\n")
    lastFile.appendText(list.joinToString(separator = "\n"))
}