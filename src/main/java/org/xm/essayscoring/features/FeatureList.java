package org.xm.essayscoring.features;

import org.xm.essayscoring.domain.EssayInstance;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * 在已抽取的140个特征项中，不同的特征项组合对高分作文的判别性能差别较大，但是某些特征项可以稳定地反映作文质量。
 * 这些选出的特征项包括:句子数、平均句长、三级词汇占比、词长大于7的单词数、词长大于8的单词数、词长大于9的单词数、
 * 单词数量、类符形符比、平均词长、动词短语的数量、动词的类符形符比、副词的类符形符比、介词类数以及代词数量。
 *
 * @author xuming
 */
public class FeatureList {

    /**
     * normalization type for the zscore function
     */
    public enum NormType {
        BASIC,
        ABS,
        PROB
    }

    public static ArrayList<EssayInstance> buildFeatures(ArrayList<EssayInstance> instances) {
        ArrayList<Features> featuresArrayList = new ArrayList<>();
        featuresArrayList.add(new LengthFeature());
        Features wordLengthFeature = new AverageWordLengthFeature();
        featuresArrayList.add(wordLengthFeature);
        Features idfFeature = new IDFFeature(instances);
        featuresArrayList.add(idfFeature);
        Features coherenceFeature = new SentenceCoherenceFeature();
        featuresArrayList.add(coherenceFeature);
        // normalization
        featuresArrayList.add(new PercentMatchesFeature(","));
        featuresArrayList.add(new PercentMatchesFeature("!"));
        featuresArrayList.add(new PercentMatchesFeature("?"));
        Features theFeature = new PercentMatchesFeature("the");
        featuresArrayList.add(theFeature);
        featuresArrayList.add(new PercentMatchesFeature("is"));
        featuresArrayList.add(new PercentMatchesFeature("@.*", true));
        // need dictionary
        Features wordFeature = null;
        try {
            wordFeature = new WordFeature();
            featuresArrayList.add(new StopWordRatioFeature());
        } catch (IOException e) {
            System.err.println("Unable to load words: " + e);
        }
        featuresArrayList.add(wordFeature);
        // primary features
        for (EssayInstance instance : instances) {
            for (Features features : featuresArrayList)
                instance.setFeature(features.getFeatureScores(instance));
        }
        ArrayList<Features> normlizationFeatures = new ArrayList<>();
        normlizationFeatures.add(new MinMaxNormalizerFeature(instances, wordFeature, EssayInstance.BaseName.OOVs.name()));
        normlizationFeatures.add(new MinMaxNormalizerFeature(instances, wordFeature, EssayInstance.BaseName.obvious_typos.name()));
        normlizationFeatures.add(new MinMaxNormalizerFeature(instances, wordLengthFeature, EssayInstance.BaseName.AverageWordLength.name()));
        normlizationFeatures.add(new MinMaxNormalizerFeature(instances, idfFeature, EssayInstance.BaseName.AverageIDF.name()));
        normlizationFeatures.add(new GaussianNormailizerFeature(instances, idfFeature, EssayInstance.BaseName.AverageIDF.name(), GaussianNormailizerFeature.Type.ABS_ZSCORE));
        normlizationFeatures.add(new GaussianNormailizerFeature(instances, idfFeature, EssayInstance.BaseName.AverageIDF.name(), GaussianNormailizerFeature.Type.ZSCORE));

        normlizationFeatures.add(new GaussianNormailizerFeature(instances, coherenceFeature, EssayInstance.BaseName.overlap_coherence.name(), GaussianNormailizerFeature.Type.ZSCORE));
        normlizationFeatures.add(new GaussianNormailizerFeature(instances, coherenceFeature, EssayInstance.BaseName.overlap_coherence.name(), GaussianNormailizerFeature.Type.ABS_ZSCORE));

        normlizationFeatures.add(new GaussianNormailizerFeature(instances, theFeature, "PercentMatches_\\Qthe\\E", GaussianNormailizerFeature.Type.ZSCORE));
        normlizationFeatures.add(new GaussianNormailizerFeature(instances, theFeature, "PercentMatches_\\Qthe\\E", GaussianNormailizerFeature.Type.NORMAL_PROB));
        normlizationFeatures.add(new GaussianNormailizerFeature(instances, theFeature, "PercentMatches_\\Qthe\\E", GaussianNormailizerFeature.Type.ABS_ZSCORE));
        // compute normalization feature
        for (EssayInstance instance : instances) {
            for (Features feature : normlizationFeatures)
                instance.setFeature(feature.getFeatureScores(instance));
        }

        //analysis feature
        FeatureAnalyzer.analysis(instances);
        return instances;
    }

