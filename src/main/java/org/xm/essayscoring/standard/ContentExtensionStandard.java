package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * 内容扩展度标准
 * 主题的关联内容
 *
 * @author xuming
 */
public class ContentExtensionStandard implements Standard {
    @Override
    public double getScore(Essay essay) {
        return 0;
    }
}
