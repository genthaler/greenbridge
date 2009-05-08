 package org.rsquared.annob.annotation;

/**
 * All stories have to extend this base class.
 * 
 * @author ryan
 */
public  interface StoryRef {

    public String[] narrative();
    public String name();
    public int version();
}
