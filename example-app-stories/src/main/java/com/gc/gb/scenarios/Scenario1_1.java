package com.gc.gb.scenarios;

import com.googlecode.greenbridge.annotation.*;
import com.googlecode.greenbridge.junit.ParameterizableList;
import com.gc.gb.stories.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a user has a tiddlywiki<p>
 * When it is parsed<p>
 * They will get a maven jar<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#Scenario1">See Scenario on the Wiki</a>
 */
public class Scenario1_1 extends Scenario1 implements ScenarioRef {

    @Override
    public int version() {
        return 1;
    }

    @Override
    public Class<? extends StoryRef> getStoryRef() {
        return SampleStory1_2.class;
    }


}