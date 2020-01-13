package ch.uzh.ifi.seal.smr.soa.utils.filemerger

import java.io.File
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.WRITE

fun main() {
    val inputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\results")
    val mergedFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged.csv")

    oneOutputFile(inputDir, mergedFile)
}

private fun oneOutputFile(inputDir: File, outputFile: File) {
    outputFile.createNewFile()

    inputDir.walk().forEach {
        if (it.isFile) {
            merge(it, outputFile)
        }
    }
}

private fun merge(inputFile: File, outputFile: File) {
    FileChannel.open(outputFile.toPath(), WRITE).use { dest: FileChannel ->
        // append to destination file
        dest.position(dest.size())
        FileChannel.open(inputFile.toPath(), READ).use { src: FileChannel ->
            var p: Long = 0
            val l = src.size()
            while (p < l) {
                p += src.transferTo(p, l - p, dest)
            }
        }
    }
}