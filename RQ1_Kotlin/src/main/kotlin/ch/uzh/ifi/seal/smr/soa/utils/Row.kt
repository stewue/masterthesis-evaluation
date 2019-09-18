package ch.uzh.ifi.seal.smr.soa.utils

import com.opencsv.bean.CsvBindByPosition

class Row {
    @CsvBindByPosition(position = 0)
    var project: String = ""

    @CsvBindByPosition(position = 1)
    var forked: Boolean = false

    @CsvBindByPosition(position = 2)
    var stars: Int? = null

    @CsvBindByPosition(position = 3)
    var forks: Int? = null

    @CsvBindByPosition(position = 4)
    var watchers: Int? = null

    // we get a 404 error from the github api -> repo not available
    @CsvBindByPosition(position = 5)
    var repoAvailable: Boolean = true

    // full name of the parent project (null if not a forked project)
    @CsvBindByPosition(position = 6)
    var parentProject: String? = null

    // full name of the root project (null if not a forked project)
    @CsvBindByPosition(position = 7)
    var rootProject: String? = null

    @CsvBindByPosition(position = 8)
    var mainRepo: Boolean? = null

    @CsvBindByPosition(position = 9)
    var cloneUrl: String? = null

    @CsvBindByPosition(position = 10)
    var lastUpdate: String? = null

    @CsvBindByPosition(position = 11)
    var numberOfCommits: Int? = null

    @CsvBindByPosition(position = 12)
    var numberOfTags: Int? = null

    @CsvBindByPosition(position = 13)
    var numberOfContributors: Int? = null

    @CsvBindByPosition(position = 14)
    var jmhVersion: String? = null

    @CsvBindByPosition(position = 15)
    var numberOfBenchmarks: Int? = null

    @CsvBindByPosition(position = 16)
    var javaTarget: String? = null

    @CsvBindByPosition(position = 17)
    var javaSource: String? = null

    @CsvBindByPosition(position = 18)
    var firstBenchmarkFound: Int? = null

    @CsvBindByPosition(position = 19)
    var lastCommit: Int? = null

    constructor()

    fun getGroup(): Group {
        return if (numberOfBenchmarks == null || numberOfContributors == null || stars == null || numberOfCommits == null) {
            throw IllegalArgumentException("numberOfBenchmarks, numberOfContributors, stars and numberOfCommits cannot be null")
        } else {
            if (numberOfBenchmarks!! >= 25) {
                if (numberOfContributors!! >= 5 && stars!! >= 50 && numberOfCommits!! >= 100) {
                    Group.PROFESSIONAL_MANY
                } else {
                    Group.PROFESSIONAL_FEW
                }
            } else {
                if (numberOfContributors!! >= 5 && stars!! >= 50 && numberOfCommits!! >= 100) {
                    Group.NOT_PROFESSIONAL_MANY
                } else {
                    Group.NOT_PROFESSIONAL_FEW
                }
            }
        }
    }
}