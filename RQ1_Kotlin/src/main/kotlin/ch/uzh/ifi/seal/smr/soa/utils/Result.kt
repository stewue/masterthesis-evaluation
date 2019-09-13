package ch.uzh.ifi.seal.smr.soa.utils

import ch.uzh.ifi.seal.bencher.JMHVersion
import ch.uzh.ifi.seal.smr.soa.analysis.Status
import com.opencsv.bean.CsvBindByPosition

class Result {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    var commitId: String? = null

    @CsvBindByPosition(position = 2)
    var commitTime: Int? = null

    @CsvBindByPosition(position = 3)
    var jmhVersion: JMHVersion? = null

    @CsvBindByPosition(position = 4)
    var javaTarget: String? = null

    @CsvBindByPosition(position = 5)
    var javaSource: String? = null

    @CsvBindByPosition(position = 6)
    lateinit var className: String

    @CsvBindByPosition(position = 7)
    lateinit var benchmarkName: String

    @CsvBindByPosition(position = 8)
    var warmupIterations: Int? = null

    @CsvBindByPosition(position = 9)
    var warmupIterationsClass: Int? = null

    @CsvBindByPosition(position = 10)
    var warmupIterationsMethod: Int? = null

    @CsvBindByPosition(position = 11)
    var warmupTime: Long? = null

    @CsvBindByPosition(position = 12)
    var warmupTimeClass: Long? = null

    @CsvBindByPosition(position = 13)
    var warmupTimeMethod: Long? = null

    @CsvBindByPosition(position = 14)
    lateinit var warmupTimeStatus: Status

    @CsvBindByPosition(position = 15)
    lateinit var warmupTimeStatusClass: Status

    @CsvBindByPosition(position = 16)
    lateinit var warmupTimeStatusMethod: Status

    @CsvBindByPosition(position = 17)
    var measurementIterations: Int? = null

    @CsvBindByPosition(position = 18)
    var measurementIterationsClass: Int? = null

    @CsvBindByPosition(position = 19)
    var measurementIterationsMethod: Int? = null

    @CsvBindByPosition(position = 20)
    var measurementTime: Long? = null

    @CsvBindByPosition(position = 21)
    var measurementTimeClass: Long? = null

    @CsvBindByPosition(position = 22)
    var measurementTimeMethod: Long? = null

    @CsvBindByPosition(position = 23)
    lateinit var measurementTimeStatus: Status

    @CsvBindByPosition(position = 24)
    lateinit var measurementTimeStatusClass: Status

    @CsvBindByPosition(position = 25)
    lateinit var measurementTimeStatusMethod: Status

    @CsvBindByPosition(position = 26)
    var forks: Int? = null

    @CsvBindByPosition(position = 27)
    var forksClass: Int? = null

    @CsvBindByPosition(position = 28)
    var forksMethod: Int? = null

    @CsvBindByPosition(position = 29)
    var warmupForks: Int? = null

    @CsvBindByPosition(position = 30)
    var warmupForksClass: Int? = null

    @CsvBindByPosition(position = 31)
    var warmupForksMethod: Int? = null

    @CsvBindByPosition(position = 32)
    var modeIsThroughput: Boolean? = null

    @CsvBindByPosition(position = 33)
    var modeIsThroughputClass: Boolean? = null

    @CsvBindByPosition(position = 34)
    var modeIsThroughputMethod: Boolean? = null

    @CsvBindByPosition(position = 35)
    var modeIsAverageTime: Boolean? = null

    @CsvBindByPosition(position = 36)
    var modeIsAverageTimeClass: Boolean? = null

    @CsvBindByPosition(position = 37)
    var modeIsAverageTimeMethod: Boolean? = null

    @CsvBindByPosition(position = 38)
    var modeIsSampleTime: Boolean? = null

    @CsvBindByPosition(position = 39)
    var modeIsSampleTimeClass: Boolean? = null

