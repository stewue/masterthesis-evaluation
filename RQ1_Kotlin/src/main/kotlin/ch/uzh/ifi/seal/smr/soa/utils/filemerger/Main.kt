package ch.uzh.ifi.seal.smr.soa.utils.filemerger

import java.io.File
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption.READ
import java.nio.file.StandardOpenOption.WRITE

fun main() {
    /*val inputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\projects\\current")
    val mergedFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\out.csv")

    oneOutputFile(inputDir, mergedFile)*/

    val inputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\project-history\\results-1")
    val outputDir = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\project-history\\out")

    mergePerProject(inputDir, outputDir)
}

private fun oneOutputFile(inputDir: File, outputFile: File) {
    outputFile.createNewFile()

    inputDir.walk().forEach {
        if (it.isFile) {
            merge(it, outputFile)
        }
    }
}

private fun mergePerProject(inputDir: File, outputDir: File) {
    inputDir.walk().forEach {
        if (it.isFile) {
            println(it.nameWithoutExtension)
            val project = it.nameWithoutExtension.dropLast(11)
            val outputFile = File(outputDir.absolutePath + "/$project.csv")
            if (!outputFile.exists()) {
                outputFile.createNewFile()
            }
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