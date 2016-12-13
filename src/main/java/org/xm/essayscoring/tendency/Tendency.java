package org.xm.essayscoring.tendency;

import org.xm.tendency.word.HownetWordTendency;

/**
 * @author xuming
 */
public class Tendency {

    /**
     * 词语情感分析
     *
     * @param word
     * @return
     */
    public static double getWordTendencyScore(String word) {
        HownetWordTendency hownetWordTendency = new HownetWordTendency();
        //Similarity HownetWordTendency
        return hownetWordTendency.getTendency(word);
    }


}
