package org.xm.essayscoring;

import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;

/**
 * @author xuming
 */
public class EssayScoring {
    public static final boolean DEBUG = false;
    private static ArrayList<EssayInstance> instances;
    public static ArrayList<EssayInstance> getInstances() {
        return instances;
    }
    public static void setInstances(ArrayList<EssayInstance> instances) {
        EssayScoring.instances = instances;
    }

    /**
     * 英文停用词路径
     */
    public static String StopwordsPath = "stopwords.txt";
    /**
     * 英文拼写检查词典路径
     */
    public static String SpellCheckingWordsPath = "scowl-7.1/american_50.latin1";
    /**
     * 训练集路径
     */
    public static String TrainSetPath ="Phase1/training_set_rel3.tsv";

    /**
     * 词语
     */
    public static double cilinSimilarity(String word1, String word2) {
        return 0.0;
    }

}
