package ch.uzh.ifi.seal.smr.soa.analysis.annotationpresent

import ch.uzh.ifi.seal.smr.soa.analysis.percentage
import ch.uzh.ifi.seal.smr.soa.utils.*
import java.io.File
import kotlin.reflect.KMutableProperty1

private val output = mutableListOf<ResAnnotationPresent>()

fun main() {
    val file = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\current-commit\\merged-isMain.csv")
    val outputFile = File("C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ1_Results\\aggregated\\annotationpresent.csv").toPath()
    val all = CsvResultParser(file).getList()

    output.add(ResAnnotationPresent("${all.size} benchmarks are baseline for percentage"))

    analyze(all, "warmupIterations", Result::warmupIterations, Result::warmupIterationsClass, Result::warmupIterationsMethod)
    analyze(all, "warmupTime", Result::warmupTime, Result::warmupTimeClass, Result::warmupTimeMethod)
    analyze(all, "measurementIterations", Result::measurementIterations, Result::measurementIterationsClass, Result::measurementIterationsMethod)
    analyze(all, "measurementTime", Result::measurementTime, Result::measurementTimeClass, Result::measurementTimeMethod)
    analyze(all, "forks", Result::forks, Result::forksClass, Result::forksMethod)
    analyze(all, "warmupForks", Result::warmupForks, Result::warmupForksClass, Result::warmupForksMethod)
    analyzeMode(all)
    analyzeNothingSet(all)

    OpenCSVWriter.write(outputFile, output, CustomMappingStrategy(ResAnnotationPresent::class.java))
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

    val bothAnnotationsUsedSameValue = notDefault.filter {
        it.modeIsThroughputClass == it.modeIsThroughputMethod && it.modeIsAverageTimeClass == it.modeIsAverageTimeMethod &&
                it.modeIsSampleTimeClass == it.modeIsSampleTimeMethod && it.modeIsSingleShotTimeClass == it.modeIsSingleShotTimeMethod
    }

    val bothAnnotationsUsedDifferentValue = notDefault.filter {
        (it.modeIsThroughputClass != null && it.modeIsThroughputMethod != null && it.modeIsThroughputClass != it.modeIsThroughputMethod) ||
                (it.modeIsAverageTimeClass != null && it.modeIsAverageTimeMethod != null && it.modeIsAverageTimeClass != it.modeIsAverageTimeMethod) ||
                (it.modeIsSampleTimeClass != null && it.modeIsSampleTimeMethod != null && it.modeIsSampleTimeClass != it.modeIsSampleTimeMethod) ||
                (it.modeIsSingleShotTimeClass != null && it.modeIsSingleShotTimeMethod != null && it.modeIsSingleShotTimeClass != it.modeIsSingleShotTimeMethod)
    }

    output.add(ResAnnotationPresent(
            name = "mode",
            annotationPresent = notDefault.size,
            annotationPresentPercentage = percentage(notDefault.size, all.size),
            bothAnnotationsUsedSameValue = bothAnnotationsUsedSameValue.size,
            bothAnnotationsUsedDifferentValue = bothAnnotationsUsedDifferentValue.size
    ))

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
            annotationPresent = selected.size,
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

private fun analyzeNothingSet(all: Set<Result>) {
    val nothingSet = all.nothingSet()

    output.add(ResAnnotationPresent(
            name = "nothingSet",
            annotationPresentPercentage = percentage(nothingSet.size, all.size),
            note = "annotationPresentPercentage = percentage of nothing set"
    ))
}