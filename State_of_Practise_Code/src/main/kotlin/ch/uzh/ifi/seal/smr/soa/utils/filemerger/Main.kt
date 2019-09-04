package ch.uzh.ifi.seal.smr.soa.utils.filemerger

import java.io.File
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.WRITE

fun main() {
    val inputDirectory = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\projects\\current")
    val mergedFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\out.csv")
    mergedFile.createNewFile()

    inputDirectory.walk().forEach {
        if (it.isFile) {
            FileChannel.open(mergedFile.toPath(), WRITE).use { dest: FileChannel ->
                // append to destination file
                dest.position(dest.size())
                FileChannel.open(it.toPath(), READ).use { src: FileChannel ->
                    var p: Long = 0
                    val l = src.size()
                    while (p < l) {
                        p += src.transferTo(p, l - p, dest)
                    }
                }
            }
        }
    }
}