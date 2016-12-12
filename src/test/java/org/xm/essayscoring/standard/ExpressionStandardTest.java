package org.xm.essayscoring.standard;

import org.junit.Assert;
import org.junit.Test;

import static org.xm.essayscoring.domain.EssayTest.testEssay;

/**
 * @author xuming
 */
public class ExpressionStandardTest {
    @Test
    public void getScore() throws Exception {
        ExpressionStandard ep = new ExpressionStandard();
        double d = ep.getScore(testEssay);
        System.out.println(d);
        Assert.assertTrue(d>0.0);
    }

}