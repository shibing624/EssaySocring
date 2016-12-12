package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * @author xuming
 */
public interface Standard {
    double getScore(Essay essay);

}
