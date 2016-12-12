package org.xm.essayscoring.tendency;

import org.junit.Test;
import org.xm.tendency.word.HownetWordTendency;

/**
 * @author xuming
 */
public class HownetWordTendencyTest {
    @Test
    public void getTendency() throws Exception {
        HownetWordTendency hownet = new HownetWordTendency();
        double sim = hownet.getTendency("流氓");
        System.out.println(sim);
    }

}