package ch.uzh.ifi.seal.smr.soa.analysis.javaversionchange

import com.opencsv.bean.CsvBindByPosition

class ResJavaVersionChange {
    @CsvBindByPosition(position = 0)
    var count: Int = 0

    @CsvBindByPosition(position = 1)
    var numberOfChangesShortLived: Int = 0

    @CsvBindByPosition(position = 2)
    var numberOfChangesLongLived: Int = 0

    constructor()
    constructor(count: Int, numberOfChangesShortLived: Int, numberOfChangesLongLived: Int) {
        this.count = count
        this.numberOfChangesShortLived = numberOfChangesShortLived
        this.numberOfChangesLongLived = numberOfChangesLongLived
    }
}