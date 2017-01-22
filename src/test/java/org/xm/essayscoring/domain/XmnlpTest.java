package org.xm.essayscoring.domain;

import org.junit.Test;
import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.seg.domain.Term;

import java.util.List;

/**
 * @author xuming
 */
public class XmnlpTest {
    @Test
    public void test(){
        List<Term> terms = Xmnlp.segment(EssayTest.text);
        terms.forEach(System.out::println);

    }
}
