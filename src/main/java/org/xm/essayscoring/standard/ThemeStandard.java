package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;
import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.recommend.Recommend;
import org.xm.xmnlp.seg.domain.Term;

import java.util.List;
import java.util.Map;


/**
 * @author xuming
 */
public class ThemeStandard extends Standard {

    @Override
    public double getScore(Essay essay) {
        double result = 0.0;
        int size = 3;
        // 默认150字是一个段落，取其中5个主题相关短语
        int length = essay.text.length();
        if (length < 3) {
            size = length;
        }
        if (length > 150) {
            size = 3 + ((length - 150) / 150) * 3;
        }
        List<String> list = Xmnlp.extractPhrase(essay.text, size);
        if (list == null || list.size() == 0) return 0.0;
        // 比较短语与标题的相似度，视为得分
        Recommend recommend = new Recommend();
        list.forEach(recommend::addSentence);
        List<Term> titles = Xmnlp.segment(essay.title);
        for (Term term : titles) {
            Map<String, Double> map = recommend.getRecommendScore(term.word, 1);
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                double val = entry.getValue();
                if (val > result) {
                    result = val;
                }
            }
        }

        return result;
    }


}
