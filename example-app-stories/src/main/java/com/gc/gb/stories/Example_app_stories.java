package com.gc.gb.stories;

import com.gc.gb.scenarios.*;
import com.googlecode.greenbridge.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Example_app_stories implements StoryModule {

    private Map<StoryRef,List<ScenarioRef>> map = new HashMap<StoryRef,List<ScenarioRef>>();

    public Example_app_stories() {
        registerStories();
    }

    public String getName() {
        return "com.gc.gb-example-app-stories";
    }
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    public Map<StoryRef,List<ScenarioRef>> getStories() {
        return map;
    }

    public void registerStories() {
        {
            StoryRef storyRef = new SampleStory1_2();
            List<ScenarioRef> list = new ArrayList<ScenarioRef>();
            list.add(new Scenario1_1());
            list.add(new Scenario2_1());
            map.put(storyRef, list);
        }
        {
            StoryRef storyRef = new SampleStory2_2();
            List<ScenarioRef> list = new ArrayList<ScenarioRef>();
            list.add(new SampleStory2Scenario1_1());
            map.put(storyRef, list);
        }
        {
            StoryRef storyRef = new SampleStory3_2();
            List<ScenarioRef> list = new ArrayList<ScenarioRef>();
            list.add(new TestTable_4());
            map.put(storyRef, list);
        }
    }
}
