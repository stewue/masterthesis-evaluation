package ch.uzh.ifi.seal.smr.soa.evaluation

import com.opencsv.bean.CsvBindByPosition

data class Result(
        @CsvBindByPosition(position = 0)
        val project: String,

        @CsvBindByPosition(position = 1)
        val benchmarkName: String,

        @CsvBindByPosition(position = 2)
        val warmupIterations: Int,

        @CsvBindByPosition(position = 3)
        val warmupIterationsIsDefault: Boolean,

        @CsvBindByPosition(position = 4)
        val warmupTime: Long,

        @CsvBindByPosition(position = 5)
        val warmupTimeIsDefault: Boolean,

        @CsvBindByPosition(position = 6)
        val measurementIterations: Int,

        @CsvBindByPosition(position = 7)
        val measurementIterationsIsDefault: Boolean,

        @CsvBindByPosition(position = 8)
        val measurementTime: Long,

        @CsvBindByPosition(position = 9)
        val measurementTimeIsDefault: Boolean,

        @CsvBindByPosition(position = 10)
        val forks: Int,

        @CsvBindByPosition(position = 11)
        val forksIsDefault: Boolean,

        @CsvBindByPosition(position = 12)
        val warmupForks: Int,

        @CsvBindByPosition(position = 13)
        val warmupForksIsDefault: Boolean,

        @CsvBindByPosition(position = 14)
        val modeIsThroughput: Boolean,

        @CsvBindByPosition(position = 15)
        val modeIsAverageTime: Boolean,

        @CsvBindByPosition(position = 16)
        val modeIsSampleTime: Boolean,

        @CsvBindByPosition(position = 17)
        val modeIsSingleShotTime: Boolean,

        @CsvBindByPosition(position = 18)
        val modeIsDefault: Boolean
)