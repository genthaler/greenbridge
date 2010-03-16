/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.annotation.StoryRef;
import com.googlecode.greenbridge.junit.report.ModuleReport.STATE;
import com.googlecode.greenbridge.junit.report.ScenarioReport;
import com.googlecode.greenbridge.junit.report.StoryReport;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.Description;

/**
 *
 * @author ryan
 */
public class StoryResultsXmlOutput implements Output {

    @Override
    public String getExtension() {
        return ".result.xml";
    }

    @Override
    public File write(ScenarioResult result, File basedirectory) {
        File f = new File(basedirectory, result.getScenario().name() + getExtension());
        System.out.println("Wrinting: " + f.getAbsolutePath());
        com.googlecode.greenbridge.junit.report.ScenarioResult reportResult = new com.googlecode.greenbridge.junit.report.ScenarioResult();
        reportResult.setClazz(getClassFromDescription(result.getDescription()));
        reportResult.setMethod(getMethodFromDescription(result.getDescription()));
        XStream stream = new XStream();
        try {
            stream.toXML(reportResult, new FileWriter(f));
        } catch (IOException ex) {
            Logger.getLogger(StoryResultsXmlOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

        return f;
    }





    @Override
    public void write(StoryResults results, PrintWriter stream) {
        StoryReport storyReport = new StoryReport();
        storyReport.setId(results.getStory().name());
        storyReport.setState(null);
        Map<ScenarioRef, List<ScenarioResult>> scenarioResults = results.getScenarioResults();
        for (Iterator<ScenarioRef> it = scenarioResults.keySet().iterator(); it.hasNext();) {
            ScenarioRef scenarioRef = it.next();
            ScenarioReport scenarioReport = new ScenarioReport();
            scenarioReport.setScenarioState(null);
            scenarioReport.setId(scenarioRef.name());
            storyReport.getScenarioReports().add(scenarioReport);
            List<ScenarioResult> ss = scenarioResults.get(scenarioRef);
            for (Iterator<ScenarioResult> it2 = ss.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();

                com.googlecode.greenbridge.junit.report.ScenarioResult reportResult = new com.googlecode.greenbridge.junit.report.ScenarioResult();
                reportResult.setClazz(getClassFromDescription(scenarioResult.getDescription()));
                reportResult.setMethod(getMethodFromDescription(scenarioResult.getDescription()));
                reportResult.setState(translate(scenarioResult.getState()));
                if (RunState.FAILED.equals(scenarioResult.getState())) {
                    reportResult.setErrorMessage(scenarioResult.getFailure().getMessage());
                    reportResult.setErrorTrace(scenarioResult.getFailure().getTrace());
                }

                scenarioReport.getResults().add(reportResult);
            }
         }
        XStream xstream = new XStream();
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.toXML(storyReport, stream);
    }


    private STATE translate(RunState state) {
        if (state.equals(state.FAILED)) return STATE.failed;
        if (state.equals(state.PASSED)) return STATE.passed;
        return STATE.pending;
    }

    private String getClassFromDescription(Description description) {
        String text = description.getDisplayName();
        return text.substring(text.indexOf("(") + 1, text.indexOf(")"));
    }

    private String getMethodFromDescription(Description description) {
        String text = description.getDisplayName();
        return text.substring(0, text.indexOf("("));
    }

}
