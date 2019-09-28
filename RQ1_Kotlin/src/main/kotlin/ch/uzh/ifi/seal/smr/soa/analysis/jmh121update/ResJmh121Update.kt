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
    var warmupIterationsPre: Int? = null

    @CsvBindByPosition(position = 5)
    var warmupIterationsPost: Int? = null

    @CsvBindByPosition(position = 6)
    var warmupTimePre: Double? = null

    @CsvBindByPosition(position = 7)
    var warmupTimePost: Double? = null

    @CsvBindByPosition(position = 8)
    var measurementIterationsPre: Int? = null

    @CsvBindByPosition(position = 9)
    var measurementIterationsPost: Int? = null

    @CsvBindByPosition(position = 10)
    var measurementTimePre: Double? = null

    @CsvBindByPosition(position = 11)
    var measurementTimePost: Double? = null

    @CsvBindByPosition(position = 12)
    var forksPre: Int? = null

    @CsvBindByPosition(position = 13)
    var forksPost: Int? = null


    constructor()

    constructor(project: String, className: String, benchmarkName: String, sameConfig: Boolean) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.sameConfig = sameConfig
    }

    constructor(project: String, className: String, benchmarkName: String, sameConfig: Boolean, warmupIterationsPre: Int?, warmupIterationsPost: Int?, warmupTimePre: Double?, warmupTimePost: Double?, measurementIterationsPre: Int?, measurementIterationsPost: Int?, measurementTimePre: Double?, measurementTimePost: Double?, forksPre: Int?, forksPost: Int?) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.sameConfig = sameConfig
        this.warmupIterationsPre = warmupIterationsPre
        this.warmupIterationsPost = warmupIterationsPost
        this.warmupTimePre = warmupTimePre
        this.warmupTimePost = warmupTimePost
        this.measurementIterationsPre = measurementIterationsPre
        this.measurementIterationsPost = measurementIterationsPost
        this.measurementTimePre = measurementTimePre
        this.measurementTimePost = measurementTimePost
        this.forksPre = forksPre
        this.forksPost = forksPost
    }
}