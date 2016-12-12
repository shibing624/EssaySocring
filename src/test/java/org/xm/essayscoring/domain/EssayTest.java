package org.xm.essayscoring.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xuming
 */
public class EssayTest {
    @Test
    public void testString() {
        Essay essay = new Essay("boy", new Writer("lili"), "i am a boy");
        String str = essay.toString();
        String splitStr = essay.toString(",");
        Assert.assertTrue(str != null && splitStr != null);
        System.out.println(str);
        System.out.println(splitStr);
        return;
    }
}