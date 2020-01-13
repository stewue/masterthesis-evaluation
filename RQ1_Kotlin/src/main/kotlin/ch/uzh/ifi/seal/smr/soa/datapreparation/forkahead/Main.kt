package ch.uzh.ifi.seal.smr.soa.datapreparation.forkahead

import ch.uzh.ifi.seal.smr.soa.datapreparation.github.mapper
import ch.uzh.ifi.seal.smr.soa.utils.CsvProjectParser
import ch.uzh.ifi.seal.smr.soa.utils.CustomMappingStrategy
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

val token = "c41cda64dd374ba55dcab819dc32807cc5248bae"

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Datasets\\preprocessed_repo_list_additional_information.csv")
    val projects = CsvProjectParser(file).getList()

    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\forkahead.csv").toPath()
    val output = mutableListOf<ResForkAhead>()

    val defaultBranch = projects.map { it.project to it.defaultBranch }.toMap()

    val filtered = projects.filter { it.repoAvailable == true && it.mainRepo != true }

    filtered.forEachIndexed { i, item ->
        val project = item.project
        val projectUser = project.substringBefore("/")
        val projectDefaultBranch = defaultBranch[project]

        val parent = item.parentProject
        val parentDefaultBranch = defaultBranch[parent]

        val url = "https://api.github.com/repos/$parent/compare/$parentDefaultBranch...$projectUser:$projectDefaultBranch"
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        con.setRequestProperty("Authorization", "token $token")

        println("(${con.getHeaderField("X-RateLimit-Remaining")}) $project: ${con.responseCode}")

        if (con.responseCode == 404) {
            output.add(ResForkAhead(project, true))
        } else {
            val jsonString = IOUtils.toString(con.inputStream, Charset.defaultCharset())
            val json = mapper.readTree(jsonString)

            val aheadBy = json["ahead_by"].intValue()
            val behindBy = json["behind_by"].intValue()
            val nothingChanged = aheadBy == 0

            output.add(ResForkAhead(project, false, aheadBy, behindBy, nothingChanged))
        }
    }

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResForkAhead::class.java))
}