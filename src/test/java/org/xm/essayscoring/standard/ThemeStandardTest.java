package org.xm.essayscoring.standard;

import org.junit.Assert;
import org.junit.Test;
import org.xm.essayscoring.domain.Essay;
import org.xm.essayscoring.domain.Writer;

import static org.xm.essayscoring.domain.EssayTest.text;


/**
 * @author xuming
 */
public class ThemeStandardTest {
    @Test
    public void getScore() throws Exception {
        String title = "职位介绍";
        Essay essay = new Essay(title, new Writer("lili"), text);
        ThemeStandard themeStandard = new ThemeStandard();
        Double d = themeStandard.getScore(essay);
        System.out.println(d);
        Assert.assertTrue(d > 0.0);
    }

}

//算法工程师是什么=算法=算法工程师 31.3
//工程师是什么 19.85
//苹果 0.93
//职位=职位介绍 0.92
//结合TF-IDF处理主题词 0.54

