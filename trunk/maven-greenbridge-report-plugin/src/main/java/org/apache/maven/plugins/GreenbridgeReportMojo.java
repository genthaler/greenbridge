package org.apache.maven.plugins;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.googlecode.greenbridge.ReportFileWriter;
import com.googlecode.greenbridge.junit.report.ModuleReport;
import com.googlecode.greenbridge.junit.report.ModuleReport.STATE;
import com.googlecode.greenbridge.junit.report.ScenarioReport;
import com.googlecode.greenbridge.junit.report.ScenarioResult;
import com.googlecode.greenbridge.junit.report.StoryReport;
import com.ibm.media.ReplaceURLEvent;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FilterReader;
import java.util.HashMap;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;


import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.maven.plugin.logging.Log;


/**
 * Goal which touches a timestamp file.
 *
 * @goal report
 * @phase test
 */
public class GreenbridgeReportMojo
    extends AbstractMojo
{


    private Log log;


    /**
     * @parameter expression="${project.artifactId}"
     */
    private String projectArtifactId;

    /**
     * @parameter expression="${project.groupId}"
     */
    private String projectGroupId;

     /**
     * @parameter expression="${project.version}"
     */
    private String projectVersion;


    /**
     * @parameter expression="${project.basedir}"
     */
    private File basedir;

    /**
     * Whether to link the XRef if found.
     * @parameter
     * @default-value="true"
     */
    private boolean linkXRef;


    /**
     * Location of the Xrefs to link.
     * @parameter
     * @default-value="${project.reporting.outputDirectory}/xref-test"
     */
    private String xrefLocation;


    // need someway to disable this if offline?
    public void execute()
        throws MojoExecutionException
    {
    
        if (xrefLocation == null) {
            //xrefLocation = new File(basedir, "target/site/xref-test");
        }

        log = getLog();
        try {
            log.info("Generating greenbridge report");

            File file = new File(basedir, "target/greenbridge/stories");
            if (!file.exists() || !file.isDirectory()) {
                log.info("No greenbridge information to process.");
                return;
            }
            
            File reportDir = new File(basedir, "target/greenbridge/report");
            reportDir.mkdirs();
            ReportFileWriter writer = new ReportFileWriter("/", reportDir);

            ModuleReport report = findModuleReport(file);
            Map<String,ScenarioReport> scenarioIndex = createScenarioIndex(report);
            populateModuleReport(file, scenarioIndex);
            rollupCounts(report);
            writer.writeReport(report);
            
        } catch (Exception ex) {
            throw new MojoExecutionException("", ex);
        }
    }





    private ModuleReport findModuleReport(File dir) throws FileNotFoundException {
        File[] children = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml.module")) return true;
                else return false;
            }
        });
        if (children.length == 1) {
            File moduleFile = children[0];
            XStream xstream = new XStream();
            return (ModuleReport)xstream.fromXML(new FileReader(moduleFile));
        }
        throw new RuntimeException("More than one module is currently not supported");
    }

    private Map<String,ScenarioReport> createScenarioIndex(ModuleReport report) {
        Map<String,ScenarioReport> index = new HashMap<String, ScenarioReport>();
        for (StoryReport storyReport : report.getStoryReports()) {
            for (ScenarioReport scenarioReport : storyReport.getScenarioReports()) {
                index.put(scenarioReport.getId(), scenarioReport);
            }
        }
        return index;
    }


    private void populateModuleReport(File dir, Map<String,ScenarioReport> scenarioIndex) throws FileNotFoundException {
        XStream xstream = new XStream();
        File[] children = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".result.xml")) return true;
                else return false;
            }
        });
        for (File storyReportFile : children) {
            StoryReport storyReport = (StoryReport) xstream.fromXML(new FileReader(storyReportFile));
            for (ScenarioReport scenarioReport : storyReport.getScenarioReports()) {
                String scenarioId = scenarioReport.getId();
                for (ScenarioResult result : scenarioReport.getResults()) {
                    result.setClazzHref(generateHrefForClass(result.getClazz()));
                    scenarioIndex.get(scenarioId).getResults().add(result);
                }
            }
        }
    }


    private void rollupCounts(ModuleReport report) {

        for (StoryReport storyReport : report.getStoryReports()) {
            for (ScenarioReport scenarioReport : storyReport.getScenarioReports()) {
                for (ScenarioResult result : scenarioReport.getResults()) {
                    if (STATE.passed.equals(result.getState())){
                        scenarioReport.setTotal_passing(scenarioReport.getTotal_passing() + 1);
                        storyReport.setTotal_passing(storyReport.getTotal_passing() + 1);
                        report.setTotal_passing(report.getTotal_passing() + 1);
                    }
                    if (STATE.failed.equals(result.getState())){
                        scenarioReport.setTotal_failing(scenarioReport.getTotal_failing() + 1);
                        storyReport.setTotal_failing(storyReport.getTotal_failing() + 1);
                        report.setTotal_failing(report.getTotal_failing() + 1);
                    }
                    if (STATE.pending.equals(result.getState())){
                        scenarioReport.setTotal_pending(scenarioReport.getTotal_pending() + 1);
                        storyReport.setTotal_pending(storyReport.getTotal_pending() + 1);
                        report.setTotal_pending(report.getTotal_pending() + 1);
                    }
                }
                if (scenarioReport.getResults().size() == 0) {
                    scenarioReport.setScenarioState(STATE.pending);
                    storyReport.setTotal_pending(storyReport.getTotal_pending() + 1);
                    report.setTotal_pending(report.getTotal_pending() + 1);
                }
                else if (scenarioReport.getTotal_failing() > 0) {
                    scenarioReport.setScenarioState(STATE.failed);
                }
                else if (scenarioReport.getTotal_pending() > 0) {
                    scenarioReport.setScenarioState(STATE.pending);
                }
                else scenarioReport.setScenarioState(STATE.passed);
            }
            if (storyReport.getScenarioReports().size() == 0) {
                storyReport.setState(STATE.pending);
                report.setTotal_pending(report.getTotal_pending() + 1);
            }
            else if (storyReport.getTotal_failing() > 0) {
                storyReport.setState(STATE.failed);
            }
            else if (storyReport.getTotal_pending() > 0) {
                storyReport.setState(STATE.pending);
            }
            else storyReport.setState(STATE.passed);
        }
        if (report.getStoryReports().size() == 0) {
            report.setState(STATE.pending);
        }
        else if (report.getTotal_failing() > 0) {
            report.setState(STATE.failed);
        }
        else if (report.getTotal_pending() > 0) {
            report.setState(STATE.pending);
        }
        else report.setState(STATE.passed);
    }




    private ModuleReport generateSampleReport() {
        ModuleReport report = new ModuleReport();
        report.setName("Test");
        report.setState(ModuleReport.STATE.passed);
        report.setTotal_passing(3);
        report.setTotal_pending(0);
        report.setTotal_failing(0);
        report.setVersion("1.0");

        StoryReport story = new StoryReport();
        report.getStoryReports().add(story);
        story.setId("SampleStory1");
        story.setDescription("As a<br/> I want<br/> so that</br>");
        story.setState(ModuleReport.STATE.passed);  
        story.setTotal_passing(3);

        ScenarioReport scenario = new ScenarioReport();
        scenario.setId("TestTable");
        scenario.setDescription("Given<br/>When<br/>Then<br/>");
        scenario.setScenarioState(ModuleReport.STATE.passed);
        story.getScenarioReports().add(scenario);

        ScenarioResult result = new ScenarioResult();

        result.setClazz("co.nz.vero.renew.ParameterizedTableTest");
        result.setClazzHref(generateHrefForClass(result.getClazz()));
        result.setMethod("ensureAdeposit[0]");
        result.setState(ModuleReport.STATE.passed);
        scenario.getResults().add(result);


        return report;
    }

    private String generateHrefForClass(String clazz) {
        String url = clazz.replaceAll("\\.", "/");
        url = "../../site/xref-test/" + url + ".html";
        return url;
    }

}
