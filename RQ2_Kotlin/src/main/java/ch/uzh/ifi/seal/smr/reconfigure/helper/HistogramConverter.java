package ch.uzh.ifi.seal.smr.reconfigure.helper;

import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class HistogramConverter {
    public static List<Double> toArray(List<HistogramItem> input){
        List<Double> out = new ArrayList<>();

        for(int i=0; i<input.size(); i++){
            HistogramItem item = input.get(i);

            for(int j=0; j<item.getCount(); j++){
                out.add(item.getValue());
            }
        }

        return out;
    }
}
