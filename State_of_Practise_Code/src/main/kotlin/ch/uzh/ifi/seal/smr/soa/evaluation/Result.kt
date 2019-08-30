package ch.uzh.ifi.seal.smr.soa.evaluation

import ch.uzh.ifi.seal.bencher.JMHVersion
import com.opencsv.bean.CsvBindByPosition

data class Result(
        @CsvBindByPosition(position = 0)
        val project: String,

        @CsvBindByPosition(position = 1)
        val codeVersion: String?,

        @CsvBindByPosition(position = 2)
        val jmhVersion: JMHVersion?,

        @CsvBindByPosition(position = 3)
        val benchmarkName: String,

        @CsvBindByPosition(position = 4)
        val warmupIterations: Int?,

        @CsvBindByPosition(position = 5)
        val warmupTime: Long?,

        @CsvBindByPosition(position = 6)
        val measurementIterations: Int?,

        @CsvBindByPosition(position = 7)
        val measurementTime: Long?,

        @CsvBindByPosition(position = 8)
        val forks: Int?,

        @CsvBindByPosition(position = 9)
        val warmupForks: Int?,

        @CsvBindByPosition(position = 10)
        val modeIsThroughput: Boolean?,

        @CsvBindByPosition(position = 11)
        val modeIsAverageTime: Boolean?,

        @CsvBindByPosition(position = 12)
        val modeIsSampleTime: Boolean?,

        @CsvBindByPosition(position = 13)
        val modeIsSingleShotTime: Boolean?,

        @CsvBindByPosition(position = 14)
        val methodhasParams: Boolean
)