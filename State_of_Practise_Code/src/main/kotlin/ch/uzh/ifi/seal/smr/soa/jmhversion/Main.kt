package ch.uzh.ifi.seal.smr.soa.jmhversion

import java.io.File

val dir = File("D:/projects")

fun main() {
    dir.listFiles().forEach {
        if (it.isDirectory) {
            JmhSourceCodeVersionExtractor(it).get()
        }
    }
}