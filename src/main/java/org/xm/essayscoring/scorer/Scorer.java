package org.xm.essayscoring.scorer;

/**
 * 打分接口
 */
public interface Scorer {
    /**
     * 计算分值
     *
     */
    double getScore(String text);

}
