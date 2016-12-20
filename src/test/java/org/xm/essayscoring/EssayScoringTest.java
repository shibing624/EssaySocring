package org.xm.essayscoring;

import org.xm.essayscoring.domain.EssayInstance;
import org.xm.essayscoring.features.FeatureList;
import org.xm.essayscoring.parser.EssayInstanceParser;

import java.util.ArrayList;


/**
 * @author xuming
 */
public class EssayScoringTest {
    public static void main(String[] args) {
        EssayInstanceParser parser = new EssayInstanceParser();
        // Parse the input training file
        ArrayList<EssayInstance> instances = parser.parse(EssayScoring.TrainSetPath, true);
        EssayScoring.setInstances(instances);
        EssayInstance.printEssayInstances(instances);

        // Get feature Scores for each instance
        ArrayList<EssayInstance> instancesFeatures = FeatureList.buildFeatures(instances);
        // Now we have all the instances and features
        // use any Machine Learning Tools (such as Weka)
        FeatureList.saveAllFeatures(instancesFeatures);
    }

}