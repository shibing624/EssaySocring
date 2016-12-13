package org.xm.essayscoring.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuming
 */
public class StatisticUtil {

    /**
     * 统计文本中出现的词频
     */
    public static Map<String, Integer> getWordFrequency(List<String> words,int step) {
        Map<String, Integer> map = new HashMap<>();
        //统计
        for (String word : words) {
            Integer count = map.get(word);
            if (count == null) {
                count = step;
            } else {
                count +=step;
            }
            map.put(word, count);
        }

        return map;
    }
    public static Map<String, Integer> getWordFrequency(List<String> words){
        return getWordFrequency(words,1);
    }

}
