/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import com.googlecode.greenbridge.annotation.StoryRef;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class StoryReport {

    /**
     * @return the total_passing
     */
    public int getTotal_passing() {
        return total_passing;
    }

    /**
     * @param total_passing the total_passing to set
     */
    public void setTotal_passing(int total_passing) {
        this.total_passing = total_passing;
    }

    /**
     * @return the total_failing
     */
    public int getTotal_failing() {
        return total_failing;
    }

    /**
     * @param total_failing the total_failing to set
     */
    public void setTotal_failing(int total_failing) {
        this.total_failing = total_failing;
    }

    /**
     * @return the total_ignored
     */
    public int getTotal_ignored() {
        return total_ignored;
    }

    /**
     * @param total_ignored the total_ignored to set
     */
    public void setTotal_ignored(int total_ignored) {
        this.total_ignored = total_ignored;
    }

    /**
     * @return the total_incomplete
     */
    public int getTotal_incomplete() {
        return total_incomplete;
    }

    /**
     * @param total_incomplete the total_incomplete to set
     */
    public void setTotal_incomplete(int total_incomplete) {
        this.total_incomplete = total_incomplete;
    }
    public static enum STORY_STATE {PASSED, FAILED, INCOMPLETE};

    private STORY_STATE state;
    private StoryRef storyRef;
    private transient List<ScenarioReport> scenario_reports = new ArrayList<ScenarioReport>();


    private int total_passing;
    private int total_failing;
    private int total_ignored;
    private int total_incomplete;



    /**
     * @return the state
     */
    public STORY_STATE getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(STORY_STATE state) {
        this.state = state;
    }

    /**
     * @return the scenario_reports
     */
    public List<ScenarioReport> getScenario_reports() {
        return scenario_reports;
    }

    /**
     * @param scenario_reports the scenario_reports to set
     */
    public void setScenario_reports(List<ScenarioReport> scenario_reports) {
        this.scenario_reports = scenario_reports;
    }

    /**
     * @return the storyRef
     */
    public StoryRef getStoryRef() {
        return storyRef;
    }

    /**
     * @param storyRef the storyRef to set
     */
    public void setStoryRef(StoryRef storyRef) {
        this.storyRef = storyRef;
    }


    
    

}
