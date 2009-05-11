/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.Story2121;
import com.googlecode.greenbridge.annotation.StoryRef;
import org.junit.runner.Description;

/**
 *
 * @author ryan
 */
public class StoryResultsFactory {

    public static StoryResults createMockResults() {
        StoryRef storyRef = new MockStoryRef();
        StoryResults results = new StoryResults(storyRef);
        {
            MockScenarioRef scenarioRef = new MockScenarioRef();
            ScenarioResult result = new ScenarioResult();
            result.setScenario(scenarioRef);
            Description description = Description.createTestDescription(Story2121.class, "alpha");
            result.setDescription(description);
            result.setScenario(scenarioRef);
            result.setState(RunState.PASSED);
            result.setDuration(100);
            results.addResult(result);
            
        }
        return results;
    }

}
