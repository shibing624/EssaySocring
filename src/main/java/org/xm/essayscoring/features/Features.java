package org.xm.essayscoring.features;

import org.xm.essayscoring.domain.EssayInstance;

import java.util.HashMap;

/**
 * @author xuming
 */
public interface Features {
    /**
     * get feature score for each essay instance
     * eg:spelling features <SpellingScore1,0.3>,<SpellingScore2,0.9>
     *
     * @param instance
     * @return
     */
    HashMap<String, Double> getFeatureScores(EssayInstance instance);
}
