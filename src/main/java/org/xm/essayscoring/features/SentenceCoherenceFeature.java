package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Feature for sentence-transition coherence.  More or less average keyword overlap.
 *
 * @author xuming
 */
public class SentenceCoherenceFeature implements Features {
    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numWords = 0;
        int overlap = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            HashSet<String> partParagraph = new HashSet<>();
            for (ArrayList<String> sentence : paragraph) {
                HashSet<String> words = new HashSet<>();
                for (String token : sentence) {
                    token = token.toLowerCase();
                    if (token.length() == 1 && !token.matches("\\w"))
                        continue;
                    if (partParagraph.contains(token))
                        overlap++;
                    // potential fallback rules:
                    // the should overlap the a/an;
                    // he/she/her/it should overlap with anything
                    numWords++;
                    words.add(token);
                }
                // merge
                partParagraph.addAll(words);
            }
        }
        result.put("overlap_coherence", new Double(overlap / (double) numWords));
        if (EssayScoring.DEBUG)
            System.out.println("Overlap coherence for ID(" + instance.id + ") @ score("
                    + instance.domain1_score + "): " + result.get("overlap_coherence"));

        return result;
    }
}
