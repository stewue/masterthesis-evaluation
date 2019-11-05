package org.openjdk.jmh.reconfigure.statistics.evaluation;

import org.openjdk.jmh.reconfigure.helper.HistogramItem;
import org.openjdk.jmh.reconfigure.statistics.Sampler;
import org.openjdk.jmh.reconfigure.statistics.ci.CiPercentage;

import java.util.*;

import static org.openjdk.jmh.reconfigure.statistics.ReconfigureConstants.SAMPLE_SIZE;

public class CiPercentageEvaluation implements StatisticalEvaluation {
    private double threshold;

    private Map<Integer, List<HistogramItem>> samplePerIteration = new HashMap<>();
    private Map<Integer, Double> ciPercentagePerIteration = new HashMap<>();

    public CiPercentageEvaluation(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public void addIteration(List<HistogramItem> list) {
        List<HistogramItem> sample = new Sampler(list).getSample(SAMPLE_SIZE);

        int iteration = samplePerIteration.size() + 1;
        samplePerIteration.put(iteration, sample);
    }

    @Override
    public double getThreshold() {
        return threshold;
    }

    @Override
    public Double calculateVariability() {
        if(samplePerIteration.size() < 5){
            return null;
        }else{
            List<Double> deltas = new ArrayList<>();
            int currentIteration = samplePerIteration.size();
            double currentCiPercentage = getCiPercentageOfIteration(currentIteration);

            for(int i=1; i<=4; i++){
                double ciPercentage = getCiPercentageOfIteration(currentIteration - i);
                double delta = Math.abs(ciPercentage - currentCiPercentage);
                deltas.add(delta);
            }

            return Collections.max(deltas);
        }
    }

    public double getCiPercentageOfIteration(int iteration){
        if(ciPercentagePerIteration.get(iteration) == null){
            List<HistogramItem> all = new ArrayList<>();
            for(int i=1; i<=iteration; i++){
                all.addAll(samplePerIteration.get(i));
            }

            double ciPercentage = new CiPercentage(all).getValue();
            ciPercentagePerIteration.put(iteration, ciPercentage);
            return ciPercentage;
        }else{
            return ciPercentagePerIteration.get(iteration);
        }
    }
}
