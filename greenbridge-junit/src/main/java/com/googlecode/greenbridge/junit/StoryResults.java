/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.Description;
import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;

/**
 *
 * @author ryan
 */
public class StoryResults {
    private StoryRef storyRef;
    private Map<ScenarioRef , List<ScenarioResult>> scenarioResults;

    public StoryResults(StoryRef storyRef) {
        this.storyRef = storyRef;
        scenarioResults = new HashMap<ScenarioRef , List<ScenarioResult>>();
    }

    public void addResult(ScenarioResult result) {
        List<ScenarioResult> results = scenarioResults.get(result.getScenario());
        if (results == null) {
            results = new ArrayList<ScenarioResult>();
            scenarioResults.put(result.getScenario(), results);
        }
        results.add(result);
    }


    public ScenarioResult findResultByScenarioAndDescription(ScenarioRef  scenario, Description description) {
        List<ScenarioResult> results = scenarioResults.get(scenario);
        if (results == null) return null;
        for (ScenarioResult scenarioResult : results) {
            if (description.equals(scenarioResult.getDescription())) {
                return scenarioResult;
            }
        }
        // nothing was found
        return null;
    }

    /**
     * @return the story
     */
    public StoryRef getStory() {
        return storyRef;
    }

    /**
     * @param story the story to set
     */
    public void setStory(StoryRef storyRef) {
        this.storyRef = storyRef;
    }

    /**
     * @return the scenarioResults
     */
    public Map<ScenarioRef , List<ScenarioResult>> getScenarioResults() {
        return scenarioResults;
    }

    /**
     * @param scenarioResults the scenarioResults to set
     */
    public void setScenarioResults(Map<ScenarioRef , List<ScenarioResult>> scenarioResults) {
        this.scenarioResults = scenarioResults;
    }
}
