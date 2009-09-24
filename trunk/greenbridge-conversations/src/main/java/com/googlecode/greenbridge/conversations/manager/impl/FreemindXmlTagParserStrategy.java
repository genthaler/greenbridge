/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.manager.FullConversationTagParsingStratgey;
import com.googlecode.greenbridge.conversations.manager.TagManager;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 *
 * @author ryan
 */
public class FreemindXmlTagParserStrategy implements FullConversationTagParsingStratgey {

    private TagManager tagManager;
    private FreemindNodeToMediaTagStrategy converter;
    protected SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    public FreemindXmlTagParserStrategy(TagManager tagManager, FreemindNodeToMediaTagStrategy converter) {
        this.tagManager = tagManager;
        this.converter = converter;
    }


    @Override
    public List<MediaTag> getTags(Document doc, String template_id,Integer tagStartOffset, Integer tagDuration) throws Exception {
        List<MediaTag> mediaTags = new ArrayList<MediaTag>();
        Date meetingStart = getMeetingStart(doc);
        List<Element> elements = findElementsWithCreatedBetweenMeetingTime(meetingStart, getMeetingEnd(doc), doc);
        for (Element element : elements) {
            MediaTag tag = createMediaTagFromNode(element, meetingStart, template_id,tagStartOffset,tagDuration);
            mediaTags.add(tag);
        }
        return mediaTags;
    }


    @Override
    public Document parseDocument(InputStream stream) throws Exception {
        SAXReader reader = new SAXReader();
        return reader.read(stream);
    }


    List<Element> findElementsWithCreatedBetweenMeetingTime(Date start, Date end, Document doc) {
        List<Element> finds = new ArrayList<Element>();
        XPath xpath = DocumentHelper.createXPath("//node");
        List<Element> nodes = xpath.selectNodes(doc);
        for (Element node : nodes) {
            String created   = node.attributeValue("CREATED");
            Date createdDate = getDateFromLongText(created);
            if (createdDate.after(start) && createdDate.before(end)) {
                finds.add(node);
            }
        }
        return finds;
    }

    /**
     * Iterate through all the strategys to convert. The first one that works is the onw we use.
     * @param node
     * @return
     */
    protected MediaTag createMediaTagFromNode(Element node, Date meetingStart, String template_id, Integer tagStartOffset, Integer tagDuration) {
            MediaTag mediaTag = converter.createMediaTagFromNode(node, meetingStart,template_id, tagStartOffset, tagDuration);
            return mediaTag;
    }




    @Override
    public List<String> getAttendeeList(Document doc) {
        List<String> attendees = new ArrayList<String>();
        XPath xpath = DocumentHelper.createXPath("//node[@TEXT='Attendees']/attribute[@NAME='Attendee']");
        List<Element> people = xpath.selectNodes(doc);
        for (Element person : people) {
            String name = person.attributeValue("VALUE");
            attendees.add(name);
        }
        return attendees;
    }

    public String getMeetingName(Document doc) {
        Element e = doc.getRootElement();
        Element meeting = e.element("node");
        return meeting.attributeValue("TEXT");
    }


    public Date getMeetingStart(Document doc) throws ParseException {
        XPath xpath = DocumentHelper.createXPath("/map/node/attribute[@NAME='startDate']");
        Element clock = (Element) xpath.selectSingleNode(doc);
        String created = clock.attributeValue("VALUE");
        return getDateFromAttribute(created);
    }
    public Date getMeetingEnd(Document doc) throws ParseException {
        XPath xpath = DocumentHelper.createXPath("/map/node/attribute[@NAME='endDate']");
        Element clock = (Element) xpath.selectSingleNode(doc);
        String created = clock.attributeValue("VALUE");
        return getDateFromAttribute(created);
    }

    protected Date getDateFromAttribute(String attribute) throws ParseException {
        return formatter.parse(attribute);
    }

    protected Date getDateFromLongText(String longText) {
        return new Date(Long.parseLong(longText));
    }


    public String getProject(Document doc) {
        XPath xpath = DocumentHelper.createXPath("/map/node/attribute[@NAME='project']");
        Element project = (Element) xpath.selectSingleNode(doc);
        if (project != null) {
            return project.attributeValue("VALUE");
        }
        else {
            return null;
        }
    }

    @Override
    public String getTemplateID(Document doc) {
        XPath xpath = DocumentHelper.createXPath("/map/attribute_registry/attribute_name[@NAME='templateID']/attribute_value");
        Element templateID = (Element) xpath.selectSingleNode(doc);
        if (templateID == null) {
            return null;
        }
        return templateID.attributeValue("VALUE");
    }



}
