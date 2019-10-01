package ch.uzh.ifi.seal.smr.soa.analysis.javaversion

import com.opencsv.bean.CsvBindByPosition

class ResJavaVersion {
    @CsvBindByPosition(position = 0)
    lateinit var version: String

    @CsvBindByPosition(position = 1)
    var count: Int = 0

    @CsvBindByPosition(position = 2)
    var countShortLived: Int = 0

    @CsvBindByPosition(position = 3)
    var countLongLived: Int = 0

    constructor()
    constructor(version: String, count: Int, countShortLived: Int, countLongLived: Int) {
        this.version = version
        this.count = count
        this.countShortLived = countShortLived
        this.countLongLived = countLongLived
    }
}