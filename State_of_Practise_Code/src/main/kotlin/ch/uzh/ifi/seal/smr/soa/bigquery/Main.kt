package ch.uzh.ifi.seal.smr.soa.bigquery

import ch.uzh.ifi.seal.smr.soa.evaluation.OpenCSVWriter
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    val inFile = File("C:\\Users\\stewue\\Downloads\\jmh-projects-bigquery-fh-201702.csv")
    val outFile = "C:\\Users\\stewue\\Downloads\\out.csv"
    val res = CsvResultParser(inFile).getList()

    res.forEach {
        val url = "https://github.com/" + it.project
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "GET"

        println("${it.project}: ${con.responseCode}")

        it.exist = con.responseCode != 404

        Thread.sleep(500)
    }

    OpenCSVWriter.write(outFile, res)
}