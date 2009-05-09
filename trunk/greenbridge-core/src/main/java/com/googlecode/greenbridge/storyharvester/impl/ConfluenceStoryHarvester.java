/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.swizzle.confluence.Page;
import org.codehaus.swizzle.confluence.PageSummary;
import com.googlecode.greenbridge.ScenarioNarrative;
import com.googlecode.greenbridge.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.StoryHarvester;

/**
 *
 * @author ryan
 */
public class ConfluenceStoryHarvester implements StoryHarvester {

    private static final String CONFLUENCE_NAME = "Confluence: ";

    private String pageName;
    private String spaceID;
    private ConfluenceInterface confluence;
    private ConfluenceContentParseStrategy contentParser;

    public ConfluenceStoryHarvester(ConfluenceInterface confluence,String spaceID, String pageName, ConfluenceContentParseStrategy contentParser) {
        this.confluence = confluence;
        this.spaceID = spaceID;
        this.pageName = pageName;
        this.contentParser = contentParser;
    }

    @Override
    public List<StoryNarrative> gather() {
        try {
            List<StoryNarrative> stories = new ArrayList<StoryNarrative>();
            Page root = confluence.getPage(spaceID,pageName);
            List pages = confluence.getChildren(root.getId());
            for (Iterator it = pages.iterator(); it.hasNext();) {
                PageSummary pageSummary = (PageSummary) it.next();
                Page page = confluence.getPage(pageSummary.getId());
                StoryNarrative story = convertToStory(page);
                stories.add(story);
            }
            return stories;
        } catch (Exception ex) {
            Logger.getLogger(ConfluenceStoryHarvester.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    protected StoryNarrative convertToStory(Page page) throws Exception {
        StoryNarrative story = new StoryNarrative();
        story.setId(safeID(page));
        story.setVersion(page.getVersion());
        story.setLinkName(CONFLUENCE_NAME + page.getTitle());
        story.setLinkUrl(page.getUrl());
        List<String> narrative = contentParser.getNarrative(page.getContent());
        story.setStoryNarrative(narrative);
        List<ScenarioNarrative> scenarios = harvestScenarios(page, story);
        story.setScenarios(scenarios);
        return story;
    }

    protected String safeID(Page page) {
        String unsafe_id = page.getTitle();
        return JavaLanguageSupport.makeJavaIdentifier(unsafe_id);
    }


    protected List<ScenarioNarrative> harvestScenarios(Page page, StoryNarrative story) throws Exception {
        List <ScenarioNarrative> scenarios = new ArrayList<ScenarioNarrative>();
        List children = confluence.getChildren(page.getId());
        for (Iterator it = children.iterator(); it.hasNext();) {
                PageSummary pageSummary = (PageSummary) it.next();
                Page child = confluence.getPage(pageSummary.getId());
                ScenarioNarrative scenario = convertToScenario(child);
                scenario.setStoryNarrative(story);
                scenarios.add(scenario);
         }
        return scenarios;
    }

    protected ScenarioNarrative convertToScenario(Page page) {
        List<String> narrative = contentParser.getNarrative(page.getContent());
        
        ScenarioNarrative scenario = new ScenarioNarrative(narrative);
        Datatable table  = contentParser.getDatatable(page.getContent());
        if (table != null) {
            scenario.setDatatable(table.getDatatable());
            scenario.setDatatableProperties(table.getDatatableProperties());
        }
        scenario.setId(safeID(page));
        scenario.setVersion(page.getVersion());
        scenario.setLinkName(CONFLUENCE_NAME + page.getTitle());
        scenario.setLinkUrl(page.getUrl());
        return scenario;
    }

}
