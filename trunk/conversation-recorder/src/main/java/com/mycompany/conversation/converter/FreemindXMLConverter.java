/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.converter;

import com.mycompany.conversation.Slugger;
import com.mycompany.conversation.domain.Conversation;
import com.mycompany.conversation.domain.Media;
import com.mycompany.conversation.domain.Tag;
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
public class FreemindXMLConverter {

    protected SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    protected FreemindNodeConverter nodeConverter = new FreemindNodeConverter();
    protected Slugger slugger = new Slugger();
    protected int startTagOffset = -5;
    protected int defaultTagDuration = 30;

    public FreemindXMLConverter() {

    }

    public FreemindXMLConverter(int startTagOffset, int defaultTagDuration) {
        this.startTagOffset = startTagOffset;
        this.defaultTagDuration = defaultTagDuration;
    }

    public void setStartTagOffset(int startTagOffset) {
        this.startTagOffset = startTagOffset;
    }

    public void setDefaultTagDuration(int defaultTagDuration) {
        this.defaultTagDuration = defaultTagDuration;
    }


    public Conversation parseConversation(InputStream stream) throws Exception {
        Document doc = parseStream(stream);
        Conversation c = new Conversation();
        c.setId(slugger.generateSlug(getMeetingName(doc)));
        Media m = new Media();
        m.setStartdate(getMeetingStart(doc));
        int mediaLength = (int)(getMeetingEnd(doc).getTime() - getMeetingStart(doc).getTime())/1000;
        m.setMediaLength(mediaLength);
        c.setMedia(m);

        //try {
        //    c.setDescription(getDescription(doc));
        //} catch (Exception e) {}
        String defaultTag = getDefaultTag(doc);
        List<Tag> tags = getTags(doc, defaultTag, startTagOffset, defaultTagDuration);
        c.setTags(tags);



        return c;

    }

    protected Document parseStream(InputStream stream) throws Exception {
        SAXReader reader = new SAXReader();
        return reader.read(stream);
    }



    public List<String> getAttendeeList(Document doc) {
        List<String> attendees = new ArrayList<String>();
        XPath xpath = DocumentHelper.createXPath("/map/node/attribute[translate(@NAME, 'PERSON', 'person') ='person']");

        //XPath xpath = DocumentHelper.createXPath("//node[@TEXT='Attendees']/attribute[@NAME='Attendee']");
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

    public String getDefaultTag(Document doc)  {
        String defaultTag = "tag";
        try {
            XPath xpath = DocumentHelper.createXPath("/map/node/attribute[@NAME='default-tag']");
            Element dte = (Element) xpath.selectSingleNode(doc);
            if (dte == null) return defaultTag;
            return dte.attributeValue("VALUE");
        } catch (Exception e) {
            return defaultTag;
        }
    }

    public String getDescription(Document doc) {
        XPath xpath = DocumentHelper.createXPath("/map/node/richcontent[@TYPE='NOTE']/html/body");
        Element richcontent = (Element)xpath.selectSingleNode(doc);
        if (richcontent != null) {
            return richcontent.asXML();
        }
        return null;
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





    public List<Tag> getTags(Document doc, String defaultTag,Integer tagStartOffset, Integer tagDuration) throws Exception {
        List<Tag> mediaTags = new ArrayList<Tag>();
        Date meetingStart = getMeetingStart(doc);
        List<Element> elements = findElementsWithCreatedBetweenMeetingTime(meetingStart, getMeetingEnd(doc), doc);
        for (Element element : elements) {
            Tag tag = nodeConverter.createTagFromNode(element, meetingStart, defaultTag, tagStartOffset, tagDuration);
            mediaTags.add(tag);
        }
        return mediaTags;
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

}
