/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.junit;

import java.util.Date;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.rsquared.annob.annotation.ScenarioRef;
/**
 *
 * @author ryan
 */
public class ScenarioResult {
    private ScenarioRef scenario;
    private RunState state;
    private Description description;
    private Failure failure;
    private Date startTime;
    private Date endTime;
    private Long duration;

    public long getDuration() {
        if (duration != null) return duration;
        if (endTime == null || startTime == null) {
            return 0;
        }
        if (duration == null) {
            duration = endTime.getTime() - startTime.getTime();
        }
        return duration;

    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    /**
     * @return the scenario
     */
    public ScenarioRef  getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public void setScenario(ScenarioRef  scenario) {
        this.scenario = scenario;
    }

    /**
     * @return the state
     */
    public RunState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(RunState state) {
        this.state = state;
    }

    /**
     * @return the description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * @return the failure
     */
    public Failure getFailure() {
        return failure;
    }

    /**
     * @param failure the failure to set
     */
    public void setFailure(Failure failure) {
        this.failure = failure;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
