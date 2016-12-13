package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * 判分标准
 * @author xuming
 */
public interface Standard {
    /**
     * 计算各判分标准的得分
     * @param essay 作文
     * @return double 得分
     */
    double getScore(Essay essay);

}
