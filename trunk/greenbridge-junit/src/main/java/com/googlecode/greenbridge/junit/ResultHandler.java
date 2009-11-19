/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan
 */
public class ResultHandler {
    public void handleResults(StoryResults results) {
        
        File story_root = setupStoryOutputDir();
        File scenario_root = setupScenarioOutputDir();
        String name = results.getTestDescription().getDisplayName();
        writeTextOutput(story_root, results, name);
        writeXmlOutput(story_root, results, name);
        writeScenarioOutput(scenario_root, results);
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

}
