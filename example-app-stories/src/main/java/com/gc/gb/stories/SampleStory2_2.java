package com.gc.gb.stories;

import com.googlecode.greenbridge.annotation.*;

/**
 * As a user<p>
 * I want to do more<p>
 * So that I profit<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#SampleStory2">See Scenario on the Wiki</a>
 */
public class SampleStory2_2 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "As a user",
                     "I want to do more",
                     "So that I profit",
                     ""
        };
    }

    @Override
    public String name() {
        return "SampleStory2";
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
        return "file://d:/rtemp/demo/example-app-stories/stories.html#SampleStory2";
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