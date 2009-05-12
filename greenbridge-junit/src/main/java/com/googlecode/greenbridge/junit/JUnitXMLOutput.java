/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.runner.notification.Failure;
import com.googlecode.greenbridge.annotation.ScenarioRef;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan
 */
public class JUnitXMLOutput implements Output {

    @Override
    public String getExtension() {
        return ".xml";
    }

    @Override
    public void write(StoryResults results, PrintWriter stream) {
        writeHeader(stream);
        Summary summary = calculateSummary(results);
        writeSummary(results.getStory().name(), summary, stream);
        writeTestCases(results.getScenarioResults(), stream);
        stream.println("</testsuite>");
    }

    @Override
    public File write(ScenarioResult result, File basedirectory) {
        File f = new File(basedirectory, result.getScenario().name() + getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            writeHeader(stream);
            Summary summary = new Summary();
            summary = addScenarioResult(result, summary);
            writeSummary(result.getScenario().name(), summary, stream);
            writeTestCase(result, stream);
            stream.println("</testsuite>");
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    protected void writeHeader(PrintWriter stream) {
        stream.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
    }

    protected void writeSummary(String name, Summary summary, PrintWriter stream) {
            //TODO convert tume from ms to seconds
        stream.println("<testsuite failures=\"" + summary.failures +
                       "\" time=\"0.078\" errors=\"0\" skipped=\"" +
                       summary.skipped + "\" tests=\"" + summary.tests +
                       "\" name=\"" + name +  "\" >");

    }

    protected void writeTestCases(Map<ScenarioRef, List<ScenarioResult>> scenarioResults, PrintWriter stream) {
        for (Iterator<ScenarioRef> it = scenarioResults.keySet().iterator(); it.hasNext();) {
            ScenarioRef scenarioRef = it.next();
            List<ScenarioResult> ss = scenarioResults.get(scenarioRef);
            for (Iterator<ScenarioResult> it2 = ss.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();
                writeTestCase(scenarioResult, stream);
            }
        }
    }

    private void writeTestCase(ScenarioResult scenarioResult, PrintWriter stream) {
        if (scenarioResult.getState() == RunState.PENDING) {
            writePendingTestCase(scenarioResult, stream);
        }
        if (scenarioResult.getState() == RunState.FAILED) {
            writeFailedTestCase(scenarioResult, stream);
        }
        if (scenarioResult.getState() == RunState.PASSED) {
            writePassedTestCase(scenarioResult, stream);
        }
    }

    private void writeFailedTestCase(ScenarioResult scenarioResult, PrintWriter stream) {
        writeTestCaseStartTag(scenarioResult, stream, false);
        writeErrorTagScenarioResult(scenarioResult, stream);
        writeTestCaseCloseTag(stream);
    }

    private void writeErrorTagScenarioResult(ScenarioResult scenarioResult, PrintWriter stream) {
        Failure fail = scenarioResult.getFailure();
        stream.print("\t\t<error message=\"" + fail.getMessage() + "\" type=\"" + fail.getException().getClass().getName() + "\">");
        stream.print(fail.getTrace());
        stream.println("\t\t</error>");
    }


    private void writePassedTestCase(ScenarioResult scenarioResult, PrintWriter stream) {
        writeTestCaseStartTag(scenarioResult, stream, true);
    }

    private void writePendingTestCase(ScenarioResult scenarioResult, PrintWriter stream) {
        writeTestCaseStartTag(scenarioResult, stream, false);
        stream.println("\t\t<skipped/>");
        writeTestCaseCloseTag(stream);
    }

    private void writeTestCaseStartTag(ScenarioResult scenarioResult, PrintWriter stream, boolean endTag) {
        String end = "";
        if (endTag) {
            end = "/";
        }
        stream.println("\t<testcase time=\"" + scenarioResult.getDuration()
                + "\" classname=\"\" name=\"" + scenarioResult.getScenario().name() +"\" " + end + ">");
    }

    private void writeTestCaseCloseTag( PrintWriter stream) {
        stream.println("\t</testcase>");
    }



    protected Summary calculateSummary(StoryResults results) {
        Summary summary = new Summary();
        if (results.getScenarioResults().values() == null) {
            return summary;
        }
        for (Iterator<List<ScenarioResult>> it = results.getScenarioResults().values().iterator(); it.hasNext();) {
            List<ScenarioResult> resultSet = it.next();
            for (Iterator<ScenarioResult> it2 = resultSet.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();
                summary = addScenarioResult(scenarioResult, summary);
            }
        }
        return summary;
    }



    protected Summary addScenarioResult(ScenarioResult scenarioResult, Summary summary) {
        summary.time+= scenarioResult.getDuration();
        summary.tests++;
        if (scenarioResult.getState().equals(RunState.FAILED)) {
            summary.failures++;
        }
        if (scenarioResult.getState().equals(RunState.PENDING)) {
            summary.skipped++;
        }
        return summary;
    }


    protected class Summary {
        protected long time = 0;
        protected int failures = 0;
        protected int errors = 0;
        protected int skipped = 0;
        protected int tests = 0;

    }

}
