package com.googlecode.greenbridge.annotation;

/**
`* All scenarios have to extend this base class.
 * 
 * @author ryan
 */
public interface ScenarioRef {

    public String[] narrative();
    public String name();
    public long version();
    public String linkUrl();
    public String linkName();
    public Class<? extends StoryRef> getStoryRef();
}
