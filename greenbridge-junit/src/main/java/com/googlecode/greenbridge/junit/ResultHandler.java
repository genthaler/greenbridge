/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryModule;
import com.googlecode.greenbridge.annotation.StoryRef;
import com.googlecode.greenbridge.junit.report.ModuleReport;
import com.googlecode.greenbridge.junit.report.ScenarioReport;
import com.googlecode.greenbridge.junit.report.StoryReport;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryan
 */
public class ResultHandler {
    public void handleResults(StoryResults results) {
        
        File story_root = setupStoryOutputDir();
        //File scenario_root = setupScenarioOutputDir();
        String name = results.getTestDescription().getDisplayName();
        //writeTextOutput(story_root, results, name);
        writeStoryXml(story_root, results, name);
        //writeScenarioOutput(scenario_root, results);
        //writeScenarioXml(story_root, results,name);
        writeStoryModule(story_root, results);
    }

    protected List<File> writeScenarioOutput(File root, StoryResults results) {
        JUnitXMLOutput output = new JUnitXMLOutput();
        List<File> files = new ArrayList<File>();
        Map<ScenarioRef, List<ScenarioResult>> scenarioResults = results.getScenarioResults();
        for (Iterator<ScenarioRef> it = scenarioResults.keySet().iterator(); it.hasNext();) {
            ScenarioRef scenarioRef = it.next();
            List<ScenarioResult> ss = scenarioResults.get(scenarioRef);
            for (Iterator<ScenarioResult> it2 = ss.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();
                File result_file = output.write(scenarioResult, root);
                files.add(result_file);
            }
         }
        return files;
    }

    private  List<File> writeScenarioXml(File root, StoryResults results, String name) {
       StoryResultsXmlOutput output = new StoryResultsXmlOutput();
       System.out.println("Wrinting the scenarios");
       List<File> files = new ArrayList<File>();
        Map<ScenarioRef, List<ScenarioResult>> scenarioResults = results.getScenarioResults();
        for (Iterator<ScenarioRef> it = scenarioResults.keySet().iterator(); it.hasNext();) {
            ScenarioRef scenarioRef = it.next();
            List<ScenarioResult> ss = scenarioResults.get(scenarioRef);
            for (Iterator<ScenarioResult> it2 = ss.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();
                File result_file = output.write(scenarioResult, root);
                files.add(result_file);
            }
         }
        return files;
    }

    private void writeStoryXml(File root, StoryResults results, String name) {
       StoryResultsXmlOutput output = new StoryResultsXmlOutput();
       File f = new File(root, name + output.getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            output.write(results, stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void writeXmlOutput(File root, StoryResults results, String name) {
       JUnitXMLOutput output = new JUnitXMLOutput();
       File f = new File(root, name + output.getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            output.write(results, stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeTextOutput(File root, StoryResults results, String name) {
       TxtOutput output = new TxtOutput();
       File f = new File(root, name + output.getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            output.write(results, stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    protected File setupStoryOutputDir() {
        File f =  new File("target/greenbridge/stories");
        f.mkdirs();
        return f;
    }

    protected File setupScenarioOutputDir() {
        File f =  new File("target/greenbridge/scenarios");
        f.mkdirs();
        return f;
    }

    private void writeStoryModule(File story_root, StoryResults results)  {
        try {
            StoryRef story = results.getStory();
            Class<? extends StoryModule> smc = story.getStoryModule();
            StoryModule module = smc.newInstance();
            File out = new File(story_root, smc.getName() + ".xml.module");
            if (out.exists()) {
                System.out.println("Story module output exists. No need to rewrite");
            } else {
                //out.createNewFile();
                ModuleReport moduleReport = new ModuleReport();
                moduleReport.setName(module.getName());
                moduleReport.setVersion(module.getVersion());

                for (StoryRef m_story : module.getStories().keySet()) {
                    StoryReport storyReport = new StoryReport();
                    storyReport.setId(m_story.name());
                    String desc = StringUtils.join(m_story.narrative(), "<br/>");
                    storyReport.setDescription(desc);
                    storyReport.setWikiLink(m_story.linkUrl());
                    moduleReport.getStoryReports().add(storyReport);
                    for(ScenarioRef m_scenario : module.getStories().get(m_story)) {
                        ScenarioReport scenarioReport = new ScenarioReport();
                        scenarioReport.setId(m_scenario.name());
                        String scenarioDesc = StringUtils.join(m_scenario.narrative(), "<br/>");
                        scenarioReport.setDescription(scenarioDesc);
                        scenarioReport.setWikiLink(m_scenario.linkUrl());
                        storyReport.getScenarioReports().add(scenarioReport);
                    }
                }
                XStream xstream = new XStream();
                xstream.toXML(moduleReport, new FileWriter(out));


            }


        } catch (Exception ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
