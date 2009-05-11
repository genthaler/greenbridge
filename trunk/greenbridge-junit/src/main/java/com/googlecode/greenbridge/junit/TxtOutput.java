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
import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;

/**
 *
 * @author ryan
 */
public class TxtOutput implements Output {

    private static String SECTION    = "#################################################";
    private static String SUBSECTION = "-------------------------------------------------";

    @Override
    public String getExtension() {
        return ".txt";
    }

    @Override
    public void write(StoryResults results, PrintWriter stream) {
        writeStoryInfo(results.getStory(), stream);
        writeScenarioResults(results.getScenarioResults(), stream);
        stream.flush();
    }

    private void writeScenarioResults(Map<ScenarioRef, List<ScenarioResult>> scenarioResults, PrintWriter stream) {
        for (Iterator<ScenarioRef> it = scenarioResults.keySet().iterator(); it.hasNext();) {
            ScenarioRef scenarioRef = it.next();
            stream.println(SUBSECTION);
            stream.println("Scenario Reference:\t " + scenarioRef.name());
            for (int i = 0; i < scenarioRef.narrative().length; i++) {
                String string = scenarioRef.narrative()[i];
                stream.println("\t" + string);

            }
            stream.println();
            List<ScenarioResult> ss = scenarioResults.get(scenarioRef);
            for (Iterator<ScenarioResult> it2 = ss.iterator(); it2.hasNext();) {
                ScenarioResult scenarioResult = it2.next();
                stream.println("\t" + scenarioResult.getState().name()  + " test " + scenarioResult.getDescription().getDisplayName());
                stream.println("\ttime " + scenarioResult.getDuration());
            }
            stream.println();
            
        }
    }

    private void writeStoryInfo(StoryRef story, PrintWriter stream) {

        stream.println(SECTION);
        stream.println("Story Reference:\t"  + story.name());
        stream.println("");
        for (int i = 0; i < story.narrative().length; i++) {
            String string = story.narrative()[i];
            stream.println(string);
        }
        stream.println("");
        
    }

    @Override
    public void write(ScenarioResult result, File basedirectory) {
        // ignore this for now.
    }
}
