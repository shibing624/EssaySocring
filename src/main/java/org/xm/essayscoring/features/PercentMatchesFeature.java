package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Compute the percentage of tokens match the input word or regex.
 *
 * @author xuming
 */
public class PercentMatchesFeature implements Features {
    private Pattern pattern = null;

    public PercentMatchesFeature(String word) {
        this(word, false);
    }

    public PercentMatchesFeature(String regex, boolean isRegex) {
        if (isRegex) this.pattern = Pattern.compile(regex);
        this.pattern = Pattern.compile(Pattern.quote(regex));
    }

    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numWords = 0;
        int matches = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    if (pattern.matcher(token).matches()) {
                        matches++;
                    }
                    numWords++;
                }
            }
        }
        result.put("PercentMatches_" + pattern, new Double(matches / (double) numWords));
        if (EssayScoring.DEBUG)
            System.out.println("Percent matches(" + pattern + ") for ID(" + instance.id + "): "
                    + (matches / (double) numWords));
        return result;
    }
}
