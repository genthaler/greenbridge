/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.junit;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.easymock.classextension.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.rsquared.annob.annotation.AMPEDM_128_3;
import org.rsquared.annob.annotation.ScenarioRef;
import org.rsquared.annob.annotation.Story2121;
import org.rsquared.annob.annotation.StoryRef;
import static org.junit.Assert.*;
import org.rsquared.annob.junit.JUnitXMLOutput.Summary;

/**
 *
 * @author ryan
 */
public class JUnitXMLOutputTest {

    public JUnitXMLOutputTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getExtension method, of class JUnitXMLOutput.
     */
    @Test
    public void testGetExtension() {
        System.out.println("getExtension");
        JUnitXMLOutput instance = new JUnitXMLOutput();
        String expResult = ".xml";
        String result = instance.getExtension();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateSummary method, of class JUnitXMLOutput.
     */
    @Test
    public void testCalculateSummary() {
        System.out.println("calculateSummary");
        StoryRef storyref = new Story2121();
        ScenarioRef scenario1 = new AMPEDM_128_3();
        StoryResults results = new StoryResults(storyref);

        Map<ScenarioRef,List<ScenarioResult>> map = new HashMap<ScenarioRef, List<ScenarioResult>>();
        results.setScenarioResults(map);

        List<ScenarioResult> scenarioResults = new ArrayList<ScenarioResult>();
        {
            ScenarioResult result = new ScenarioResult();
            Description description = Description.createTestDescription(Story2121.class, "alpha");
            result.setDescription(description);
            result.setScenario(scenario1);
            result.setState(RunState.PASSED);
            result.setDuration(100);
            scenarioResults.add(result);

        }
        {
            ScenarioResult result = new ScenarioResult();
            Description description = Description.createTestDescription(Story2121.class, "beta");
            result.setDescription(description);
            result.setScenario(scenario1);
            result.setState(RunState.PASSED);
            result.setDuration(200);
            scenarioResults.add(result);
        }
        {
            ScenarioResult result = new ScenarioResult();
            Description description = Description.createTestDescription(Story2121.class, "beta");
            result.setDescription(description);
            result.setScenario(scenario1);
            result.setState(RunState.FAILED);
            result.setDuration(200);
            scenarioResults.add(result);
        }
        {
            ScenarioResult result = new ScenarioResult();
            Description description = Description.createTestDescription(Story2121.class, "beta");
            result.setDescription(description);
            result.setScenario(scenario1);
            result.setState(RunState.PENDING);
            result.setDuration(200);
            scenarioResults.add(result);
        }
        map.put(scenario1, scenarioResults);
        
        JUnitXMLOutput instance = new JUnitXMLOutput();
        Summary result = instance.calculateSummary(results);
        assertEquals(700, result.time);
        assertEquals(4, result.tests);
        assertEquals(1, result.skipped);
        assertEquals(1, result.failures);
    }

}