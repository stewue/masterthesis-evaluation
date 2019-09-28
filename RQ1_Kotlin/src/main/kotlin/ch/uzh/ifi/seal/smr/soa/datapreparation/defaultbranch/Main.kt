package ch.uzh.ifi.seal.smr.soa.datapreparation.defaultbranch

import ch.uzh.ifi.seal.smr.soa.datapreparation.github.mapper
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.Row
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

val token = ""

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val projects = CsvProjectParser(file).getList()

    projects.forEachIndexed { i, item ->
        val project = item.project

        val url = "https://api.github.com/repos/$project"
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        con.setRequestProperty("Authorization", "token $token")

        println("(${con.getHeaderField("X-RateLimit-Remaining")}) $project: ${con.responseCode}")

        if (con.responseCode != 404) {
            val jsonString = IOUtils.toString(con.inputStream, Charset.defaultCharset())
            val json = mapper.readTree(jsonString)

            item.defaultBranch = json["default_branch"].textValue()
        }
    }

    OpenCSVWriter.write(file.toPath(), projects, CustomMappingStrategy(Row::class.java))
}