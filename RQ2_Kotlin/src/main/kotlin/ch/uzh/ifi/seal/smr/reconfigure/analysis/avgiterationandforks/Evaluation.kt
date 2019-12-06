package ch.uzh.ifi.seal.smr.reconfigure.analysis.avgiterationandforks

import java.io.File
import java.nio.file.Paths

fun main() {
    println("project;benchmarks;avgReachedForkCov;avgReachedIterationCov;perForkNotStableCov;perIterationNotStableCov;avgReachedForkCi;avgReachedIterationCi;perForkNotStableCi;perIterationNotStableCi;avgReachedForkDivergence;avgReachedIterationDivergence;perForkNotStableDivergence;perIterationNotStableDivergence")
    val input = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\variability\\")
    input.list().forEach {
        val folder = Paths.get(input.absolutePath, it).toFile()

        if (folder.isDirectory) {
            print(it)
            val covFile = Paths.get(folder.absolutePath, "outputCovTotal.csv").toFile()
            run(covFile, true)
            val ciFile = Paths.get(folder.absolutePath, "outputCiTotal.csv").toFile()
            run(ciFile, false)
            val divergenceFile = Paths.get(folder.absolutePath, "outputDivergenceTotal.csv").toFile()
            run(divergenceFile, false)
            println("")
        }
    }
}

private fun run(file: File, printSize: Boolean = false) {
    var counter = 0.0
    var reachedFork = mutableListOf<Int>()
    var reachedIteration = mutableListOf<Int>()
    var forkNotStable = 0
    var iterationNotStable = 0

    file.forEachLine {
        if (it == "project;commit;benchmark;params;threshold;f2;f3;f4;f5;reachedForks;reachedF1;reachedF2;reachedF3;reachedF4;reachedF5") {
            return@forEachLine
        }

        val parts = it.split(";")


        reachedFork.add(getForkValue(parts[9].toInt()))

        reachedIteration.add(getIterationValue(parts[10].toInt()))
        reachedIteration.add(getIterationValue(parts[11].toInt()))
        reachedIteration.add(getIterationValue(parts[12].toInt()))
        reachedIteration.add(getIterationValue(parts[13].toInt()))
        reachedIteration.add(getIterationValue(parts[14].toInt()))

        if (parts[9].toInt() == Int.MAX_VALUE) {
            forkNotStable++
        }

        if (parts[10].toInt() == Int.MAX_VALUE) {
            iterationNotStable++
        }
        if (parts[11].toInt() == Int.MAX_VALUE) {
            iterationNotStable++
        }
        if (parts[12].toInt() == Int.MAX_VALUE) {
            iterationNotStable++
        }
        if (parts[13].toInt() == Int.MAX_VALUE) {
            iterationNotStable++
        }
        if (parts[14].toInt() == Int.MAX_VALUE) {
            iterationNotStable++
        }

        counter++
    }

    val avgReachedFork = reachedFork.average()
    val avgReachedIteration = reachedIteration.average()
    val perForkNotStable = forkNotStable / counter
    val perIterationNotStable = iterationNotStable / (counter * 5)

    if (printSize) {
        print(";$counter")
    }
    print(";$avgReachedFork;$avgReachedIteration;$perForkNotStable;$perIterationNotStable")
}

private fun getForkValue(value: Int): Int {
    return if (value == Int.MAX_VALUE) {
        5
    } else {
        value
    }
}

private fun getIterationValue(value: Int): Int {
    return if (value == Int.MAX_VALUE) {
        50
    } else {
        value
    }
}

