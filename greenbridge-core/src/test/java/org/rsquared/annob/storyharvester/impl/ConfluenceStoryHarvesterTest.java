/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.codehaus.swizzle.confluence.Page;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.rsquared.annob.ScenarioNarrative;
import org.rsquared.annob.StoryNarrative;

/**
 *
 * @author ryan
 */
public class ConfluenceStoryHarvesterTest {
    private static final String ROOT_SPACE = "temp";
    private static final String ROOT_PAGE = "temp";
    ConfluenceStoryHarvester harvester;

    ConfluenceInterface confluence;
    ConfluencePages pages;

    @Before
    public void setUp() {
        confluence = EasyMock.createMock(ConfluenceInterface.class);
        pages = new ConfluencePages();
        ConfluenceContentParseStrategy contentParser = new DefaultConfluenceContentParseStrategy();
        harvester = new ConfluenceStoryHarvester(confluence, ROOT_SPACE, ROOT_PAGE, contentParser);
    }

    public ConfluenceStoryHarvesterTest() {
    }

    

    @Test
    public void testGather() throws Exception {
        List confluencePages = pages.storyPages();
        List children = new ArrayList();
        
        expect(confluence.getPage((String) anyObject(), (String) anyObject())).andReturn(pages.getSampleScenarioPage());
        expect(confluence.getChildren((String) anyObject())).andReturn(children);
        expect(confluence.getPage((String) anyObject(), (String) anyObject())).andReturn(pages.getSampleScenarioPage());
        replay(confluence);
        
        List<StoryNarrative> stories = harvester.gather();
        assertEquals(1, confluencePages.size());

    }


    /**
     * Test of convertToStory method, of class ConfluenceStoryHarvester.
     */
    @Test
    public void testConvertToStory() throws Exception {
        System.out.println("convertToStory");
        List confluencePages = new ArrayList();
        expect(confluence.getChildren((String) anyObject())).andReturn(confluencePages);
        replay(confluence);
        Page page = pages.getSampleStoryPage();
        StoryNarrative result = harvester.convertToStory(page);
        assertEquals("A1234", result.getId());
        assertEquals(1, result.getVersion());
        List<String> narrative = result.getStoryNarrative();
        assertEquals(2, narrative.size());
    }

    /**
     * Test of safeID method, of class ConfluenceStoryHarvester.
     */
    @Test
    public void testSafeIDWithBadId() throws IOException {
        System.out.println("safeID");
        Page page = pages.getSampleStoryPageWithBadTitle();
        String expResult = "_Bad_Story";
        String result = harvester.safeID(page);
        assertEquals(expResult, result);

    }


    @Test
    public void testSafeID() throws IOException {
        System.out.println("safeID");
        Page page = pages.getSampleStoryPage();
        String expResult = "A1234";
        String result = harvester.safeID(page);
        assertEquals(expResult, result);

    }

    /**
     * Test of convertToScenario method, of class ConfluenceStoryHarvester.
     */
    @Test
    public void testConvertToScenario() throws IOException {
        System.out.println("convertToScenario");
        Page page = pages.getSampleScenarioPage();
        ScenarioNarrative result = harvester.convertToScenario(page);
        assertEquals("ABCD", result.getId());
        assertEquals(2, result.getVersion());
        List<String> narrative = result.getNarrative();
        assertEquals(3, narrative.size());
        assertEquals("Given a account with a balance of (balance)", narrative.get(0));
        assertNotNull(result.getDatatableProperties());
        assertNotNull(result.getDatatable());
    }
}