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
public class StoryReport {

    private STATE state = STATE.pending;
    private String id;
    private String description;
    private String wikiLink;

    private  List<ScenarioReport> scenarioReports = new ArrayList<ScenarioReport>();


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
     * @return the scenario_reports
     */
    public List<ScenarioReport> getScenarioReports() {
        return scenarioReports;
    }

    /**
     * @param scenario_reports the scenario_reports to set
     */
    public void setScenarioReports(List<ScenarioReport> scenarioReports) {
        this.scenarioReports = scenarioReports;
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
