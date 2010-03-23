/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;


import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class JiraStoryHarvesterTest {

    String testSearchRequest = "/SearchRequest.xml";
    URL testSearchRequestURL;

    public JiraStoryHarvesterTest() {
        testSearchRequestURL = this.getClass().getResource(testSearchRequest);
    }


    @Test
    public void testGather() throws URISyntaxException, Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        harvester.setJiraFilterURL(testSearchRequestURL.toURI().toString());
        System.out.println("URL: " + harvester.getJiraFilterURL());
        List<StoryNarrative> list = harvester.gather();
    }


    @Test
    public void shouldOnlyReturnChildJiraWithTypeOfSubTask() throws Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        harvester.setScenarioTypeId(5);
        Document d = harvester.parseDocument(getClass().getResourceAsStream(testSearchRequest));
        List<Element> nodes = harvester.findAllNodes(d);
        nodes = harvester.filterScenarios(nodes);
        assertEquals(1, nodes.size());
    }
    @Test
    public void shouldOnlyReturnChildJira() throws Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        Document d = harvester.parseDocument(getClass().getResourceAsStream(testSearchRequest));
        List<Element> nodes = harvester.findAllNodes(d);
        System.out.println("Nodes: " + nodes.size());
        nodes = harvester.filterScenarios(nodes);
        System.out.println("Nodes: " + nodes.size());
        assertEquals(38, nodes.size());
    }

    @Test
    public void shouldOnlyNullParents() throws Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        Document d = harvester.parseDocument(getClass().getResourceAsStream(testSearchRequest));
        List<Element> nodes = harvester.findAllNodes(d);
        nodes = harvester.filterStories(nodes);
        assertEquals(22, nodes.size());
    }
    @Test
    public void shouldOnlyNullParentsWithTypeOfTask() throws Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        harvester.setStoryTypeId(9);
        Document d = harvester.parseDocument(getClass().getResourceAsStream(testSearchRequest));
        List<Element> nodes = harvester.findAllNodes(d);
        nodes = harvester.filterStories(nodes);
        System.out.println("Nodes: " + nodes.size());
        assertEquals(8, nodes.size());
    }

    @Test
    public void shouldPrintStories() throws Exception {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        //harvester.setStoryTypeId(9);
        Document d = harvester.parseDocument(getClass().getResourceAsStream(testSearchRequest));
        List<Element> nodes = harvester.findAllNodes(d);
        nodes = harvester.filterStories(nodes);
        harvester.convertToStories(nodes);

    }

    @Test
    public void shouldRemoveHtml() {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        String escaped = "Given a table&lt;br/&gt;\nWith Stuff&lt;br/&gt;\nI want to do stuff";
        List<String> results = harvester.removeHtml(escaped);
        assertEquals("Given a table", results.get(0));
        assertEquals("With Stuff", results.get(1));
        assertEquals("I want to do stuff", results.get(2));
    }


    @Test
    public void shouldParseJiraDate() throws ParseException {
        JiraStoryHarvester harvester = new JiraStoryHarvester();
        String date = "Mon, 22 Mar 2010 14:38:11 +1200 (NZST)";
        Date d = harvester.parseUpdatedDate(date);
        System.out.println("date: " + d);
        long version = harvester.turnDateToVersion(d);
        assertEquals(20100322153811l, version);
    }



}