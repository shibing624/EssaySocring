package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xuming
 */
public class LengthFeature implements Features {
    HashMap<String, Double> featureScores = new HashMap<>();
    private static int maxWordCount = -1;

    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        featureScores.put("lengthratio", getScore(instance.essay));
        return featureScores;
    }

    /**
     * The length of this essay normalized against the length of longest essay
     * @param essay
     * @return
     */
    public Double getScore(String essay) {
        String[] words = essay.split("\\s");
        return Double.valueOf((double) words.length / getMaxWordCount());
    }

    private int getMaxWordCount() {
        if (maxWordCount == -1) {
            ArrayList<EssayInstance> instances = EssayScoring.getInstances();
            // compute the word length of longest essay
            for (EssayInstance instance : instances) {
                String essay = instance.essay;
                String[] words = essay.split("\\s");
                int count = words.length;
                if (count > maxWordCount)
                    maxWordCount = count;
            }
        }
        return maxWordCount;
    }
}
