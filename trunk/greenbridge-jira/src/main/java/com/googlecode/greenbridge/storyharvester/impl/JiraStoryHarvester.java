/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import com.googlecode.greenbridge.storyharvester.ScenarioNarrative;
import com.googlecode.greenbridge.storyharvester.StoryHarvester;
import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import com.googlecode.greenbridge.util.JavaLanguageSupport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryan
 */
public class JiraStoryHarvester implements StoryHarvester {

    private String jiraFilterURL;
    private String jiraProjectURL;
    private Integer scenarioTypeId;
    private Integer storyTypeId;
    private SimpleDateFormat jiraUpdatedParser;
    private SimpleDateFormat updateDateToVersion;

    public JiraStoryHarvester() {
        jiraUpdatedParser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z (z)");
        updateDateToVersion = new SimpleDateFormat("yyyyMMddHHmmss");
    }


    @Override
    public List<StoryNarrative> gather() throws Exception {
        URL url = getSourceURL();
        Document d = parseDocument(url.openStream());
        List<Element> nodes = findAllNodes(d);
        List<Element> xmlstories = filterStories(nodes);
        Map<Integer,StoryNarrative> stories = convertToStories(xmlstories);

        List<Element> xmlscenarios = filterScenarios(nodes);
        convertToScenarioAndAddToStory(xmlscenarios, stories);

        return new ArrayList<StoryNarrative>(stories.values());
    }


    protected Document parseDocument(InputStream stream) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(stream);
    }


    protected List<Element> findAllNodes(Document d) {
        XPath xpath = DocumentHelper.createXPath("//item");
        return xpath.selectNodes(d);
    }




    protected void convertToScenarioAndAddToStory(List<Element> scenarios, Map<Integer,StoryNarrative> stories) {
        for (Element scenario : scenarios) {
            Integer parentJiraId = Integer.parseInt(scenario.element("parent").attributeValue("id"));
            ScenarioNarrative narrative = convertToScenario(scenario);
            StoryNarrative story = stories.get(parentJiraId);
            story.getScenarios().add(narrative);
         }
    }


    private ScenarioNarrative convertToScenario(Element xmlScenario) {
        String summary = xmlScenario.elementText("summary");
        String description = xmlScenario.elementText("description");
        ScenarioNarrative narrative = new ScenarioNarrative(parseStoryNarrative(summary, description));
        String jira_key = xmlScenario.elementText("key");
        narrative.setId(safeID(jira_key));
        narrative.setLinkName("See scenario in Jira");
        String link = xmlScenario.elementText("link");
        narrative.setLinkUrl(link);

        String updated = xmlScenario.elementText("updated");
        narrative.setVersion(turnDateToVersion(parseUpdatedDate(updated)));
        return narrative;
    }

    protected Map<Integer,StoryNarrative> convertToStories(List<Element> stories) {
         Map<Integer,StoryNarrative> jiraIdToStory = new HashMap<Integer, StoryNarrative>();
         for (Element story : stories) {
            Integer jiraId = Integer.parseInt(story.element("key").attributeValue("id"));
            StoryNarrative narrative = convertToStory(story);
            jiraIdToStory.put(jiraId, narrative);
         }
         return jiraIdToStory;
    }

    private StoryNarrative convertToStory(Element xmlStory) {
        StoryNarrative narrative = new StoryNarrative();
        String jira_key = xmlStory.elementText("key");
        narrative.setId(safeID(jira_key));
        narrative.setLinkName("See story in Jira");
        String link = xmlStory.elementText("link");
        narrative.setLinkUrl(link);
        String summary = xmlStory.elementText("summary");
        String description = xmlStory.elementText("description");
        narrative.setStoryNarrative(parseStoryNarrative(summary, description));
        String updated = xmlStory.elementText("updated");
        narrative.setVersion(turnDateToVersion(parseUpdatedDate(updated)));
        return narrative;
    }


    protected Date parseUpdatedDate(String updated) {
        try {
            return jiraUpdatedParser.parse(updated);
        } catch (ParseException ex) {
            Logger.getLogger(JiraStoryHarvester.class.getName()).log(Level.SEVERE, null, ex);
            return new Date();
        }
    }

    protected long turnDateToVersion(Date date) {
        return Long.parseLong(updateDateToVersion.format(date));
    }




    protected List<String> parseStoryNarrative(String summary, String description) {
        List<String> narrative = new ArrayList<String>();
        narrative.add(summary);
        narrative.addAll(removeHtml(description));
        return narrative;
    }


    protected List<String> removeHtml(String summary) {
        String htmld =  StringEscapeUtils.unescapeHtml(summary);
        StringReader reader = new StringReader(htmld);
        try {
            return HTMLUtils.extractText(reader);
        } catch (IOException ex) {
            Logger.getLogger(JiraStoryHarvester.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<String>();
        }
    }







    protected List<Element> filterScenarios(List<Element> elements) {
        List<Element> scenarios = new ArrayList<Element>();
        for (Element element : elements) {
            Element parent = element.element("parent");
            if (parent != null) {
                if (scenarioTypeId != null) {
                    Integer type = Integer.valueOf(element.element("type").attribute("id").getText());
                    if (scenarioTypeId.equals(type)) {
                        scenarios.add(element);
                    }
                } else {
                    scenarios.add(element);
                }
            }
        }
        return scenarios;
    }

    protected List<Element> filterStories(List<Element> elements) {
        List<Element> stories = new ArrayList<Element>();
        for (Element element : elements) {
            Element parent = element.element("parent");
            if (parent == null) {
                if (storyTypeId != null) {
                    Integer type = Integer.valueOf(element.element("type").attribute("id").getText());
                    if (storyTypeId.equals(type)) {
                        stories.add(element);
                    }
                } else {
                    stories.add(element);
                }
            }
        }
        return stories;
    }

    protected URL getSourceURL() throws MalformedURLException {
        return new URL(getJiraFilterURL());
    }

    protected String safeID(String title) {
        return JavaLanguageSupport.makeJavaIdentifier(title);
    }

    /**
     * @return the jiraFilterURL
     */
    public String getJiraFilterURL() {
        return jiraFilterURL;
    }

    /**
     * @param jiraFilterURL the jiraFilterURL to set
     */
    public void setJiraFilterURL(String jiraFilterURL) {
        this.jiraFilterURL = jiraFilterURL;
    }

    /**
     * @return the jiraProjectURL
     */
    public String getJiraProjectURL() {
        return jiraProjectURL;
    }

    /**
     * @param jiraProjectURL the jiraProjectURL to set
     */
    public void setJiraProjectURL(String jiraProjectURL) {
        this.jiraProjectURL = jiraProjectURL;
    }

    /**
     * @return the scenarioTypeId
     */
    public Integer getScenarioTypeId() {
        return scenarioTypeId;
    }

    /**
     * @param scenarioTypeId the scenarioTypeId to set
     */
    public void setScenarioTypeId(Integer scenarioTypeId) {
        this.scenarioTypeId = scenarioTypeId;
    }

    /**
     * @return the storyTypeId
     */
    public Integer getStoryTypeId() {
        return storyTypeId;
    }

    /**
     * @param storyTypeId the storyTypeId to set
     */
    public void setStoryTypeId(Integer storyTypeId) {
        this.storyTypeId = storyTypeId;
    }








}
