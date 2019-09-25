package ch.uzh.ifi.seal.smr.soa.analysis.valuecorrelation

import com.opencsv.bean.CsvBindByPosition

class ResValueCorrelation {
    @CsvBindByPosition(position = 0)
    var warmupIterations: Int? = null

    @CsvBindByPosition(position = 1)
    var warmupTime: Long? = null

    @CsvBindByPosition(position = 2)
    var measurementIterations: Int? = null

    @CsvBindByPosition(position = 3)
    var measurementTime: Long? = null

    @CsvBindByPosition(position = 4)
    var forks: Int? = null

    @CsvBindByPosition(position = 5)
    var warmupForks: Int? = null

    constructor()
    constructor(warmupIterations: Int?, warmupTime: Long?, measurementIterations: Int?, measurementTime: Long?, forks: Int?, warmupForks: Int?) {
        this.warmupIterations = warmupIterations
        this.warmupTime = warmupTime
        this.measurementIterations = measurementIterations
        this.measurementTime = measurementTime
        this.forks = forks
        this.warmupForks = warmupForks
    }
}