package ch.uzh.ifi.seal.smr.soa.analysis.hash

data class Change(
        val type: ChangeType,
        val commitTime: Int,
        val benchmark: String,
        val configChanged: Boolean
)

enum class ChangeType {
    ADDED,
    ADDED_SAME, /* added -> removed -> added with same hash */
    ADDED_DIFFERENT, /* added -> removed -> added with other hash */
    UPDATED,
    REMOVED
}