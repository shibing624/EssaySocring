package org.xm.essayscoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;

/**
 * @author xuming
 */
public class EssayScoring {
    private static final Logger LOG = LoggerFactory.getLogger(EssayScoring.class);
    public static final boolean DEBUG = true;
    private static ArrayList<EssayInstance> instances;
    public static ArrayList<EssayInstance> getInstances() {
        return instances;
    }

    public static void setInstances(ArrayList<EssayInstance> instances) {
        EssayScoring.instances = instances;
    }


    /**
     * 路径
     */
    public static String CilinPath = "cilin.db.gz";
    /**
     * 路径
     */
    public static String PinyinPath = "F02-GB2312-to-PuTongHua-PinYin.txt";

    private EssayScoring() {
    }


    /**
     * 词语相似度
     * 计算词林编码相似度
     *
     * @param word1
     * @param word2
     * @return
     */
    public static double cilinSimilarity(String word1, String word2) {
        return 0.0;
    }

}
