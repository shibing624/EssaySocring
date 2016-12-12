package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * @author xuming
 */
public class PositiveStandard implements Standard {
    @Override
    public double getScore(Essay essay) {
        return 0;
    }

}
