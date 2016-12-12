package org.xm.essayscoring.standard;

import org.junit.Assert;
import org.junit.Test;
import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.domain.EssayTest;
import org.xm.essayscoring.domain.Writer;


/**
 * @author xuming
 */
public class ThemeStandardTest {
    @Test
    public void getScore() throws Exception {
        Essay essay = new Essay("算法工程师是什么", new Writer("lili"), EssayTest.text);
        ThemeStandard themeStandard = new ThemeStandard();
        Double d = themeStandard.getScore(essay);
        System.out.println(d);
        Assert.assertTrue(d > 0.0);
    }

}