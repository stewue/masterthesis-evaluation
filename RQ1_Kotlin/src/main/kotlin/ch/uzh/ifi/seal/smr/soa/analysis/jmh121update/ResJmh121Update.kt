package ch.uzh.ifi.seal.smr.soa.analysis.jmh121update

import com.opencsv.bean.CsvBindByPosition

class ResJmh121Update {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    lateinit var className: String

    @CsvBindByPosition(position = 2)
    lateinit var benchmarkName: String

    @CsvBindByPosition(position = 3)
    var sameConfig: Boolean = false

    @CsvBindByPosition(position = 4)
    var warmupIterationsOldDefault: Boolean? = null

    @CsvBindByPosition(position = 5)
    var warmupTimeOldDefault: Boolean? = null

    @CsvBindByPosition(position = 6)
    var measurementIterationsOldDefault: Boolean? = null

    @CsvBindByPosition(position = 7)
    var measurementTimeOldDefault: Boolean? = null

    @CsvBindByPosition(position = 8)
    var forksOldDefault: Boolean? = null

    @CsvBindByPosition(position = 9)
    var iterationsOneChanged: Boolean? = null

    @CsvBindByPosition(position = 10)
    var timeOneChanged: Boolean? = null

    @CsvBindByPosition(position = 11)
    var warmupIterationsNullChanged: Boolean? = null

    @CsvBindByPosition(position = 12)
    var warmupTimeNullChanged: Boolean? = null

    @CsvBindByPosition(position = 13)
    var measurementIterationsNullChanged: Boolean? = null

    @CsvBindByPosition(position = 14)
    var measurementTimeNullChanged: Boolean? = null

    @CsvBindByPosition(position = 15)
    var forksNullChanged: Boolean? = null

    @CsvBindByPosition(position = 16)
    var warmupIterationsDifference: Int? = null

    @CsvBindByPosition(position = 17)
    var warmupTimeDifference: Double? = null

    @CsvBindByPosition(position = 18)
    var measurementIterationsDifference: Int? = null

    @CsvBindByPosition(position = 19)
    var measurementTimeDifference: Double? = null

    @CsvBindByPosition(position = 20)
    var forksDifference: Int? = null

    @CsvBindByPosition(position = 21)
    var warmupIterationsPre: Int? = null

    @CsvBindByPosition(position = 22)
    var warmupTimePre: Double? = null

    @CsvBindByPosition(position = 23)
    var measurementIterationsPre: Int? = null

    @CsvBindByPosition(position = 24)
    var measurementTimePre: Double? = null

    @CsvBindByPosition(position = 25)
    var forksPre: Int? = null

    @CsvBindByPosition(position = 26)
    var warmupIterationsPost: Int? = null

    @CsvBindByPosition(position = 27)
    var warmupTimePost: Double? = null

    @CsvBindByPosition(position = 28)
    var measurementIterationsPost: Int? = null

    @CsvBindByPosition(position = 29)
    var measurementTimePost: Double? = null

    @CsvBindByPosition(position = 30)
    var forksPost: Int? = null

    constructor(project: String, className: String, benchmarkName: String, sameConfig: Boolean, warmupIterationsOldDefault: Boolean?, warmupTimeOldDefault: Boolean?, measurementIterationsOldDefault: Boolean?, measurementTimeOldDefault: Boolean?, forksOldDefault: Boolean?, iterationsOneChanged: Boolean?, timeOneChanged: Boolean?, warmupIterationsNullChanged: Boolean?, warmupTimeNullChanged: Boolean?, measurementIterationsNullChanged: Boolean?, measurementTimeNullChanged: Boolean?, forksNullChanged: Boolean?, warmupIterationsDifference: Int?, warmupTimeDifference: Double?, measurementIterationsDifference: Int?, measurementTimeDifference: Double?, forksDifference: Int?, warmupIterationsPre: Int?, warmupTimePre: Double?, measurementIterationsPre: Int?, measurementTimePre: Double?, forksPre: Int?, warmupIterationsPost: Int?, warmupTimePost: Double?, measurementIterationsPost: Int?, measurementTimePost: Double?, forksPost: Int?) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.sameConfig = sameConfig
        this.warmupIterationsOldDefault = warmupIterationsOldDefault
        this.warmupTimeOldDefault = warmupTimeOldDefault
        this.measurementIterationsOldDefault = measurementIterationsOldDefault
        this.measurementTimeOldDefault = measurementTimeOldDefault
        this.forksOldDefault = forksOldDefault
        this.iterationsOneChanged = iterationsOneChanged
        this.timeOneChanged = timeOneChanged
        this.warmupIterationsNullChanged = warmupIterationsNullChanged
        this.warmupTimeNullChanged = warmupTimeNullChanged
        this.measurementIterationsNullChanged = measurementIterationsNullChanged
        this.measurementTimeNullChanged = measurementTimeNullChanged
        this.forksNullChanged = forksNullChanged
        this.warmupIterationsDifference = warmupIterationsDifference
        this.warmupTimeDifference = warmupTimeDifference
        this.measurementIterationsDifference = measurementIterationsDifference
        this.measurementTimeDifference = measurementTimeDifference
        this.forksDifference = forksDifference
        this.warmupIterationsPre = warmupIterationsPre
        this.warmupTimePre = warmupTimePre
        this.measurementIterationsPre = measurementIterationsPre
        this.measurementTimePre = measurementTimePre
        this.forksPre = forksPre
        this.warmupIterationsPost = warmupIterationsPost
        this.warmupTimePost = warmupTimePost
        this.measurementIterationsPost = measurementIterationsPost
        this.measurementTimePost = measurementTimePost
        this.forksPost = forksPost
    }

    constructor(project: String, className: String, benchmarkName: String, sameConfig: Boolean) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.sameConfig = sameConfig
    }

    constructor()

    fun somethingChanged(): Boolean {
        return warmupIterationsOldDefault!! || warmupTimeOldDefault!! || measurementIterationsOldDefault!! || measurementTimeOldDefault!! || forksOldDefault!!
    }

    fun nullSet(): Boolean {
        return warmupIterationsNullChanged!! || warmupTimeNullChanged!! || measurementIterationsNullChanged!! || measurementTimeNullChanged!! || forksNullChanged!!
    }
}