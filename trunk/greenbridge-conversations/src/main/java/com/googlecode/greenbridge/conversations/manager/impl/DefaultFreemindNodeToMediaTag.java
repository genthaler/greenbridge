/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.Tag;
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
    private long endOffsetConstant = 5000l;

    public DefaultFreemindNodeToMediaTag(TagManager tagManager) {
        this.tagManager = tagManager;
    }


    @Override
    public MediaTag createMediaTagFromNode(Element node, Date meetingStart, Long project_id, Integer tagStartOffset, Integer tagDuration) {
        MediaTag tag = new MediaTag();
        setName(tag, node, project_id);
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
    protected void setName(MediaTag mediaTag, Element node, Long project_id)  throws AssertionError {
        XPath xpath = node.createXPath("attribute[@NAME='Tag']");
        Element tagAttribute = (Element)xpath.selectSingleNode(node);
        if (tagAttribute != null) {
            String name =tagAttribute.attributeValue("VALUE");
            Tag tag = tagManager.findTagByName(name, project_id);
            mediaTag.setTag(tag);
        } else {
            //The tag does not exist. Try just the text
            String name = node.attributeValue("TEXT");
            Tag tag = tagManager.findTagByName(name, project_id);
            mediaTag.setTag(tag); 
        }
        
    }


    protected void setStartTime(MediaTag tag, Element node, Date meetingStart, Integer tagStartOffset) {
        String longText = node.attributeValue("CREATED");
        Date tagStart = new Date(Long.parseLong(longText));
        long startOffset = (tagStart.getTime() - meetingStart.getTime()) /1000;
        if (tagStartOffset != null && tagStartOffset >= startOffset) {
            startOffset = startOffset - tagStartOffset;
        }
        tag.setStartTime(startOffset);
    }

    protected void setEndTime(MediaTag tag, Element node, Date meetingStart, Integer tagDuration) {
        long oldestTagInBranch = treeWalkOldest(node);
        long endOffset = (oldestTagInBranch - meetingStart.getTime() + endOffsetConstant) / 1000;
        tag.setEndTime(endOffset);
    }

    protected void setExtraInfo(MediaTag tag, Element node) {
        List<MediaTagExtraInfo> extraInfo = new ArrayList<MediaTagExtraInfo>();
        tag.setMediaTagExtraInfos(extraInfo);
        setShortDiscription(tag, node);
        setLongDescription(tag, node);
        setIcons(tag,node);
        setAttributes(tag,node);
    }


    protected void setShortDiscription(MediaTag tag, Element node) {
        String description = node.attributeValue("TEXT");
        if (tag.getTag() != null && !description.equals(tag.getTag().getTagName())) {
            addExtraInfo("shortDescription", description, tag);
        }
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

    protected void setAttributes(MediaTag tag, Element node) {
        XPath xpath = node.createXPath("attribute[@NAME!='Tag']");
        List<Element> tagAttribute = xpath.selectNodes(node);
        for (Element attribute : tagAttribute) {
            addExtraInfo(attribute.attributeValue("NAME"), attribute.attributeValue("VALUE"), tag);
        }
    }


    private void addExtraInfo(String property, String value, MediaTag tag) {
        MediaTagExtraInfo extraInfo = new MediaTagExtraInfo();
        extraInfo.setProp(property);
        extraInfo.setEntry(value);
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
