/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 *
 * @author ryan
 */
public class JSONReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(Report report, File destination) throws Exception {


        JsonConfig c = new JsonConfig();
        c.setIgnoreTransientFields(true);
        generateReportRoot(report, c, destination);

        List<StorySource> sources = report.getStorySources();
        for (StorySource storySource : sources) {
            generateStorySource(storySource, c, destination);
            List<StoryReport> story = storySource.getStoryReports();
            for (StoryReport storyReport : story) {
                generateStory(storySource, storyReport, c, destination);
                List<ScenarioReport> scenarios = storyReport.getScenario_reports();
                for (ScenarioReport scenarioReport : scenarios) {
                    generateScenarioReport(storySource, storyReport, scenarioReport, c, destination);
                }
            }
        }

    }


    protected void generateScenarioReport(StorySource s, StoryReport story, ScenarioReport report, JsonConfig config, File dir) throws Exception {
        JSON json = JSONSerializer.toJSON(report, config);
        File name = new File(dir, createStoryName(s,story) + "_" + report.getScenario().name() + ".json");
        writeOut(json, name);
    }


    protected void generateStory(StorySource s,StoryReport report, JsonConfig config, File dir) throws Exception {
        JSON json = JSONSerializer.toJSON(report, config);
        File name = new File(dir, createStoryName(s, report) + ".json");
        writeOut(json, name);
    }


    private String createStoryName(StorySource s, StoryReport report) {
        return s.getSourceName() + "_" + report.getStoryRef().name() ;
    }


    protected void generateReportRoot(Report r, JsonConfig config, File dir) throws Exception {
        JSON json = JSONSerializer.toJSON(r, config);
        File name = new File(dir, "index.json");
        writeOut(json, name);
    }

    protected void generateStorySource(StorySource s, JsonConfig config, File dir) throws Exception {
        JSON json = JSONSerializer.toJSON(s, config);
        File name = new File(dir, s.getSourceName() + ".json");
        writeOut(json, name);
    }




    private void writeOut(JSON json, File file) throws Exception {
        FileWriter fileWriter =  new FileWriter(file);
        json.write(fileWriter);
        fileWriter.close();
    }




}
