package com.gc.gb.stories;

import com.googlecode.greenbridge.annotation.*;

/**
 * As a developer<p>
 * I want to have tables<p>
 * so that I can drive tests with data<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#SampleStory3">See Scenario on the Wiki</a>
 */
public class SampleStory3_2 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "As a developer",
                     "I want to have tables",
                     "so that I can drive tests with data",
                     ""
        };
    }

    @Override
    public String name() {
        return "SampleStory3";
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
        return "file://d:/rtemp/demo/example-app-stories/stories.html#SampleStory3";
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