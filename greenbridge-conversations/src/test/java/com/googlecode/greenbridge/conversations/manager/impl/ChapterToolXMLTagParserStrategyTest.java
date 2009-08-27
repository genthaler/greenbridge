/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.manager.*;
import com.googlecode.greenbridge.conversations.manager.impl.ChapterToolXMLTagParserStrategy;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class ChapterToolXMLTagParserStrategyTest {

    public ChapterToolXMLTagParserStrategyTest() {
    }


    @Test 
    public void testParseXMLFile() throws Exception {
        InputStream xml = getClass().getResourceAsStream("/appleChapter.xml");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        ChapterToolXMLTagParserStrategy instance = new ChapterToolXMLTagParserStrategy(mockTagManager);
        Document d = instance.parseDocument(xml);
        List<MediaTag> result = instance.getTags(d, null, null, null);
        assertEquals(2, result.size());
        Iterator i = result.iterator();
        MediaTag tag = (MediaTag) i.next();
        assertEquals("tag1", tag.getTag().getTagName());

        MediaTag tag2 = (MediaTag) i.next();
        assertEquals("tag2", tag2.getTag().getTagName());
    }

    /**
     * Test of parseChapter method, of class ChapterToolXMLTagParserStrategy.
     */
    @Test
    public void testParseChapter() {
        System.out.println("parseChapter");
        Element chapter = new DefaultElement("chapter");
        chapter.addAttribute("starttime", "2:20");

        Element title = new DefaultElement("title");
        title.setText("tag1");

        chapter.add(title);

        MockTagManager mockTagManager = new MockTagManager();
        ChapterToolXMLTagParserStrategy instance = new ChapterToolXMLTagParserStrategy(mockTagManager);

        MediaTag result = instance.parseChapter(chapter, null);

        assertEquals("tag1", result.getTag().getTagName());
        assertEquals(140000L, (long)result.getStartTime());
        assertEquals(140000L, (long)result.getEndTime());
    }
    /**
     * Test of parseChapter method, of class ChapterToolXMLTagParserStrategy.
     */
    @Test
    public void testParseChapterWithEndTime() {
        System.out.println("parseChapter");
        Element chapter = new DefaultElement("chapter");
        chapter.addAttribute("starttime", "2:20");
        chapter.addAttribute("endtime", "2:21");
        Element title = new DefaultElement("title");
        title.setText("tag1");

        chapter.add(title);

        MockTagManager mockTagManager = new MockTagManager();
        ChapterToolXMLTagParserStrategy instance = new ChapterToolXMLTagParserStrategy(mockTagManager);

        MediaTag result = instance.parseChapter(chapter, null);

        assertEquals("tag1", result.getTag().getTagName());
        assertEquals(140000L, (long)result.getStartTime());
        assertEquals(141000L, (long)result.getEndTime());
    }
}