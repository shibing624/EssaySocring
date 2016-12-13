package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.util.StatisticUtil;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 修辞与表达标准
 * 成语、俗语等形容词占比
 *
 * @author xuming
 */
public class ExpressionStandard implements Standard {

    @Override
    public double getScore(Essay essay) {
        String text = essay.text;
        Map<String, Integer> posFrequencyMap = getPosFrequency(text);
        int adFreq = 0;
        int nFreq = 0;
        int vFreq = 0;
        for (Map.Entry<String, Integer> entry : posFrequencyMap.entrySet()) {
            if (entry.getKey().startsWith("n")) nFreq += entry.getValue();
            if (entry.getKey().startsWith("a")) adFreq += entry.getValue();
            if (entry.getKey().startsWith("v")) vFreq += entry.getValue();
        }
        double weight = (double) (adFreq + vFreq) / (nFreq + adFreq + vFreq);
        return weight;
    }

    public static Map<String, Integer> getPosFrequency(String text) {
        List<String> words = new ArrayList<>();
        //处理text
        List<Term> terms = NotionalTokenizer.segment(text);
        words.addAll(terms.stream().map(term -> term.getNature().name()).collect(Collectors.toList()));
        //统计同一词性的词频
        return StatisticUtil.getWordFrequency(words);
    }


}
