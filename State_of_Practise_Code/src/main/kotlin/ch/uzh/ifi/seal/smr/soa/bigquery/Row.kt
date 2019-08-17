package ch.uzh.ifi.seal.smr.soa.bigquery

import com.opencsv.bean.CsvBindByPosition

class Row {
    @CsvBindByPosition(position = 0)
    var project: String = ""

    @CsvBindByPosition(position = 1)
    var forked: Boolean = false

    @CsvBindByPosition(position = 2)
    var watchers: Int = 0

    @CsvBindByPosition(position = 3)
    var stars: Int = 0

    @CsvBindByPosition(position = 4)
    var forks: Int = 0

    @CsvBindByPosition(position = 5)
    var subscribers: Int = 0

    // we get a 404 error from the github api -> repo not available
    @CsvBindByPosition(position = 6)
    var repoAvailable: Boolean = true

    // full name of the parent project (null if not a forked project)
    @CsvBindByPosition(position = 7)
    var parentProject: String? = null

    // is the parent project in our list
    @CsvBindByPosition(position = 8)
    var parentInList: Boolean? = null

    @CsvBindByPosition(position = 9)
    var cloneUrl: String? = null

    @CsvBindByPosition(position = 10)
    var lastUpdate: String? = null

    @CsvBindByPosition(position = 11)
    var duplicatedFork: Boolean? = null

    @CsvBindByPosition(position = 12)
    var selectedForStudy: Boolean? = null

    @CsvBindByPosition(position = 13)
    var archived: Boolean? = null

    @CsvBindByPosition(position = 14)
    var disabled: Boolean? = null

    @CsvBindByPosition(position = 15)
    var numberOfReleases: Int? = null

    @CsvBindByPosition(position = 16)
    var numberOfCommits: Int? = null

    constructor()
}