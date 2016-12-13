package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.tendency.Tendency;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 格调阳光积极标准
 * @author xuming
 */
public class PositiveStandard implements Standard {
    @Override
    public double getScore(Essay essay) {
        String text = essay.text;
        return getSentenceTendencyScore(text);
    }

    /**
     * 句子文本的情感趋势
     *
     * @param sentence 句子及文本
     * @return 得分列表
     */
    public static List<Double> getSentenceTendency(String sentence) {
        List<Double> scores = new ArrayList<>();
        //Xmnlp NotionalTokenizer
        List<Term> terms = NotionalTokenizer.segment(sentence);
        List<Term> adTerms = terms.stream().filter(term -> term.getNature().startsWith("a")).collect(Collectors.toList());
        scores.addAll(adTerms.stream().map(term -> Tendency.getWordTendencyScore(term.word)).collect(Collectors.toList()));
        return scores;
    }

    /**
     * 句子情感趋势得分：大于0是正面；小于0是负面
     * 统计各个词语得分
     * @param sentence 句子
     * @return 得分
     */
    public static double getSentenceTendencyScore(String sentence) {
        double result = 0.0;
        List<Double> scores = getSentenceTendency(sentence);
        for (Double s : scores) result += s;
        return result;
    }
}
