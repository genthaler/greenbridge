/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class DefaultFreemindNodeToMediaTagTest {


    List<Element> nodes;

    List<Element> badNodes;


    public DefaultFreemindNodeToMediaTagTest() throws Exception{
        InputStream xml = getClass().getResourceAsStream("/tag_examples.mm");
        assertNotNull(xml);
        MockTagManager mockTagManager = new MockTagManager();
        FreemindXmlTagParserStrategy instance = new FreemindXmlTagParserStrategy(mockTagManager, null);
        Document doc = instance.parseDocument(xml);
        XPath xpath = DocumentHelper.createXPath("/map/node/node");
        nodes = xpath.selectNodes(doc);


        InputStream xml2 = getClass().getResourceAsStream("/bad_tags.mm");
        assertNotNull(xml2);
        Document doc2 = instance.parseDocument(xml2);
        badNodes = xpath.selectNodes(doc2);
    }



    @Test
    public void testThatTheStartOffsetIsCorrect() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);

        long meeting_start = 1248757600609l;
        long tag_start = 1248757670609l;
        long expected = (tag_start - meeting_start) / 1000;
        instance.setStartTime(mediaTag, nodes.get(0), new Date(meeting_start));
        assertEquals(expected, (long)mediaTag.getStartTime());
    }


    @Test
    public void testThatTheEndOffsetIsCorrect() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);

        long meeting_start = 1248757600609l;
        long tag_start = 1248757670609l;
        long expected = (tag_start - meeting_start + instance.getEndOffsetConstant()) / 1000;
        instance.setEndTime(mediaTag, nodes.get(0), new Date(meeting_start));
        assertEquals(expected, (long)mediaTag.getEndTime());
    }

    @Test
    public void testThatTheEndOffsetIsCorrectForATree() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);

        long meeting_start = 1248757600609l;
        long child_tag_start = 1248759999993l;
        long expected = (child_tag_start - meeting_start + instance.getEndOffsetConstant()) / 1000;
        instance.setEndTime(mediaTag, nodes.get(2), new Date(meeting_start));
        assertEquals(expected, (long)mediaTag.getEndTime());
    }



    /**
     * Test of setName method, of class DefaultFreemindNodeToMediaTag.
     */
    @Test
    public void testThatANodeWithATagNameAttributeGetsThatTag() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setName(mediaTag, nodes.get(0), null);
        assertEquals("Alpha-1", mediaTag.getTag().getTagName());
    }

    @Test(expected=AssertionError.class)
    public void testThatANodeWithABadTagNameAttributeIsRejected() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setName(mediaTag, badNodes.get(2), null);
    }


    /**
     * Test of setName method, of class DefaultFreemindNodeToMediaTag.
     */
    @Test
    public void testThatANodeWithTextThatIsAWordGetsThatTag() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setName(mediaTag, nodes.get(2), null);
        assertEquals("FollowUp", mediaTag.getTag().getTagName());
    }

    /**
     * Test of setName method, of class DefaultFreemindNodeToMediaTag.
     */
    @Test(expected=AssertionError.class)
    public void testThatANodeWithTextThatIsASentanceIsRejected() {
        MediaTag mediaTag = new MediaTag();
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setName(mediaTag, badNodes.get(0), null);

    }

    @Test
    public void testThatANodeWithATagGetsTheTextAsTheShortDescription() {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("Alpha-1");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setShortDiscription(mediaTag, nodes.get(0));
        MediaTagExtraInfo extraInfo = mediaTag.getMediaTagExtraInfos().get(0);
        assertEquals("shortDescription", extraInfo.getProp());
        assertEquals("Description, with tag", extraInfo.getEntry());
    }

    @Test
    public void testThatANodeWithHtmlTextGetsTheTextAsTheLongDescription() throws IOException {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("FollowUp");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setLongDescription(mediaTag, nodes.get(5));
        MediaTagExtraInfo extraInfo = mediaTag.getMediaTagExtraInfos().get(0);
        assertEquals("longDescription", extraInfo.getProp());
        assertTrue( extraInfo.getEntry().startsWith("<html>"));
    }

    @Test
    public void testThatANodeWithNoHtmlTextGetsNoLongDescription() throws IOException {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("FollowUp");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setLongDescription(mediaTag, nodes.get(0));
        assertEquals(0, mediaTag.getMediaTagExtraInfos().size());
    }

    @Test
    public void testThatANodeWithAnIconGetsAIcon() {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("FollowUp");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setIcons(mediaTag, nodes.get(4));
        MediaTagExtraInfo extraInfo = mediaTag.getMediaTagExtraInfos().get(0);
        assertEquals("icon", extraInfo.getProp());
        assertEquals("flag-pink", extraInfo.getEntry());
    }

    @Test
    public void testThatANodeWithNoIconGetsNoIcon() {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("FollowUp");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setIcons(mediaTag, nodes.get(0));
        assertEquals(0, mediaTag.getMediaTagExtraInfos().size());
    }

    @Test
    public void testThatAdditionalAttributesGetAdded() {
        MediaTag mediaTag = new MediaTag();
        Tag t = new Tag();
        t.setTagName("FollowUp");
        mediaTag.setTag(t);
        MockTagManager mockTagManager = new MockTagManager();
        DefaultFreemindNodeToMediaTag instance = new DefaultFreemindNodeToMediaTag(mockTagManager);
        instance.setAttributes(mediaTag, nodes.get(1));
        MediaTagExtraInfo extraInfo = mediaTag.getMediaTagExtraInfos().get(0);
        assertEquals("Attendee", extraInfo.getProp());
        assertEquals("Ryan", extraInfo.getEntry());
        
    }



}