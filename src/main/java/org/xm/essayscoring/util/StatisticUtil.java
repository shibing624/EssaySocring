package org.xm.essayscoring.util;

import org.xm.essayscoring.tokenizer.Tokenizer;
import org.xm.essayscoring.tokenizer.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuming
 */
public class StatisticUtil {
    private static final int HEAD_WEIGHT = 2;
    private static final int OTHER_WEIGHT = 1;

    /**
     * 统计文本中出现的词频
     */
    public static Map<String, Integer> getWordFrequency(String text) {
        List<Word> headWords = new ArrayList<>();
        List<Word> otherWords = new ArrayList<>();

        //处理text
        List<Word> words = Tokenizer.segment(text);
        if (words.size() > 300) {
            for (int i = 300; i < words.size(); i++)
                otherWords.add(words.get(i));
        }
        //前300词算head
        int heads = words.size() < 300 ? words.size() : 300 ;
        for (int i = 0; i < heads; i++)
            headWords.add(words.get(i));
        //统计词频
        //head中出现一次算两次
        //other中出现一次算一次
        Map<String, Integer> map = new HashMap<>();
        for (Word word : headWords) {
            Integer count = map.get(word);
            if (count == null) {
                count = HEAD_WEIGHT;
            } else {
                count += HEAD_WEIGHT;
            }
            map.put(word.name, count);
        }
        for (Word word : otherWords) {
            Integer count = map.get(word);
            if (count == null) {
                count = OTHER_WEIGHT;
            } else {
                count += OTHER_WEIGHT;
            }
            map.put(word.name, count);
        }

        return map;
    }

    public static Map<String, Integer> getPosFrequency(String text) {
        //处理text
        List<Word> words = Tokenizer.notionalSegment(text);
        //统计同一词性的词频
        Map<String, Integer> map = new HashMap<>();
        for (Word word : words) {
            Integer count = map.get(word.pos);
            if (count == null) {
                count = 1;
            } else {
                count ++;
            }
            map.put(word.pos, count);
        }

        return map;
    }
}
