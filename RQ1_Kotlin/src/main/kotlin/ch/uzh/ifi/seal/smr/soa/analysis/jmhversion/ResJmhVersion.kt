package ch.uzh.ifi.seal.smr.soa.analysis.jmhversion

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

class ResJmhVersion {
    @CsvBindByPosition(position = 0)
    lateinit var version: JMHVersion

    @CsvBindByPosition(position = 1)
    var count: Int = 0

    constructor()
    constructor(version: JMHVersion, count: Int) {
        this.version = version
        this.count = count
    }
}