package ch.uzh.ifi.seal.smr.soa.analysis.forkchanged

import com.opencsv.bean.CsvBindByPosition

class ResForkChanged {
    @CsvBindByPosition(position = 0)
    lateinit var project: String

    @CsvBindByPosition(position = 1)
    lateinit var className: String

    @CsvBindByPosition(position = 2)
    lateinit var benchmarkName: String

    @CsvBindByPosition(position = 3)
    var sameConfig: Boolean? = null

    @CsvBindByPosition(position = 4)
    var sameCode: Boolean = false

    @CsvBindByPosition(position = 5)
    var bothSame: Boolean? = null

    @CsvBindByPosition(position = 6)
    var neverSameBenchmark: Boolean = true

    constructor()
    constructor(project: String, className: String, benchmarkName: String, sameConfig: Boolean?, sameCode: Boolean, bothSame: Boolean?, neverSameBenchmark: Boolean) {
        this.project = project
        this.className = className
        this.benchmarkName = benchmarkName
        this.sameConfig = sameConfig
        this.sameCode = sameCode
        this.bothSame = bothSame
        this.neverSameBenchmark = neverSameBenchmark
    }
}