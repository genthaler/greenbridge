/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import com.googlecode.greenbridge.storyharvester.ScenarioNarrative;
import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class TiddlyWikiStoryHarvesterTest {

    public TiddlyWikiStoryHarvesterTest() {
    }


    @Test
    public void testGather() throws Exception {
        TiddlyWikiStoryHarvester harvester = new TiddlyWikiStoryHarvester();
        File f = new File("src/test/resources/mptw.html");

        harvester.setTiddlyWikiFile(f.toURI().toURL().toString());
        List<StoryNarrative> stories = harvester.gather();
        assertEquals(3, stories.size());

        StoryNarrative sampleStory1 = stories.get(0);
        assertEquals("SampleStory1", sampleStory1.getId());
        assertEquals(2, sampleStory1.getVersion());
        assertEquals(2, sampleStory1.getScenarios().size());
        List<String> nar = sampleStory1.getStoryNarrative();
        assertEquals("As A User", nar.get(0));


        StoryNarrative sampleStory3 = stories.get(2);
        assertEquals("SampleStory3", sampleStory3.getId());
        assertEquals(1, sampleStory3.getScenarios().size());
        List<String> nar3 = sampleStory3.getStoryNarrative();
        assertEquals(3, nar3.size());
        assertEquals("so that I can drive tests with data", nar3.get(2));

        ScenarioNarrative scenario = sampleStory3.getScenarios().get(0);
        assertEquals(sampleStory3, scenario.getStoryNarrative());
        assertEquals(3,scenario.getNarrative().size());
        List<String> columns = scenario.getDatatableProperties();
        assertEquals(3, columns.size());

        List<Map<String,String>> data_grid = scenario.getDatatable();
        assertEquals(2, data_grid.size());

        Map<String,String>   row1 = data_grid.get(0);
        assertEquals("0.00", row1.get("PrevBalance"));
        assertEquals("100",  row1.get("Deposit"));
        assertEquals("100",  row1.get("NewBalance"));

        Map<String,String>   row2 = data_grid.get(1);
        assertEquals("100", row2.get("PrevBalance"));
        assertEquals("5",  row2.get("Deposit"));
        assertEquals("105",  row2.get("NewBalance"));



        


    }


    @Test
    public void testGetDivs() throws Exception {
        File f = new File("src/test/resources/mptw.html");

        URL url = f.toURI().toURL();
        TiddlyWikiStoryHarvester harvester = new TiddlyWikiStoryHarvester();
        List<Element> elements = harvester.getDivs(url);
        assertEquals(52, elements.size());
    }

    @Test
    public void testConvertToStoryHolderMap() throws Exception {
        File f = new File("src/test/resources/mptw.html");
        URL url = f.toURI().toURL();
        TiddlyWikiStoryHarvester harvester = new TiddlyWikiStoryHarvester();
        List<Element> elements = harvester.getDivs(url);
        Map<String,StoryHolder> results = harvester.convertToStoryHolderMap(elements);
        assertTrue(results.containsKey("SampleStory1"));


    }



}