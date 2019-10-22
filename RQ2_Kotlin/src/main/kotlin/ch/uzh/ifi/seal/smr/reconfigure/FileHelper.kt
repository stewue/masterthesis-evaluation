package ch.uzh.ifi.seal.smr.reconfigure

import java.io.File

fun main(){
    val file = File("D:\\rq2\\pre\\log4j2.json")

    var i = 0
    file.forEachLine {
        if(i > 20){
            return@forEachLine
        }
        println(it)
        i++
    }
}