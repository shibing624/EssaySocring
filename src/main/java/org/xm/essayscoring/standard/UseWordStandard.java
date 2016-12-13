package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * 用词能力标准
 * 贴切程度，同义词的差异度，各种词性词频差异度
 *
 * @author xuming
 */
public class UseWordStandard implements Standard {
    @Override
    public double getScore(Essay essay) {
        return 0;
    }
}
