package ch.uzh.ifi.seal.smr.soa.jmhversion

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class JmhSourceCodeVersionExtractorTest {
    @Test
    fun test() {
        val input = JmhSourceCodeVersionExtractor.jmhVersions.toMutableList()
        input.shuffle()
        input.forEach { expectedVersion ->
            val actualVersion = search(expectedVersion)
            if (actualVersion != expectedVersion) {
                Assertions.fail<String>("version $actualVersion is not expected version $expectedVersion")
            } else {
                println("$actualVersion = $expectedVersion")
            }
        }
    }

    private fun search(input: String): String? {
        JmhSourceCodeVersionExtractor.jmhVersions.forEach { version ->
            if (input.contains(version)) {
                return version
            }
        }

        return null
    }
}