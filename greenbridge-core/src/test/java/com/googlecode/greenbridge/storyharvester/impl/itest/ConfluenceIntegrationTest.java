/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl.itest;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import org.codehaus.swizzle.confluence.Confluence;
import org.codehaus.swizzle.confluence.Page;
import org.codehaus.swizzle.confluence.PageSummary;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import com.googlecode.greenbridge.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.impl.ConfluenceAdaptor;
import com.googlecode.greenbridge.storyharvester.impl.ConfluenceContentParseStrategy;
import com.googlecode.greenbridge.storyharvester.impl.ConfluenceStoryHarvester;
import com.googlecode.greenbridge.storyharvester.impl.DefaultConfluenceContentParseStrategy;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class ConfluenceIntegrationTest {

    public ConfluenceIntegrationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Ignore
    @Test
    public void shouldGetStoriesFromConfluence() throws Exception {
        Confluence c = new Confluence("http://fg5664/rpc/xmlrpc");
        c.login("confluence_application", "password");
        ConfluenceAdaptor cAdaptor = new ConfluenceAdaptor(c);

        ConfluenceContentParseStrategy strategy = new DefaultConfluenceContentParseStrategy();

        ConfluenceStoryHarvester harvester = new ConfluenceStoryHarvester(cAdaptor, "~ryan_ramage", "My New App", strategy);

        List<StoryNarrative> stories = harvester.gather();
        assertEquals(1, stories.size());
        StoryNarrative first = stories.get(0);
        assertEquals("VOLFOUND_7_17", first.getId());
    }


    @Ignore
    @Test
    public void hello() {
        try {
        Confluence c = new Confluence("http://fg5664/rpc/xmlrpc");
        c.login("confluence_application", "password");
        Page p = c.getPage("~ryan_ramage", "My New App");

        List stories = c.getChildren(p.getId());
            for (Iterator it = stories.iterator(); it.hasNext();) {
                PageSummary storySummary = (PageSummary)it.next();
                Page story = c.getPage(storySummary.getId());

                System.out.println("Story:\t" + story.getTitle());
                System.out.println("URL:\t" + story.getUrl());
                System.out.println("Narrative:\n" + story.getContent());

                
            System.out.println("------- Scenarios --------------");
                List scenarios = c.getChildren(story.getId());
                for (Iterator it2 = scenarios.iterator(); it2.hasNext();) {
                    PageSummary scenarioSummary = (PageSummary)it2.next();
                    Page scenario = c.getPage(scenarioSummary.getId());
                    System.out.println("Scenario:\t" + scenario.getTitle());
                    System.out.println("URL:\t" + scenario.getUrl());
                    System.out.println("Narrative:\n" + scenario.getContent());
                }
            }






        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}