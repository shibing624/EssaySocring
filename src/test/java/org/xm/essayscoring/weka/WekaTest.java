package org.xm.essayscoring.weka;

import org.junit.Test;
import weka.classifiers.functions.LinearRegression;
import weka.core.*;

/**
 * @author xuming
 */
public class WekaTest {
    @Test
    public void test1() {
        Attribute a1 = new Attribute("houseSize", 0);
        Attribute a2 = new Attribute("lotSize", 1);
        Attribute a3 = new Attribute("bedrooms", 2);
        Attribute a4 = new Attribute("granite", 3);
        Attribute a5 = new Attribute("bathroom", 4);
        Attribute a6 = new Attribute("sellingPrice", 5);

        FastVector attrs = new FastVector();
        attrs.addElement(a1);
        attrs.addElement(a2);
        attrs.addElement(a3);
        attrs.addElement(a4);
        attrs.addElement(a5);
        attrs.addElement(a6);

        Instance i1 = new DenseInstance(6);
        i1.setValue(a1, 3529);
        i1.setValue(a2, 9191);
        i1.setValue(a3, 6);
        i1.setValue(a4, 0);
        i1.setValue(a5, 0);
        i1.setValue(a6, 205000);

        Instance i2 = new DenseInstance(6);
        i2.setValue(a1, 3247);
        i2.setValue(a2, 10061);
        i2.setValue(a3, 5);
        i2.setValue(a4, 1);
        i2.setValue(a5, 1);
        i2.setValue(a6, 224900);

        Instance i3 = new DenseInstance(6);
        i3.setValue(a1, 4032);
        i3.setValue(a2, 10150);
        i3.setValue(a3, 5);
        i3.setValue(a4, 0);
        i3.setValue(a5, 1);
        i3.setValue(a6, 197900);

        Instance i4 = new DenseInstance(6);
        i4.setValue(a1, 2397);
        i4.setValue(a2, 14156);
        i4.setValue(a3, 4);
        i4.setValue(a4, 1);
        i4.setValue(a5, 0);
        i4.setValue(a6, 189900);

        Instance i5 = new DenseInstance(6);
        i5.setValue(a1, 2200);
        i5.setValue(a2, 9600);
        i5.setValue(a3, 4);
        i5.setValue(a4, 0);
        i5.setValue(a5, 1);
        i5.setValue(a6, 195000);


        Instance i7 = new DenseInstance(6);
        i7.setValue(a1, 3536);
        i7.setValue(a2, 19994);
        i7.setValue(a3, 6);
        i7.setValue(a4, 1);
        i7.setValue(a5, 1);
        i7.setValue(a6, 325000);

        Instance i8 = new DenseInstance(6);
        i8.setValue(a1, 2983);
        i8.setValue(a2, 9365);
        i8.setValue(a3, 5);
        i8.setValue(a4, 0);
        i8.setValue(a5, 1);
        i8.setValue(a6, 230000);


        Instances dataset = new Instances("housePrices", attrs, 7);
        dataset.add(i1);
        dataset.add(i2);
        dataset.add(i3);
        dataset.add(i4);
        dataset.add(i5);
        dataset.add(i7);
        dataset.add(i8);

        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Create the LinearRegression model, which is the data mining
// model we're using in this example
        LinearRegression linearRegression = new LinearRegression();

// This method does the "magic", and will compute the regression
// model.  It takes the entire dataset we've defined to this point
// When this method completes, all our "data mining" will be complete
// and it is up to you to get information from the results
        try {
            linearRegression.buildClassifier(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }

// We are most interested in the computed coefficients in our model,
// since those will be used to compute the output values from an
// unknown data instance.
        double[] coef = linearRegression.coefficients();

// Using the values from my house (from the first article), we
// plug in the values and multiply them by the coefficients
// that the regression model created.  Note that we skipped
// coefficient[5] as that is 0, because it was the output
// variable from our training data
        double myHouseValue = (coef[0] * 3198) +
                (coef[1] * 9669) +
                (coef[2] * 5) +
                (coef[3] * 3) +
                (coef[4] * 1) +
                coef[6];

        System.out.println(myHouseValue);
// outputs 219328.35717359098
// which matches the output from the earlier article
    }
}
