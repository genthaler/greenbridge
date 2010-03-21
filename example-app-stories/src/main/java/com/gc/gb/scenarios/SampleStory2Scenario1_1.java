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
 * Given when then<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#SampleStory2Scenario1">See Scenario on the Wiki</a>
 */
public class SampleStory2Scenario1_1 extends SampleStory2Scenario1 implements ScenarioRef {

    @Override
    public int version() {
        return 1;
    }

    @Override
    public Class<? extends StoryRef> getStoryRef() {
        return SampleStory2_2.class;
    }


}