package org.xm.essayscoring.features;

import org.xm.essayscoring.EssayScoring;
import org.xm.essayscoring.domain.EssayInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author xuming
 */
public class GaussianNormailizerFeature implements Features {
    public enum Type {
        ZSCORE,
        ABS_ZSCORE,
        NORMAL_PROB
    }

    private HashMap<Integer, double[]> means;
    private HashMap<Integer, double[]> stddev;
    private Features baseFeature;
    private String baseName;
    private String name;
    private Type type;

    /**
     * Learn the mean and stddev of the feature
     *
     * @param trainingSample the instance
     * @param base
     * @param baseName
     * @param type
     */
    public GaussianNormailizerFeature(ArrayList<EssayInstance> trainingSample, Features base, String baseName, Type type) {
        means = new HashMap<>();
        stddev = new HashMap<>();
        this.baseFeature = base;
        this.baseName = baseName;
        this.name = baseName + "_" + type.toString().toLowerCase() + "_norm_task";
        this.type = type;
        HashMap<Integer, int[]> docs = new HashMap<>();
        // compute the sums
        for (EssayInstance instance : trainingSample) {
            double value = getBaseValue(instance);
            if (!means.containsKey(instance.set)) means.put(instance.set, new double[]{value});
            else means.get(instance.set)[0] += value;

            if (!docs.containsKey(instance.set)) docs.put(instance.set, new int[]{1});
            else docs.get(instance.set)[0]++;
        }
        // convert to mean
        for (Integer i : means.keySet()) means.get(i)[0] /= docs.get(i)[0];
        // compute standard deviation
        for (EssayInstance instance : trainingSample) {
            double value = getBaseValue(instance);
            double dev = means.get(instance.set)[0] - value;
            dev *= dev;
            if (!stddev.containsKey(instance.set))
                stddev.put(instance.set, new double[]{dev});
            else stddev.get(instance.set)[0] += dev;
        }
        // normalize the stddev
        for (Integer i : stddev.keySet()) stddev.get(i)[0] = Math.sqrt(stddev.get(i)[0] / (docs.get(i)[0] - 1));
        // debug
        if (EssayScoring.DEBUG) {
            List<Integer> tasks = new ArrayList<>(means.keySet());
            Collections.sort(tasks);
            for (Integer task : tasks) {
                System.out.println("Feature " + baseName + " for task/set " + task + ":");
                System.out.println("\tx: " + means.get(task)[0]);
                System.out.println("\ts: " + stddev.get(task)[0]);
            }
        }
    }

    @Override
    public HashMap<String, Double> getFeatureScores(EssayInstance instance) {
        HashMap<String, Double> values = new HashMap<>();
        double value = getBaseValue(instance);
        // missing features
        assert (means.containsKey(instance.set) && stddev.containsKey(instance.set));
        double tempMean = means.get(instance.set)[0];
        double tempStddev = stddev.get(instance)[0];
        // not zero
        assert (tempStddev != 0);
        double core = (value - tempMean) / tempStddev;
        if (type == Type.ABS_ZSCORE) core = Math.abs(core);
        else if (type == Type.NORMAL_PROB)
            core = (Math.exp(-Math.pow(core, 2) / 2)) / (tempStddev * Math.sqrt(2 * Math.PI));
        values.put(name, core);
        return values;
    }

    /**
     * Checks to see if base value is already computed.
     * Otherwise computes the value and stores it.
     * @param instance
     * @return
     */
    private double getBaseValue(EssayInstance instance) {
        Double value = instance.getFeature(baseName);
        if (value == null) {
            // duplicate features
            HashMap<String, Double> values = baseFeature.getFeatureScores(instance);
            instance.setFeature(baseName, values.get(baseName));
            value = values.get(baseName);
        }
        return value.doubleValue();
    }
}
