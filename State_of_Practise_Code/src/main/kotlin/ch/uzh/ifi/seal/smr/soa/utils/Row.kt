package ch.uzh.ifi.seal.smr.soa.utils

import com.opencsv.bean.CsvBindByPosition

class Row {
    @CsvBindByPosition(position = 0)
    var project: String = ""

    @CsvBindByPosition(position = 1)
    var forked: Boolean = false

    @CsvBindByPosition(position = 2)
    var watchers: Int? = null

    @CsvBindByPosition(position = 3)
    var stars: Int? = null

    @CsvBindByPosition(position = 4)
    var forks: Int? = null

    @CsvBindByPosition(position = 5)
    var subscribers: Int? = null

    // we get a 404 error from the github api -> repo not available
    @CsvBindByPosition(position = 6)
    var repoAvailable: Boolean = true

    // full name of the parent project (null if not a forked project)
    @CsvBindByPosition(position = 7)
    var parentProject: String? = null

    @CsvBindByPosition(position = 8)
    var cloneUrl: String? = null

    @CsvBindByPosition(position = 9)
    var lastUpdate: String? = null

    @CsvBindByPosition(position = 10)
    var numberOfCommits: Int? = null

    @CsvBindByPosition(position = 11)
    var numberOfTags: Int? = null

    @CsvBindByPosition(position = 12)
    var numberOfContributors: Int? = null

    @CsvBindByPosition(position = 13)
    var jmhVersion: String? = null

    @CsvBindByPosition(position = 14)
    var numberOFBenchmarks: Int? = null

    constructor()
}