    public static void saveAllFeatures(ArrayList<EssayInstance> instances) {
        try {
            // generate an ARFF with real valued output class (for regression if possible)
            saveARFFRealClass(FeatureAnalyzer.filter(instances, 1), "data/training_essay1_real.arff");
            saveARFFDiscreteClass(FeatureAnalyzer.filter(instances, 1), "data/training_essay1_discrete.arff");
            // generate an ARFF where grade is turned into a binary feature based on the threshold (in this case over/under 8.5)
            saveARFFThresholdClass(FeatureAnalyzer.filter(instances, 1), "data/training_essay1_t8.5.arff", 8.5);
        } catch (IOException e) {
            System.err.println("Error saving ARFF: " + e);
        }
    }

    /**
     * Save the data as an ARFF file where grade is specified as a real-valued feature.
     * This type of output class doesn't work with most of Weka.
     */
    public static void saveARFFRealClass(ArrayList<EssayInstance> instances, String filename) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.println("% Autogenerated by java");
        printWriter.println("@relation " + filename);
        List<String> features = instances.get(0).listFeatures();
        for (String i : features)
            printWriter.println("@attribute " + arffEscapeName(i) + " real");
        printWriter.println("@attribute grade real");
        printWriter.println("@data");
        for (EssayInstance instance : instances) {
            for (String feature : features)
                printWriter.print(instance.getFeature(feature) + ",");
            printWriter.println(instance.getFeature("grade"));
        }
        printWriter.close();
    }

    public static String arffEscapeName(String name) {
        name = name.replaceAll("\\\\Q|\\\\E", "");    // strip \\Q \\E
        name = name.replaceAll("!", "exclamation_mark");
        name = name.replaceAll("\\?", "question_mark");
        name = name.replaceAll("\\.\\*", "dot_star");
        name = name.replaceAll(",", "comma");
        name = name.replaceAll("@", "at_sign");

        return name;
    }

    /**
     * Save the data as an ARFF file where grade is specified as a discrete feature.
     * This type of output class works with Weka, but the machine learning won't
     * take into account that it's really a continuous scale.
     */
    public static void saveARFFDiscreteClass(ArrayList<EssayInstance> instances, String filename) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));

        out.println("% Auto generated by java");
        out.println("@relation training_essay_set_1");

        List<String> features = instances.get(0).listFeatures();
        for (String feature : features) {
            out.println("@attribute " + arffEscapeName(feature) + " real");
        }

        HashMap<Double, int[]> histogram = FeatureAnalyzer.buildHistogram(instances, "grade");
        out.print("@attribute grade {");
        ArrayList<Double> values = new ArrayList<Double>(histogram.keySet());
        Collections.sort(values);
        for (int i = 0; i < values.size(); i++) {
            if (i > 0)
                out.print(",");
            out.print(values.get(i));
        }
        out.println("}");
        out.println("@data");
        for (EssayInstance instance : instances) {
            for (String feature : features)
                out.print(instance.getFeature(feature) + ",");
            out.println(instance.getFeature("grade"));
        }

        out.close();
    }

    /**
     * Save the data as an ARFF file where grade is specified as a binary
     * feature of less-than or greater-than-or-equal-to the threshold.
     * This type of feature works nicely with Weka.
     */
    public static void saveARFFThresholdClass(ArrayList<EssayInstance> instances, String filename, double gradeThreshold) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));

        out.println("% Auto generated by java");
        out.println("@relation training_essay_set_1");

        List<String> features = instances.get(0).listFeatures();
        for (String feature : features) {
            out.println("@attribute " + arffEscapeName(feature) + " real");
        }
        out.println("@attribute grade {0,1}");
        out.println("@data");
        for (EssayInstance instance : instances) {
            for (String feature : features)
                out.print(instance.getFeature(feature) + ",");
            out.println(instance.getFeature("grade") < gradeThreshold ? 0 : 1);
        }

        out.close();
    }


}
