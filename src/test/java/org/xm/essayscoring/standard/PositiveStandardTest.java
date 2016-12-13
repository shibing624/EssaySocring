package org.xm.essayscoring.standard;

import org.junit.Assert;
import org.junit.Test;

import static org.xm.essayscoring.domain.EssayTest.testEssay;

/**
 * @author xuming
 */
public class PositiveStandardTest {
    @Test
    public void getScore() throws Exception {
        System.out.println("PositiveStandardTest");
        PositiveStandard positiveStandard = new PositiveStandard();
        double d = positiveStandard.getScore(testEssay);
        Assert.assertTrue(d !=0.0);
        System.out.println(d);

    }

}