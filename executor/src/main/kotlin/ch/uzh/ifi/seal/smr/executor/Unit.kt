package ch.uzh.ifi.seal.smr.executor

data class Unit (
    val javaPath: String,
    val jarPath: String,
    val outputFile: String,
    val patternString: String
)