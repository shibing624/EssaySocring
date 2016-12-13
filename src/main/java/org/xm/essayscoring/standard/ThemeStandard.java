package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.util.StatisticUtil;
import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.recommend.Recommend;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 主题一致性标准
 *
 * @author xuming
 */
public class ThemeStandard implements Standard {

    private static final int PHRASE_SIZE = 5;
    private static final double RECOMMEND_WEIGHT = 0.5;
    private static final double FREQUENCY_WEIGHT = 0.5;
    private static final int HEAD_WEIGHT = 2;
    private static final int OTHER_WEIGHT = 1;

    @Override
    public double getScore(Essay essay) {
        String text = essay.text;
        String title = essay.title;
        double recommendScore = getRecommendScore(title, text);
        double freqScore = getTitleFrequencyScore(title, text);
        return recommendScore * RECOMMEND_WEIGHT + freqScore * FREQUENCY_WEIGHT;
    }

    /**
     * 标题切合正文的得分
     * 通过提取主题短语，比较标题与主题短语相似度
     *
     * @param title 标题
     * @param text  正文
     * @return 得分
     */
    private double getRecommendScore(String title, String text) {
        double result = 0.0;
        int size = PHRASE_SIZE;
        // 默认300字是一个段落，取其中5个主题相关短语
        int length = text.length();
        if (length < PHRASE_SIZE)
            size = length;
        if (length > 300)
            size = PHRASE_SIZE + ((length - 300) / 300) * PHRASE_SIZE;
        List<String> list = Xmnlp.extractPhrase(text, size);
        if (list == null || list.size() == 0) return 0.0;
        // 比较主题短语与标题的相似度
        Recommend recommend = new Recommend();
        list.forEach(recommend::addSentence);
        List<Term> titles = NotionalTokenizer.segment(title);
        for (Term w : titles) {
            Map<String, Double> map = recommend.getRecommendScore(w.word, 1);
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                double val = entry.getValue();
                if (val > result) {
                    result = val;
                }
            }
        }
        if (result > 1.0) return 1.0;
        result /= 10;
        return result;
    }

    /**
     * 标题在正文中词频得分（TF）
     *
     * @param title 标题
     * @param text  正文
     * @return 得分
     */
    private double getTitleFrequencyScore(String title, String text) {
        double score = 0.0;
        int times = 0;
        Map<String, Integer> map = getTextWordFrequency(text);
        List<Term> titles = NotionalTokenizer.segment(title);
        for (Term w : titles) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getKey().equals(w.word)) {
                    times += entry.getValue();
                }
            }
        }
        if (times > 1) return 1.0;
        return score;
    }

    /**
     * 文本中词频
     *
     * @param text 作文正文
     * @return map
     */
    private Map<String, Integer> getTextWordFrequency(String text) {
        List<String> headWords = new ArrayList<>();
        List<String> otherWords = new ArrayList<>();

        //处理text
        List<Term> words = Xmnlp.segment(text);
        if (words.size() > 300) {
            for (int i = 300; i < words.size(); i++)
                otherWords.add(words.get(i).word);
        }
        //前300词算head
        int heads = words.size() < 300 ? words.size() : 300;
        for (int i = 0; i < heads; i++)
            headWords.add(words.get(i).word);
        //统计词频
        //head中出现一次算两次
        //other中出现一次算一次
        Map<String, Integer> map = new HashMap<>();
        map.putAll(StatisticUtil.getWordFrequency(headWords, HEAD_WEIGHT));
        map.putAll(StatisticUtil.getWordFrequency(otherWords, OTHER_WEIGHT));

        return map;
    }

}
