package org.xm.essayscoring.features;

import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;

/**
 * @author xuming
 */
public class FeatureList {
    public enum NormType{
        BASIC,
        ABS,
        PROB
    }
    public static void buildFeatures(ArrayList<EssayInstance> instances){
        ArrayList<Features> featuresArrayList = new ArrayList<>();
        featuresArrayList.add(new LengthFeature());
    }
}
