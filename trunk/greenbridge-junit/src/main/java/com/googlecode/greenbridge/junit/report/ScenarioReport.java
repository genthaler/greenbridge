/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import com.googlecode.greenbridge.annotation.ScenarioRef;

/**
 *
 * @author ryan
 */
public class ScenarioReport {

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
    public static enum TEST_STATE {PASSED, FAILED, IGNORED, NO_TEST};

    private TEST_STATE scenario_state;
    private ScenarioRef scenario;


    private int total_passing;
    private int total_failing;
    private int total_ignored;
    private int total_incomplete;


    /**
     * @return the scenario_state
     */
    public TEST_STATE getScenario_state() {
        return scenario_state;
    }

    /**
     * @param scenario_state the scenario_state to set
     */
    public void setScenario_state(TEST_STATE scenario_state) {
        this.scenario_state = scenario_state;
    }

    /**
     * @return the scenario
     */
    public ScenarioRef getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public void setScenario(ScenarioRef scenario) {
        this.scenario = scenario;
    }





}
