 package com.googlecode.greenbridge.annotation;

/**
 * All stories have to extend this base class.
 * 
 * @author ryan
 */
public  interface StoryRef {

    public String storyPackage();
    public String linkUrl();
    public String linkName();
    public String[] narrative();
    public String name();
    public int version();
}
