package ch.uzh.ifi.seal.smr.soa.bigquery

import ch.uzh.ifi.seal.smr.soa.evaluation.OpenCSVWriter
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

val mapper = ObjectMapper()
// TODO entfernen
val token = ""

fun main() {
    val inFile = File("C:\\Users\\stewue\\Downloads\\jmh-projects-bigquery-fh-201702.csv")
    val outFile = File("C:\\Users\\stewue\\Downloads\\out.csv").toPath()
    val res = CsvResultParser(inFile).getList()

    var i = 0
    res.forEach { p ->
        //if (i < 10) {
        val url = "https://api.github.com/repos/" + p.project
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        con.setRequestProperty("Authorization", "token $token")

        println("${p.project}: ${con.responseCode}")

        if (con.responseCode == 404) {
            p.repoAvailable = false
            p.selectedForStudy = false
        } else {
            val jsonString = IOUtils.toString(con.inputStream, Charset.defaultCharset())
            val json = mapper.readTree(jsonString)

            val fork = json["fork"].booleanValue()

            p.forked = fork
            p.watchers = json["watchers_count"].intValue()
            p.stars = json["stargazers_count"].intValue()
            p.forks = json["forks_count"].intValue()
            p.subscribers = json["subscribers_count"].intValue()
            p.repoAvailable = true
            p.cloneUrl = json["clone_url"].textValue()
            p.lastUpdate = json["updated_at"].textValue()
            p.archived = json["archived"].booleanValue()
            p.disabled = json["disabled"].booleanValue()

            if (fork) {
                p.parentProject = json["parent"]["full_name"].textValue()
                p.parentInList = res.any { s -> s.project == p.parentProject }
                p.duplicatedFork = res.any { s -> s != p && s.parentProject == p.parentProject }
                p.selectedForStudy = p.parentInList!!.not() && p.duplicatedFork!!.not()
            } else {
                p.selectedForStudy = true
            }

            if (p.selectedForStudy == true) {
                p.numberOfReleases = getNumberOfReleases(p.project)
                p.numberOfCommits = getNumberOfCommits(p.project)
            }
        }
        //}

        i++
    }

    OpenCSVWriter.write(outFile, res)
}

fun getNumberOfReleases(name: String): Int {
    val url = "https://api.github.com/repos/$name/releases?per_page=100"
    val obj = URL(url)
    val con = obj.openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    con.setRequestProperty("Authorization", "token $token")

    val jsonString = IOUtils.toString(con.inputStream, Charset.defaultCharset())
    val json = mapper.readTree(jsonString)

    return json.count()
}

fun getNumberOfCommits(name: String): Int {
    val url = "https://api.github.com/repos/$name/commits?per_page=100"
    val obj = URL(url)
    val con = obj.openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    con.setRequestProperty("Authorization", "token $token")

    val jsonString = IOUtils.toString(con.inputStream, Charset.defaultCharset())
    val json = mapper.readTree(jsonString)

    return json.count()
}