    @CsvBindByPosition(position = 40)
    var modeIsSampleTimeMethod: Boolean? = null

    @CsvBindByPosition(position = 41)
    var modeIsSingleShotTime: Boolean? = null

    @CsvBindByPosition(position = 42)
    var modeIsSingleShotTimeClass: Boolean? = null

    @CsvBindByPosition(position = 43)
    var modeIsSingleShotTimeMethod: Boolean? = null

    @CsvBindByPosition(position = 44)
    var parametrizationCombinations: Int = 0

    @CsvBindByPosition(position = 45)
    var paramString: String? = null

    @CsvBindByPosition(position = 46)
    var paramCount: Int = 0

    @CsvBindByPosition(position = 47)
    var paramCountStateObject: Int = 0

    @CsvBindByPosition(position = 48)
    var paramCountStateObjectWithoutJmhParam: Int = 0

    @CsvBindByPosition(position = 49)
    var hasBlackhole: Boolean = false

    @CsvBindByPosition(position = 50)
    var hasControl: Boolean = false

    @CsvBindByPosition(position = 51)
    var hasBenchmarkParams: Boolean = false

    @CsvBindByPosition(position = 52)
    var hasIterationParams: Boolean = false

    @CsvBindByPosition(position = 53)
    var hasThreadParams: Boolean = false

    @CsvBindByPosition(position = 54)
    var jmhParamString: String? = null

    @CsvBindByPosition(position = 55)
    var jmhParamPairs: String? = null

    @CsvBindByPosition(position = 56)
    var jmhParamCount: Int = 0

    @CsvBindByPosition(position = 57)
    var jmhParamCountOwnClass: Int = 0

    @CsvBindByPosition(position = 58)
    var jmhParamCountArgument: Int = 0

    @CsvBindByPosition(position = 59)
    var returnType: String? = null

    @CsvBindByPosition(position = 60)
    var partOfGroup: Boolean = false

    @CsvBindByPosition(position = 61)
    lateinit var methodHash: String

