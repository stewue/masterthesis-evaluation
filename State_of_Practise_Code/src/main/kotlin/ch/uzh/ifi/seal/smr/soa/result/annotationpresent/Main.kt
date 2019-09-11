package ch.uzh.ifi.seal.smr.soa.result.annotationpresent

import ch.uzh.ifi.seal.smr.soa.result.percentage
import ch.uzh.ifi.seal.smr.soa.utils.CsvResultParser
import ch.uzh.ifi.seal.smr.soa.utils.OpenCSVWriter
import ch.uzh.ifi.seal.smr.soa.utils.Result
import java.io.File
import kotlin.reflect.KMutableProperty1

private val output = mutableListOf<ResAnnotationPresent>()

fun main() {
    val file = File("D:\\mp\\current-merged-isMain.csv")
    val outputFile = File("D:\\mp\\out.csv").toPath()
    val all = CsvResultParser(file).getList()

    analyze(all, "warmupIterations", Result::warmupIterations, Result::warmupIterationsClass, Result::warmupIterationsMethod)
    analyze(all, "warmupTime", Result::warmupTime, Result::warmupTimeClass, Result::warmupTimeMethod)
    analyze(all, "measurementIterations", Result::measurementIterations, Result::measurementIterationsClass, Result::measurementIterationsMethod)
    analyze(all, "measurementTime", Result::measurementTime, Result::measurementTimeClass, Result::measurementTimeMethod)
    analyze(all, "forks", Result::forks, Result::forksClass, Result::forksMethod)
    analyze(all, "warmupForks", Result::warmupForks, Result::warmupForksClass, Result::warmupForksMethod)
    analyzeMode(all)

    OpenCSVWriter.write(outputFile, output)
}

private fun analyze(all: Set<Result>, title: String, property: KMutableProperty1<Result, *>, propertyClass: KMutableProperty1<Result, *>, propertyMethod: KMutableProperty1<Result, *>) {
    val list = all.filter { property.get(it) != null }
    val listOnlyClass = all.filter { propertyClass.get(it) != null && propertyMethod.get(it) == null }
    val listOnlyMethod = all.filter { propertyMethod.get(it) != null && propertyClass.get(it) == null }
    val listBothSame = all.filter { propertyMethod.get(it) != null && propertyClass.get(it) != null && propertyMethod.get(it) == propertyClass.get(it) }
    val listBothDifferent = all.filter { propertyMethod.get(it) != null && propertyClass.get(it) != null && propertyMethod.get(it) != propertyClass.get(it) }

    addValue(title, all, list, listOnlyClass, listOnlyMethod, listBothSame, listBothDifferent)
}

private fun analyzeMode(all: Set<Result>) {
    val notDefault = all.filter {
        it.modeIsThroughput == true || it.modeIsAverageTime == true || it.modeIsSampleTime == true || it.modeIsSingleShotTime == true
    }.toSet()
    output.add(ResAnnotationPresent(
            name = "mode",
            annotationPresentPercentage = percentage(notDefault.size, all.size)
    ))
    output.add(ResAnnotationPresent(name = ""))

    analyzeSingleMode(notDefault, "throughput", Result::modeIsThroughput, Result::modeIsThroughputClass, Result::modeIsThroughputMethod)
    analyzeSingleMode(notDefault, "average", Result::modeIsAverageTime, Result::modeIsAverageTimeClass, Result::modeIsAverageTimeMethod)
    analyzeSingleMode(notDefault, "sample", Result::modeIsSampleTime, Result::modeIsSampleTimeClass, Result::modeIsSampleTimeMethod)
    analyzeSingleMode(notDefault, "singleshot", Result::modeIsSingleShotTime, Result::modeIsSingleShotTimeClass, Result::modeIsSingleShotTimeMethod)
}

private fun analyzeSingleMode(all: Set<Result>, title: String, p: KMutableProperty1<Result, *>, pc: KMutableProperty1<Result, *>, pm: KMutableProperty1<Result, *>) {
    val list = all.filter { p.get(it) == true }
    val listOnlyClass = all.filter { pc.get(it) == true && pm.get(it) == null }
    val listOnlyMethod = all.filter { pm.get(it) == true && pc.get(it) == null }
    val listBothSame = all.filter { pm.get(it) == true && pc.get(it) == true }
    val listBothDifferent = all.filter { pm.get(it) != null && pc.get(it) != null && pc.get(it) != pm.get(it) }

    addValue("mode->$title", all, list, listOnlyClass, listOnlyMethod, listBothSame, listBothDifferent, "annotationPresentPercentage = $title is used / BenchmarkMode annotation exists")
}

private fun addValue(title: String, all: Set<Result>, selected: List<Result>, listOnlyClass: List<Result>, listOnlyMethod: List<Result>, listBothSame: List<Result>, listBothDifferent: List<Result>, note: String? = null) {
    output.add(ResAnnotationPresent(
            name = title,
            annotationPresentPercentage = percentage(selected.size, all.size),
            onlyClassAnnotationUsed = listOnlyClass.size,
            onlyClassAnnotationUsedPercentage = percentage(listOnlyClass.size, selected.size),
            onlyMethodAnnotationUsed = listOnlyMethod.size,
            onlyMethodAnnotationUsedPercentage = percentage(listOnlyMethod.size, selected.size),
            bothAnnotationsUsedSameValue = listBothSame.size,
            bothAnnotationsUsedSameValuePercentage = percentage(listBothSame.size, selected.size),
            bothAnnotationsUsedDifferentValue = listBothDifferent.size,
            bothAnnotationsUsedDifferentValuePercentage = percentage(listBothDifferent.size, selected.size),
            note = note
    ))
}