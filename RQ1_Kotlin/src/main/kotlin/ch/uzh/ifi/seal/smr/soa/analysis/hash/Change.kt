package ch.uzh.ifi.seal.smr.soa.analysis.hash

data class Change(
        var type: ChangeType,
        val commitTime: Int,
        val benchmark: String,
        val configChanged: Boolean
)

enum class ChangeType {
    ADDED,
    ADDED_SAME, /* added -> removed -> added with same hash */
    UPDATED,
    REMOVED,
    REMOVED_ADDED_LATER,
    EXISTING
}