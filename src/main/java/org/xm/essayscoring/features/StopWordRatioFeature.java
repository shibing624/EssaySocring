package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;
import org.xm.xmnlp.dic.DicReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * stop word num / all word num
 *
 * @author xuming
 */
public class StopWordRatioFeature implements Features {
    HashSet<String> stopwords;
    private static final String PATH = EssayScoring.StopwordsPath;
    public StopWordRatioFeature()throws IOException{
         this(PATH);
    }
    public StopWordRatioFeature(String stopwordPath) throws IOException {
        stopwords = new HashSet<>();
        BufferedReader br = DicReader.getReader(stopwordPath);
        String line;
        while ((line = br.readLine()) != null) {
            line.trim();
            if (line.length() == 0 || line.charAt(0) == '#')
                continue;
            stopwords.add(line.toLowerCase());
        }
        br.close();
    }

    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        HashMap<String, Double> result = new HashMap<>();
        int numStopwords = 0;
        int numWords = 0;
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = instance.getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    // filter nonsense words
                    if (token.length() == 1 && !token.matches("\\w"))
                        continue;
                    if (stopwords.contains(token.toLowerCase()))
                        numStopwords++;
                    numWords++;
                }
            }
        }
        result.put("stopword_ratio", numStopwords / (double) numWords);
        return result;
    }
}
