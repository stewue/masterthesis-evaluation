package ch.uzh.ifi.seal.smr.soa.evaluation

object JmhParamHelper {
    private const val jmhParamSeperator = "§"

    fun getJmhParamsString(jmhParams: Collection<Pair<String, String>>): String {
        var jmhParamsString = ""

        if (jmhParams.isNotEmpty()) {
            val sorted = jmhParams.sortedBy { it.first }
            for (jmhParam in sorted) {
                jmhParamsString += jmhParamSeperator + jmhParam.first + "=" + jmhParam.second
            }
        }

        return jmhParamsString
    }
}