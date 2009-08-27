/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import java.io.InputStream;
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
public class FreemindXmlTagParserStrategyTest {

    public FreemindXmlTagParserStrategyTest() {
    }


    /**
     * Test of parseStream method, of class FreemindXmlTagParserStrategy.
     */
    @Test
    public void testParseStream() throws Exception {
        System.out.println("parseStream");
        InputStream xml = getClass().getResourceAsStream("/freemind.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, null);
        Document result = instance.parseDocument(xml);
        assertNotNull(result);

    }


    @Test
    public void testParseAttendees() throws Exception {
        InputStream xml = getClass().getResourceAsStream("/freemind.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, null);
        Document doc = instance.parseDocument(xml);
        List<String> attendees = instance.getAttendeeList(doc);
        assertEquals(4, attendees.size());
        assertTrue(attendees.contains("Ryan"));
    }


    @Test
    public void testFindElementsWithCreatedBetweenMeetingTime() throws Exception {
        InputStream xml = getClass().getResourceAsStream("/freemind.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, null);
        Document doc = instance.parseDocument(xml);
        Date start = new Date(1248671705531L);
        Date end   = new Date(1248671771640L);
        List<Element> nodes = instance.findElementsWithCreatedBetweenMeetingTime(start, end, doc);
        assertEquals(3, nodes.size());
    }

    @Test
    public void testGetMeetingStart() throws Exception {
        InputStream xml = getClass().getResourceAsStream("/freemind.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, null);
        Document doc = instance.parseDocument(xml);
        Date date = instance.getMeetingStart(doc);
        String shouldBe = "14:57:13 08/57/2009";
        Date actual = instance.formatter.parse(shouldBe);
        assertEquals(actual, date);

    }

    @Test
    public void testAMeetingIsRead() throws Exception {
        InputStream xml = getClass().getResourceAsStream("/TestMeeting.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag converter = new DefaultFreemindNodeToMediaTag(mockTagManager);
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, converter);
        Document doc = instance.parseDocument(xml);
        String name = instance.getMeetingName(doc);
        assertEquals("Test Meeting", name);
        
        Date startDate = instance.formatter.parse("13:44:59 09/08/2009");
        assertEquals(startDate, instance.getMeetingStart(doc));
        
        Date endDate = instance.formatter.parse("13:45:45 09/08/2009");
        assertEquals(endDate, instance.getMeetingEnd(doc));
        
        List<Element> elements = instance.findElementsWithCreatedBetweenMeetingTime(startDate, endDate, doc);
        assertEquals(2, elements.size());

        List<MediaTag> tags = instance.getTags(doc, null, 0, 0);
        assertEquals(2, tags.size());
    }


}