/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class ModuleReport {

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public static enum STATE {passed, failed, pending};

    private STATE state = STATE.pending;
    private String name;
    private String version;

    private  List<StoryReport> storyReports = new ArrayList<StoryReport>();


    private int total_passing;
    private int total_failing;
    private int total_pending;
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
    public int getTotal_pending() {
        return total_pending;
    }

    /**
     * @param total_incomplete the total_incomplete to set
     */
    public void setTotal_pending(int total_pending) {
        this.total_pending = total_pending;
    }




    /**
     * @return the state
     */
    public STATE getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(STATE state) {
        this.state = state;
    }

    /**
     * @return the sourceName
     */
    public String getName() {
        return name;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setName(String name) {
        this.name = name;
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
