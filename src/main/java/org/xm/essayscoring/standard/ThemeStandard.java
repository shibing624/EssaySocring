package org.xm.essayscoring.standard;

import org.xm.xmnlp.Xmnlp;

import java.util.List;

/**
 * @author xuming
 */
public class ThemeStandard extends Standard {

    @Override
    public double getScore(String text) {
        int size = 5;
        // 默认150字是一个段落，取其中5个主题相关短语
        if (text.length() > 150) {
            size = 5 + ((text.length() - 150) / 150) * 5;
        }
        List<String> list = Xmnlp.extractPhrase(text, size);
        // 比较短语与标题的相似度，视为得分
        return 0;
    }
}
