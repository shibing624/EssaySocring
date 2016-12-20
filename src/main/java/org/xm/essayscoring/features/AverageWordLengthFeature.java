package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @author xuming
 */
public class AverageWordLengthFeature implements Features {
    public static final Pattern validWord = Pattern.compile("\\w");

    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        int numWords = 0;
        int sumLength = 0;
        ArrayList<ArrayList<ArrayList<String>>> pargraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> pargraph : pargraphs) {
            for (ArrayList<String> sentence : pargraph) {
                for (String token : sentence) {
                    if (token.charAt(0) != '@' && validWord.matcher(token).find()) {
                        numWords++;
                        sumLength += token.length();
                    }
                }
            }
        }
        HashMap<String, Double> values = new HashMap<>();
        values.put("AverageWordLength", new Double(sumLength / (double) numWords));
        if (EssayScoring.DEBUG)
            System.out.println("Average word length for ID(" + instance.id + "): " + (sumLength / (double) numWords));
        return values;
    }
}
