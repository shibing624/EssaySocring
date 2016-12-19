package org.xm.essayscoring.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * essay instance
 *
 * @author xuming
 */
public class EssayInstance {
    //storing all the fields available in the training set file
    public int id;
    public int set;
    public String essay;
    public int rater1_domain1 = -1;
    public int rater2_domain1 = -1;
    public int rater3_domain1 = -1;
    public int domain1_score = -1;
    public int rater1_domain2 = -1;
    public int rater2_domain2 = -1;
    public int domain2_score = -1;
    public int rater1_trait1 = -1;
    public int rater1_trait2 = -1;
    public int rater1_trait3 = -1;
    public int rater1_trait4 = -1;
    public int rater1_trait5 = -1;
    public int rater1_trait6 = -1;

    public int rater2_trait1 = -1;
    public int rater2_trait2 = -1;
    public int rater2_trait3 = -1;
    public int rater2_trait4 = -1;
    public int rater2_trait5 = -1;
    public int rater2_trait6 = -1;

    public int rater3_trait1 = -1;
    public int rater3_trait2 = -1;
    public int rater3_trait3 = -1;
    public int rater3_trait4 = -1;
    public int rater3_trait5 = -1;
    public int rater3_trait6 = -1;

    public static final Pattern paragraphPattern = Pattern.compile("\\s{3,}");
    public static final Pattern sentencePattern = Pattern.compile("(?<=[\\.?!][^\\w\\s]?)\\s+(?![a-z])");
    public static final Pattern wordPattern = Pattern.compile("\\s+");
    public static final Pattern frontPattern = Pattern.compile("([^\\w@]+)(\\w@].*)");
    public static final Pattern backPattern = Pattern.compile("(.*\\w)(\\W+)");

    /**
     * EN abbreviation
     */
    public static final Pattern endsInAbbreviation = Pattern.compile(".*(Mr|Mrs|Dr|Jr|Ms|Prof|Sr|dept|Univ|Inc|Ltd|Co" +
            "|Corp|Mt|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sep|Oct|Nov|Dec|Sept|vs|etc|no)\\.");
    private ArrayList<ArrayList<ArrayList<String>>> cachedParse = null;
    HashMap<String, Double> features;

    public EssayInstance() {
        this.features = new HashMap<>();
    }

    public void setFeature(HashMap<String, Double> featureScores) {
        for (String key : featureScores.keySet()) {
            if (features.containsKey(key))
                features.put(key.concat("1"), featureScores.get(key));
            else
                features.put(key, featureScores.get(key));
        }
    }


    public void setFeature(String feature, double value) {
        features.put(feature, value);
    }

    public HashMap<String, Double> getFeatures() {
        return this.features;
    }

    public double getFeature(String feature) {
        if (feature.equals("grade"))
            return domain1_score;
        return features.get(feature);
    }

    public List<String> listFeatures() {
        ArrayList<String> fList = new ArrayList<>(features.keySet());
        Collections.sort(fList);
        return fList;
    }

    /**
     * paragraphs
     *
     * @return the parsed structure of the text at the paragraph, sentence, word levels
     */
    public ArrayList<ArrayList<ArrayList<String>>> getParagraphs() {
        if (cachedParse != null) return cachedParse;
        cachedParse = new ArrayList<>();
        String[] paragraphs = paragraphPattern.split(essay);
        for (String paragraph : paragraphs) {
            ArrayList<ArrayList<String>> sentenceList = new ArrayList<>();
            cachedParse.add(sentenceList);
            // boring sentence splitter
            String[] sentences = sentencePattern.split(paragraph);
            // load an abbreviation list and merge sentences back ending in abbrevs
            boolean merageIntoPrevious = false;
            for (String sentence : sentences) {
                ArrayList<String> wordList;
                if (merageIntoPrevious)
                    wordList = sentenceList.get(sentenceList.size() - 1);
                else {
                    wordList = new ArrayList<>();
                    sentenceList.add(wordList);
                }

                //next sentence
                if (endsInAbbreviation.matcher(sentence).matches())
                    merageIntoPrevious = true;
                else merageIntoPrevious = false;
                // split on spaces, then strip off leading and trailing punctuation
                String[] tokens = wordPattern.split(sentence);
                for (String token : tokens) {
                    // empty token
                    if (token.length() == 0)
                        continue;
                    Matcher m = frontPattern.matcher(token);
                    if (m.matches()) {
                        // explode group 1
                        String front = m.group(1);
                        for (int i = 0; i < front.length(); i++)
                            wordList.add(String.valueOf(front.charAt(i)));

                        // group 2
                        token = m.group(2);
                    }
                    m = backPattern.matcher(token);
                    if (m.matches()) {
                        // save group 1
                        wordList.add(m.group(1));
                        // explode group 2
                        String back = m.group(2);
                        for (int i = 0; i < back.length(); i++)
                            wordList.add(String.valueOf(back.charAt(i)));
                    } else wordList.add(token);
                }
            }
        }
        return cachedParse;
    }

    /**
     * toString
     *
     * @return shows ID and parsed contents
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ID(" + id + "): ");
        ArrayList<ArrayList<ArrayList<String>>> paragraphs = getParagraphs();
        for (ArrayList<ArrayList<String>> paragraph : paragraphs) {
            for (ArrayList<String> sentence : paragraph) {
                for (String token : sentence) {
                    sb.append(token);
                    sb.append(" ");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

