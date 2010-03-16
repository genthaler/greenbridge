/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import com.googlecode.greenbridge.junit.report.ModuleReport.STATE;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class ScenarioReport {

    private STATE state = STATE.pending;

    private String id;
    private String description;
    private String wikiLink;

    private List<ScenarioResult> results = new ArrayList<ScenarioResult>();

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
     * @return the scenario_state
     */
    public STATE getState() {
        return state;
    }

    /**
     * @param scenario_state the scenario_state to set
     */
    public void setScenarioState(STATE state) {
        this.state = state;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the results
     */
    public List<ScenarioResult> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<ScenarioResult> results) {
        this.results = results;
    }

    /**
     * @return the wikiLink
     */
    public String getWikiLink() {
        return wikiLink;
    }

    /**
     * @param wikiLink the wikiLink to set
     */
    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }


}
