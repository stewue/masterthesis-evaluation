package ch.uzh.ifi.seal.smr.soa.jmhversion

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if(args.size != 1){
        println("Needed arguments: inputDirectory")
        exitProcess(-1)
    }

    val inDir = File(args[0])

    inDir.listFiles().forEach {
        if (it.isDirectory) {
            val res = JmhSourceCodeVersionExtractor(it).get()
            println("$it: $res")
        }
    }
}