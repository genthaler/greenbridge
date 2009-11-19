/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfoPerson;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.PersonManager;
import com.googlecode.greenbridge.conversations.manager.TagManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 *
 * @author ryan
 */
public class DefaultFreemindNodeToMediaTag implements FreemindNodeToMediaTagStrategy {


    private TagManager tagManager;
    private PersonManager personManager;
    private long endOffsetConstant = 5000l;

    public DefaultFreemindNodeToMediaTag(TagManager tagManager, PersonManager personManager) {
        this.tagManager = tagManager;
        this.personManager = personManager;
    }


    @Override
    public MediaTag createMediaTagFromNode(Element node, Date meetingStart, String template_id, Integer tagStartOffset, Integer tagDuration) {
        MediaTag tag = new MediaTag();
        List<MediaTagExtraInfo> extraInfo = new ArrayList<MediaTagExtraInfo>();
        tag.setMediaTagExtraInfos(extraInfo);
        setNameAndShortDescription(tag, node, template_id);
        setStartTime(tag, node, meetingStart, tagStartOffset);
        setEndTime(tag,node, meetingStart, tagDuration);
        setExtraInfo(tag, node);
        return tag;
    }

    /**
     *
     * @param mediaTag
     * @param node
     * @throws java.lang.AssertionError if the best name is nt a valid tag name.
     */
    protected void setNameAndShortDescription(MediaTag mediaTag, Element node, String template_id)  throws AssertionError {
        XPath xpath = node.createXPath("attribute[translate(@NAME, 'TAG', 'tag') ='tag']");
        Element tagAttribute = (Element)xpath.selectSingleNode(node);
        if (tagAttribute != null) {
            String name =tagAttribute.attributeValue("VALUE");
            Tag tag = tagManager.findTagByNameOrCreate(name, template_id);
            mediaTag.setTag(tag);

            String description = node.attributeValue("TEXT");
            mediaTag.setShortDescription(description);


        } else {
            //The tag does not exist. Try just the text
            String name = node.attributeValue("TEXT");
            Tag tag = tagManager.findTagByNameOrCreate(name, template_id);
            mediaTag.setTag(tag); 
        }
        
    }


    protected void setStartTime(MediaTag tag, Element node, Date meetingStart, Integer tagStartOffset) {
        String longText = node.attributeValue("CREATED");

        Date tagStart = new Date(Long.parseLong(longText));
        long startOffset = (tagStart.getTime() - meetingStart.getTime()) /1000;

        if (tagStartOffset != null && tagStartOffset <= startOffset) {
            startOffset = startOffset - tagStartOffset;
        }
        tag.setStartTime(startOffset);

        Date tagDate = new Date(meetingStart.getTime() + startOffset);
        tag.setDate(tagDate);

    }

    protected void setEndTime(MediaTag tag, Element node, Date meetingStart, Integer tagDuration) {
        long oldestTagInBranch = treeWalkOldest(node);
        long localEndOfffset = endOffsetConstant;
        if (tagDuration != null) {
            localEndOfffset = tagDuration * 1000;
        }
        long endOffset = (oldestTagInBranch - meetingStart.getTime() + localEndOfffset) / 1000;
        tag.setEndTime(endOffset);
    }

    protected void setExtraInfo(MediaTag tag, Element node) {
        setLongDescription(tag, node);
        setIcons(tag,node);
        setPeople(tag, node);
        setAttributes(tag,node);
    }




    protected void setLongDescription(MediaTag tag, Element node)  {
        XPath xpath = node.createXPath("richcontent[@TYPE='NOTE']/html");
        Element richcontent = (Element)xpath.selectSingleNode(node);
        if (richcontent != null) {
            addExtraInfo("longDescription", richcontent.asXML(), tag);
        } 
    }
    protected void setIcons(MediaTag tag, Element node) {
        XPath xpath = node.createXPath("icon");
        List<Element> icons = xpath.selectNodes(node);
        for (Element icon : icons) {
            String type = icon.attributeValue("BUILTIN");
            addExtraInfo("icon", type, tag);
        }
    }

    protected void setPeople(MediaTag tag, Element node) {
        XPath xpath = node.createXPath("attribute[translate(@NAME, 'PERSON', 'person') ='person']");
        List<Element> people = xpath.selectNodes(node);
        for (Element element : people) {
            String personEmailOrSlug = element.attributeValue("VALUE");
            Person p = personManager.findPersonForText(personEmailOrSlug);
            if (p == null && personManager.isEmailAddress(personEmailOrSlug)) {
                p = personManager.createPersonForEmailText(personEmailOrSlug);
            }
            if (p != null) {
                addExtraInfoPerson("personId", p, tag);
            } else {
                addExtraInfo("person", personEmailOrSlug, tag);
            }
        }
    }





    protected void setAttributes(MediaTag tag, Element node) {
        XPath xpath = node.createXPath("attribute[@NAME!='Tag']");
        List<Element> tagAttribute = xpath.selectNodes(node);
        for (Element attribute : tagAttribute) {
            String name = attribute.attributeValue("NAME").toLowerCase();
            if (!"tag".equals(name) && !"person".equals(name)) {
                addExtraInfo(attribute.attributeValue("NAME"), attribute.attributeValue("VALUE"), tag);
            }
        }
    }


    private void addExtraInfo(String property, String value, MediaTag tag) {
        MediaTagExtraInfo extraInfo = new MediaTagExtraInfo();
        extraInfo.setProp(property);
        extraInfo.setEntry(value);
        extraInfo.setMediaTag(tag);
        tag.getMediaTagExtraInfos().add(extraInfo);
    }

    private void addExtraInfoPerson(String property, Person person, MediaTag tag) {
        MediaTagExtraInfoPerson extraInfo = new MediaTagExtraInfoPerson();
        extraInfo.setProp(property);
        extraInfo.setEntry(person.getId());
        extraInfo.setPerson(person);
        extraInfo.setMediaTag(tag);
        tag.getMediaTagExtraInfos().add(extraInfo);
    }
    public long treeWalkOldest(Element element) {
        long currentEnd = Long.parseLong(element.attributeValue("CREATED"));
        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
            Node node = element.node(i);
            if ( node instanceof Element && node.getName().equalsIgnoreCase("node")) {
                long childEnd = treeWalkOldest( (Element) node );
                if (childEnd > currentEnd) {
                    currentEnd = childEnd;
                }
            }
        }
        return currentEnd;
    }

    /**
     * @return the endOffsetConstant
     */
    public long getEndOffsetConstant() {
        return endOffsetConstant;
    }

    /**
     * @param endOffsetConstant the endOffsetConstant to set
     */
    public void setEndOffsetConstant(long endOffsetConstant) {
        this.endOffsetConstant = endOffsetConstant;
    }
















}
