package ch.uzh.ifi.seal.smr.soa.mavencentral

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.apache.commons.io.IOUtils
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

fun main() {
    val input = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Code\\Evaluation\\State_of_Practise_Code\\src\\main\\kotlin\\ch\\uzh\\ifi\\seal\\smr\\soa\\mavencentral\\input.txt")

    input.forEachLine {
        val (groupId, artifactId) = it.split("/", limit = 2)

        val version = getVersion(groupId, artifactId)
        if (version != null) {
            val res = getGit(groupId, artifactId, version)
            if(res != null){
                val connection = res["connection"]
                if(connection != null){
                    val connectionValue = connection.textValue()
                    if(connectionValue.contains("github")){
                        println(connectionValue)
                    }
                }
            }
        }
    }
}

val xmlMapper = XmlMapper()

fun getVersion(groupId: String, artifactId: String): String? {
    val url = "https://repo1.maven.org/maven2/${groupId.replace('.', '/')}/${artifactId.replace('.', '/')}/maven-metadata.xml"
    val obj = URL(url)
    val con = obj.openConnection() as HttpURLConnection
    con.requestMethod = "GET"

    return if (con.responseCode == 200) {
        val stringValue = IOUtils.toString(con.inputStream, Charset.defaultCharset())
        val node = xmlMapper.readTree(stringValue)
        node["versioning"]["release"].textValue()
    } else {
        null
    }
}

fun getGit(groupId: String, artifactId: String, version: String): JsonNode? {
    val artifactIdDots = artifactId.replace('.', '/')
    val url = "https://repo1.maven.org/maven2/${groupId.replace('.', '/')}/$artifactIdDots/$version/$artifactIdDots-$version.pom"
    val obj = URL(url)
    val con = obj.openConnection() as HttpURLConnection
    con.requestMethod = "GET"

    return if (con.responseCode == 200) {
        val stringValue = IOUtils.toString(con.inputStream, Charset.defaultCharset())
        val node = xmlMapper.readTree(stringValue)

        if(node["scm"] == null){
            null
        }else{
            node["scm"]
        }
    } else {
        null
    }
}