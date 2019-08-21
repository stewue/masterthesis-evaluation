package ch.uzh.ifi.seal.smr.soa.clone

import ch.uzh.ifi.seal.smr.soa.preprocessing.CsvResultParser
import java.io.File
import java.nio.file.Paths

fun main() {
    val dir = "D:\\projects"
    val inFile = File("C:\\Users\\stewue\\Downloads\\out.csv")
    val res = CsvResultParser(inFile).getList()

    res.forEach {
        val projectName = it.project.replace('/', '#')

        val projectDir = Paths.get(dir, projectName).toFile()
        val c = projectDir.mkdir()

        if (!c) {
            println("${it.project} already exists")
            return@forEach
        }

        val process = Runtime.getRuntime().exec("git clone ${it.cloneUrl} .", null, projectDir)
        process.waitFor()

        println("${it.project} already exists")
    }
}