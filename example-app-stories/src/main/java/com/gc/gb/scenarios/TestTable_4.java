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
 * Given a tiddler with a table<p>
 * When the scenario is parsed<p>
 * It should have a table attached to it.<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#TestTable">See Scenario on the Wiki</a>
 */
public class TestTable_4 extends TestTable implements ScenarioRef {

    @Override
    public int version() {
        return 4;
    }

    @Override
    public Class<? extends StoryRef> getStoryRef() {
        return SampleStory3_2.class;
    }


}