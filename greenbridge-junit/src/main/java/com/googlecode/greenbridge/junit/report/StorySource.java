/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.util.List;

/**
 *
 * @author ryan
 */
public class StorySource {

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
    public static enum SOURCE_STATE {PASSED, FAILED, INCOMPLETE};

    private SOURCE_STATE state;
    private String sourceName;

    private transient List<StoryReport> storyReports;


    private int total_passing;
    private int total_failing;
    private int total_incomplete;



    /**
     * @return the state
     */
    public SOURCE_STATE getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(SOURCE_STATE state) {
        this.state = state;
    }

    /**
     * @return the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * @return the storyReports
     */
    public List<StoryReport> getStoryReports() {
        return storyReports;
    }

    /**
     * @param storyReports the storyReports to set
     */
    public void setStoryReports(List<StoryReport> storyReports) {
        this.storyReports = storyReports;
    }




}
