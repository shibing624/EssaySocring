package org.xm.essayscoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuming
 */
public class EssayScoring {
    private static final Logger logger = LoggerFactory.getLogger(EssayScoring.class);

    public static final class Config {
        /**
         * 路径
         */
        public static String CilinPath = "data/cilin.db.gz";
        /**
         * 路径
         */
        public static String PinyinPath = "data/F02-GB2312-to-PuTongHua-PinYin.txt";
        /**
         * concept路径
         */
        public static String ConceptPath = "data/concept.dat";
        /**
         * concept.xml.gz路径
         */
        public static String ConceptXmlPath = "data/concept.xml.gz";
        /**
         * 义原关系的路径
         */
        public static String SememePath = "data/sememe.dat";
        /**
         * 义原数据路径
         */
        public static String SememeXmlPath = "data/sememe.xml.gz";
    }

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
