package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.tokenizer.Tokenizer;
import org.xm.essayscoring.tokenizer.Word;
import org.xm.essayscoring.util.StatisticUtil;
import org.xm.xmnlp.recommend.Recommend;

import java.util.List;
import java.util.Map;


/**
 * @author xuming
 */
public class ThemeStandard implements Standard {

    private static final int PHRASE_SIZE = 5;
    private static final double RECOMMEND_WEIGHT = 0.5;
    private static final double FREQUENCY_WEIGHT = 0.5;

    @Override
    public double getScore(Essay essay) {
        String text = essay.text;
        String title = essay.title;
        double recommendScore = getRecommendScore(title, text);
        double freqScore = getFrequencyScore(title,text);
        return recommendScore * RECOMMEND_WEIGHT + freqScore *FREQUENCY_WEIGHT;

    }

    public double getRecommendScore(String title, String text) {
        double result = 0.0;
        int size = PHRASE_SIZE;
        // 默认300字是一个段落，取其中5个主题相关短语
        int length = text.length();
        if (length < PHRASE_SIZE)
            size = length;
        if (length > 300)
            size = PHRASE_SIZE + ((length - 300) / 300) * PHRASE_SIZE;
        List<String> list = Tokenizer.extractPhrase(text, size);
        if (list == null || list.size() == 0) return 0.0;
        // 比较主题短语与标题的相似度
        Recommend recommend = new Recommend();
        list.forEach(recommend::addSentence);
        List<Word> titles = Tokenizer.notionalSegment(title);
        for (Word w : titles) {
            Map<String, Double> map = recommend.getRecommendScore(w.name, 1);
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


    public double getFrequencyScore(String title, String text) {
        double score = 0.0;
        int times = 0;
        Map<String, Integer> map = StatisticUtil.getWordFrequency(text);
        List<Word> titles = Tokenizer.notionalSegment(title);
        for (Word w : titles) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getKey().equals(w.name)) {
                    times += entry.getValue();
                }
            }
        }
        if (times > 1) return 1.0;
        return score;
    }


}
