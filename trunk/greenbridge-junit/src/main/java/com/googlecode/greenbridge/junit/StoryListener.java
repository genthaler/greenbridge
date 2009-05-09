/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import com.googlecode.greenbridge.annotation.Scenario;
import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;

/**
 *
 * @author ryan
 */
public class StoryListener extends RunListener {

    private StoryResults storyResults;
    private Map<Scenario, ScenarioRef> singletonRef;

    public StoryListener(StoryRef storyRef) {
        storyResults = new StoryResults(storyRef);
        singletonRef = new HashMap<Scenario, ScenarioRef>();
    }


    public ScenarioRef getScenario(Description description) throws Exception {
        Scenario scenarioAnno = description.getAnnotation(Scenario.class);
        ScenarioRef ref = singletonRef.get(scenarioAnno);
        if (ref == null) {
            ref = scenarioAnno.value().newInstance();
            singletonRef.put(scenarioAnno, ref);
        }
        return ref;
    }

	/**
	 * Called when an atomic test is about to be started.
	 * @param description the description of the test that is about to be run
	 * (generally a class and method name)
	 */
    @Override
	public void testStarted(Description description) throws Exception {
        ScenarioResult result = new ScenarioResult();
        result.setScenario(getScenario(description));
        result.setStartTime(new Date());
        result.setDescription(description);
        result.setState(RunState.STARTED);
        getStoryResults().addResult(result);
	}


	/**
	 * Called when an atomic test has finished, whether the test succeeds or fails.
	 * @param description the description of the test that just ran
	 */
    @Override
	public void testFinished(Description description) throws Exception {
        ScenarioRef  scenario = getScenario(description);
        ScenarioResult result = getStoryResults().findResultByScenarioAndDescription(scenario, description);
        if (result !=null ) {
            result.setEndTime(new Date());
        }
        if (result != null && result.getState() == RunState.STARTED) {
            result.setState(RunState.PASSED);
            
        }
    }

	/**
	 * Called when an atomic test fails.
	 * @param failure describes the test that failed and the exception that was thrown
	 */
    @Override
	public void testFailure(Failure failure) throws Exception {
        testAssumptionFailure(failure);
	}

	/**
	 * Called when an atomic test flags that it assumes a condition that is
	 * false
	 *
	 * @param failure
	 *            describes the test that failed and the
	 *            {@link AssumptionViolatedException} that was thrown
	 */
    @Override
	public void testAssumptionFailure(Failure failure) {
        ScenarioRef  scenario;
        try {
            scenario = getScenario(failure.getDescription());
            ScenarioResult result = getStoryResults().findResultByScenarioAndDescription(scenario, failure.getDescription());
            if (result != null) {
                result.setState(RunState.FAILED);
                result.setFailure(failure);
            }
        } catch (Exception ex) {
            Logger.getLogger(StoryListener.class.getName()).log(Level.SEVERE, null, ex);
        }

	}

	/**
	 * Called when a test will not be run, generally because a test method is annotated
	 * with {@link org.junit.Ignore}.
	 *
	 * @param description describes the test that will not be run
	 */
    @Override
	public void testIgnored(Description description) throws Exception {
        ScenarioResult result = new ScenarioResult();
        result.setScenario(getScenario(description));
        result.setStartTime(new Date());
        result.setDescription(description);
        result.setState(RunState.PENDING);
        result.setEndTime(new Date());
        getStoryResults().addResult(result);
	}

    /**
     * @return the storyResults
     */
    public StoryResults getStoryResults() {
        return storyResults;
    }
}
