package com.gc.gb.stories;

import com.googlecode.greenbridge.annotation.*;

/**
 * As A User<p>
 * I want to store my stories in a easy to navigate wiki<p>
 * So that I can cross link ideas.<p>
 * 
 * 
 * @see <a href="http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#SampleStory1">See Scenario on the Wiki</a>
 */
public class SampleStory1_2 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "As A User",
                     "I want to store my stories in a easy to navigate wiki",
                     "So that I can cross link ideas.",
                     ""
        };
    }

    @Override
    public String name() {
        return "SampleStory1";
    }

    @Override
    public int version() {
        return 2;
    }

    @Override
    public String storyPackage() {
        return "com.gc.gb:example-app-stories:1.0-SNAPSHOT";
    }

    @Override
    public String linkUrl() {
        return "http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#SampleStory1";
    }

    @Override
    public String linkName() {
        return "See Scenario on the Wiki";
    }

    @Override
    public Class<? extends StoryModule> getStoryModule() {
        return Example_app_stories.class;
    }

}