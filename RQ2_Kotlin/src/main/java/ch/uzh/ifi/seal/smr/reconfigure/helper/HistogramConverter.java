package ch.uzh.ifi.seal.smr.reconfigure.helper;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class HistogramConverter {
    public static List<Double> toArray(List<Pair<Double, Long>> input){
        List<Double> out = new ArrayList<>();

        for(int i=0; i<input.size(); i++){
            Pair<Double, Long> pair = input.get(i);
            double value = pair.getKey();
            long count = pair.getValue();

            for(int j=0; j<count; j++){
                out.add(value);
            }
        }

        return out;
    }
}
