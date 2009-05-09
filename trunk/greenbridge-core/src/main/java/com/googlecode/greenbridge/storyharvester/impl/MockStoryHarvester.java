/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import com.googlecode.greenbridge.storyharvester.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.googlecode.greenbridge.ScenarioNarrative;
import com.googlecode.greenbridge.StoryNarrative;

/**
 *
 * @author ryan
 */
public class MockStoryHarvester implements StoryHarvester {

    boolean fail;

    public MockStoryHarvester(boolean fail) {
        this.fail = fail;
    }

    @Override
    public List<StoryNarrative> gather() {
        if (fail) throw new RuntimeException("Intended exception");
        return getStories();
    }


    public static enum COLUMN {
        name, address
    }

    public static List<StoryNarrative> getStories() {



        List<StoryNarrative> stories = new ArrayList<StoryNarrative>();
        {
            StoryNarrative story = new StoryNarrative();
            List<String> asList = Arrays.asList("As a user ", "I want a login,", "So that I can ensure my details are secure.");
            story.setStoryNarrative(asList);
            story.setId("ENDM_130");
            story.setVersion(1);
            List<ScenarioNarrative> scenarios = new ArrayList<ScenarioNarrative>();
            {
                ScenarioNarrative scenario = new ScenarioNarrative(
                        "Given a when a username entered is null and a pass is null",
                        "When the login is processed",
                        "The the system should return with a code 129 error");
                scenario.setId("ENDM_130_1");
                scenario.setVersion(1);
                scenario.setLinkName("Jira");
                scenario.setLinkUrl("http://www.atlassian.com/software/jira/");
                scenario.setStoryNarrative(story);
                scenarios.add(scenario);
                List<String> columns = new ArrayList<String>();
                columns.add("name");
                columns.add("address");
                scenario.setDatatableProperties(columns);

                List<Map<String,String>> data = new ArrayList<Map<String,String>>();
                {
                    Map<String,String> row = new HashMap<String,String>();
                    row.put("name", "Ryan");
                    row.put("address", "123 street rd");
                    data.add(row);
                }

                scenario.setDatatable(data);
            }
            {
                ScenarioNarrative scenario = new ScenarioNarrative(
                        "Given a when a username entered is filled and a pass is null",
                        "When the login is processed",
                        "The the system should return with a code 130 error");
                scenario.setId("ENDM_130_2");
                scenario.setVersion(2);
                scenario.setLinkName("Jira");
                scenario.setLinkUrl("http://www.atlassian.com/software/jira/");
                scenario.setStoryNarrative(story);
                scenarios.add(scenario);

            }
            story.setScenarios(scenarios);
            stories.add(story);
        }
        {
            StoryNarrative story = new StoryNarrative();
            List<String> asList = Arrays.asList("As a user,", "I want to add an address,", "So that I can receive lots of mailings.");
            story.setStoryNarrative(asList);
            story.setId("ENDM_137");
            story.setLinkName("Jira");
            story.setLinkUrl("http://www.atlassian.com/software/jira/");
            List<ScenarioNarrative> scenarios = new ArrayList<ScenarioNarrative>();
            {
                ScenarioNarrative scenario = new ScenarioNarrative(
                        "Given a user and no address",
                        "When a user adds an address of 123 bob st",
                        "Then the address will be the one and only for that user");
                scenario.setId("ENDM_137_1_5");
                scenario.setLinkName("Jira");
                scenario.setLinkUrl("http://www.atlassian.com/software/jira/");
                scenario.setStoryNarrative(story);
                scenarios.add(scenario);

            }
            story.setScenarios(scenarios);
            stories.add(story);
        }

        return stories;
    }

}