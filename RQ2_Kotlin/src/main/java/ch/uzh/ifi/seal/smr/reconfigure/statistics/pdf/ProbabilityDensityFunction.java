package ch.uzh.ifi.seal.smr.reconfigure.statistics.pdf;

import ch.uzh.ifi.seal.smr.reconfigure.helper.ListToArray;
import smile.stat.distribution.KernelDensity;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityDensityFunction {
    public static double[] estimate(List<Double> sample, List<Double> y) {
        KernelDensity kd = new KernelDensity(ListToArray.toPrimitive(sample));
        List<Double> result = new ArrayList<>();

        for (double value : y) {
            result.add(kd.p(value));
        }

        return ListToArray.toPrimitive(result);
    }
}