    constructor()
    constructor(project: String, commitId: String?, commitTime: Int?, jmhVersion: JMHVersion?, javaTarget: String?, javaSource: String?, className: String, benchmarkName: String, warmupIterations: Int?, warmupIterationsClass: Int?, warmupIterationsMethod: Int?, warmupTime: Long?, warmupTimeClass: Long?, warmupTimeMethod: Long?, warmupTimeStatus: Status, warmupTimeStatusClass: Status, warmupTimeStatusMethod: Status, measurementIterations: Int?, measurementIterationsClass: Int?, measurementIterationsMethod: Int?, measurementTime: Long?, measurementTimeClass: Long?, measurementTimeMethod: Long?, measurementTimeStatus: Status, measurementTimeStatusClass: Status, measurementTimeStatusMethod: Status, forks: Int?, forksClass: Int?, forksMethod: Int?, warmupForks: Int?, warmupForksClass: Int?, warmupForksMethod: Int?, modeIsThroughput: Boolean?, modeIsThroughputClass: Boolean?, modeIsThroughputMethod: Boolean?, modeIsAverageTime: Boolean?, modeIsAverageTimeClass: Boolean?, modeIsAverageTimeMethod: Boolean?, modeIsSampleTime: Boolean?, modeIsSampleTimeClass: Boolean?, modeIsSampleTimeMethod: Boolean?, modeIsSingleShotTime: Boolean?, modeIsSingleShotTimeClass: Boolean?, modeIsSingleShotTimeMethod: Boolean?, parametrizationCombinations: Int, paramString: String?, paramCount: Int, paramCountStateObject: Int, paramCountStateObjectWithoutJmhParam: Int, hasBlackhole: Boolean, hasControl: Boolean, hasBenchmarkParams: Boolean, hasIterationParams: Boolean, hasThreadParams: Boolean, jmhParamString: String?, jmhParamPairs: String?, jmhParamCount: Int, jmhParamCountOwnClass: Int, jmhParamCountArgument: Int, returnType: String?, partOfGroup: Boolean, methodHash: String) {
        this.project = project
        this.commitId = commitId
        this.commitTime = commitTime
        this.jmhVersion = jmhVersion
        this.javaTarget = javaTarget
        this.javaSource = javaSource
        this.className = className
        this.benchmarkName = benchmarkName
        this.warmupIterations = warmupIterations
        this.warmupIterationsClass = warmupIterationsClass
        this.warmupIterationsMethod = warmupIterationsMethod
        this.warmupTime = warmupTime
        this.warmupTimeClass = warmupTimeClass
        this.warmupTimeMethod = warmupTimeMethod
        this.warmupTimeStatus = warmupTimeStatus
        this.warmupTimeStatusClass = warmupTimeStatusClass
        this.warmupTimeStatusMethod = warmupTimeStatusMethod
        this.measurementIterations = measurementIterations
        this.measurementIterationsClass = measurementIterationsClass
        this.measurementIterationsMethod = measurementIterationsMethod
        this.measurementTime = measurementTime
        this.measurementTimeClass = measurementTimeClass
        this.measurementTimeMethod = measurementTimeMethod
        this.measurementTimeStatus = measurementTimeStatus
        this.measurementTimeStatusClass = measurementTimeStatusClass
        this.measurementTimeStatusMethod = measurementTimeStatusMethod
        this.forks = forks
        this.forksClass = forksClass
        this.forksMethod = forksMethod
        this.warmupForks = warmupForks
        this.warmupForksClass = warmupForksClass
        this.warmupForksMethod = warmupForksMethod
        this.modeIsThroughput = modeIsThroughput
        this.modeIsThroughputClass = modeIsThroughputClass
        this.modeIsThroughputMethod = modeIsThroughputMethod
        this.modeIsAverageTime = modeIsAverageTime
        this.modeIsAverageTimeClass = modeIsAverageTimeClass
        this.modeIsAverageTimeMethod = modeIsAverageTimeMethod
        this.modeIsSampleTime = modeIsSampleTime
        this.modeIsSampleTimeClass = modeIsSampleTimeClass
        this.modeIsSampleTimeMethod = modeIsSampleTimeMethod
        this.modeIsSingleShotTime = modeIsSingleShotTime
        this.modeIsSingleShotTimeClass = modeIsSingleShotTimeClass
        this.modeIsSingleShotTimeMethod = modeIsSingleShotTimeMethod
        this.parametrizationCombinations = parametrizationCombinations
        this.paramString = paramString
        this.paramCount = paramCount
        this.paramCountStateObject = paramCountStateObject
        this.paramCountStateObjectWithoutJmhParam = paramCountStateObjectWithoutJmhParam
        this.hasBlackhole = hasBlackhole
        this.hasControl = hasControl
        this.hasBenchmarkParams = hasBenchmarkParams
        this.hasIterationParams = hasIterationParams
        this.hasThreadParams = hasThreadParams
        this.jmhParamString = jmhParamString
        this.jmhParamPairs = jmhParamPairs
        this.jmhParamCount = jmhParamCount
        this.jmhParamCountOwnClass = jmhParamCountOwnClass
        this.jmhParamCountArgument = jmhParamCountArgument
        this.returnType = returnType
        this.partOfGroup = partOfGroup
        this.methodHash = methodHash
    }

    fun sameConfig(o: Result): Boolean {
        return warmupIterations == o.warmupIterations &&
                warmupTime == o.warmupTime &&
                measurementIterations == o.measurementIterations &&
                measurementTime == o.measurementTime &&
                forks == o.forks &&
                warmupForks == o.warmupForks &&
                modeIsThroughput == o.modeIsThroughput &&
                modeIsAverageTime == o.modeIsAverageTime &&
                modeIsSampleTime == o.modeIsSampleTime &&
                modeIsSingleShotTime == o.modeIsSingleShotTime
    }
}