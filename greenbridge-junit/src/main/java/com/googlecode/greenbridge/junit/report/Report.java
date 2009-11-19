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
public class Report {

    private int total_passing;
    private int total_failing;
    private int total_incomplete;

    private transient List<StorySource> storySources;


    /**
     * @return the storySources
     */
    public List<StorySource> getStorySources() {
        return storySources;
    }

    /**
     * @param storySources the storySources to set
     */
    public void setStorySources(List<StorySource> storySources) {
        this.storySources = storySources;
    }

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

    

}
