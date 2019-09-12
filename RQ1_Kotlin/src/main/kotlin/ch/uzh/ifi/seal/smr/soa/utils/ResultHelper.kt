package ch.uzh.ifi.seal.smr.soa.utils

fun Collection<Result>.nothingSet(): Collection<Result>{
    return filter {
        it.warmupIterations == null && it.warmupTime == null && it.measurementIterations == null && it.measurementTime == null &&
                it.forks == null && it.warmupForks == null && (it.modeIsThroughput == null || it.modeIsThroughput == false)
                && (it.modeIsAverageTime == null || it.modeIsAverageTime == false) && (it.modeIsSampleTime == null || it.modeIsSampleTime == false)
                && (it.modeIsSingleShotTime == null || it.modeIsSingleShotTime == false)
    }
}