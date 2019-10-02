package ch.uzh.ifi.seal.smr.soa.analysis.sampledistribution

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

class ResJmhVersionNormalized {
    @CsvBindByPosition(position = 0)
    lateinit var version: JMHVersion

    @CsvBindByPosition(position = 1)
    var normalizedCount: Double = 0.0

    constructor()
    constructor(version: JMHVersion, normalizedCount: Double) {
        this.version = version
        this.normalizedCount = normalizedCount
    }

}