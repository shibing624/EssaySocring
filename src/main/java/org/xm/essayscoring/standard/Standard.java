package org.xm.essayscoring.standard;

import org.xm.essayscoring.domain.Essay;

/**
 * @author xuming
 */
public abstract class Standard {

    public Standard(){

    }

    public abstract double getScore(Essay essay);

}
