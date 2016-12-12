package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.util.StatisticUtil;

import java.util.Map;

/**
 * @author xuming
 */
public class ExpressionStandard implements Standard {
    @Override
    public double getScore(Essay essay) {
        String text = essay.text;
        Map<String, Integer> posFrequencyMap = StatisticUtil.getPosFrequency(text);
        int adFreq = 0;
        int nFreq = 0;
        int vFreq = 0;
        for (Map.Entry<String, Integer> entry : posFrequencyMap.entrySet()) {
            if (entry.getKey().startsWith("n")) nFreq += entry.getValue();
            if (entry.getKey().startsWith("a")) adFreq += entry.getValue();
            if (entry.getKey().startsWith("v")) vFreq += entry.getValue();
        }
        double weight = (double) (adFreq+vFreq) / (nFreq + adFreq + vFreq);
        return weight;
    }


}
