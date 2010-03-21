package com.gc.gb;

import com.googlecode.greenbridge.annotation.Scenario;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.junit.GreenbridgeRunner;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static com.gc.gb.scenarios.Scenario1.*;


/**
 * Unit test for simple App.
 */
@RunWith(GreenbridgeRunner.class)
@Story(com.gc.gb.stories.SampleStory1_2.class)
public class UnitLevelTest {

    @Test @Scenario(com.gc.gb.scenarios.Scenario1_1.class)
    public void test1() {
        Given_a_user_has_a_tiddlywiki();
        When_it_is_parsed();
        They_will_get_a_maven_jar();
    }

}
