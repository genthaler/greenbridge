/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.converter;

import com.mycompany.conversation.domain.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 *
 * @author ryan
 */
public class FreemindNodeConverter {

    private long endOffsetConstant = 5000l;

    public Tag createTagFromNode(Element node, Date meetingStart, String defaultTag, Integer tagStartOffset, Integer tagDuration) {
        Tag tag = new Tag();
        setNameAndShortDescription(tag, node, defaultTag);
        setStartTime(tag, node, meetingStart, tagStartOffset);
        setDuration(tag,node, meetingStart, tagDuration);
        setExtraInfo(tag, node);
        return tag;
    }

    /**
     *
     * @param mediaTag
     * @param node
     * @throws java.lang.AssertionError if the best name is nt a valid tag name.
     */
    protected void setNameAndShortDescription(Tag mediaTag, Element node, String defaultTag)  throws AssertionError {
        XPath xpath = node.createXPath("attribute[translate(@NAME, 'TAG', 'tag') ='tag']");
        Element tagAttribute = (Element)xpath.selectSingleNode(node);
        if (tagAttribute != null) {
            String name =tagAttribute.attributeValue("VALUE");
            String description = node.attributeValue("TEXT");
            mediaTag.setTag(name);
            mediaTag.setDescription(description);
        }
        else {
            String text = node.attributeValue("TEXT");
            if (StringUtils.isNotEmpty(defaultTag)) {
                mediaTag.setTag(defaultTag);
                mediaTag.setDescription(text);
            } else {
                mediaTag.setTag(text);
            }
        }

    }


    protected void setStartTime(Tag tag, Element node, Date meetingStart, Integer tagStartOffset) {
        String longText = node.attributeValue("CREATED");

        Date tagStart = new Date(Long.parseLong(longText));
        long startOffset = (tagStart.getTime() - meetingStart.getTime()) /1000;

        if (tagStartOffset != null && tagStartOffset <= startOffset) {
            startOffset = startOffset - tagStartOffset;
        }
        tag.setMediaOffset((int)startOffset);

        Date tagDate = new Date(meetingStart.getTime() + startOffset);
        tag.setStart(tagDate);

    }

    protected void setDuration(Tag tag, Element node, Date meetingStart, Integer tagDuration) {
        long tagCreated = Long.parseLong(node.attributeValue("CREATED"));
        long oldestTagInBranch = treeWalkOldest(node);
        int duration = (int)((oldestTagInBranch - tagCreated) / 1000) + tagDuration;
        tag.setDuration(duration);
    }

    protected void setExtraInfo(Tag tag, Element node) {
        setLongDescription(tag, node);
        setIcons(tag,node);
    }




    protected void setLongDescription(Tag tag, Element node)  {
        XPath xpath = node.createXPath("richcontent[@TYPE='NOTE']/html");
        Element richcontent = (Element)xpath.selectSingleNode(node);
        if (richcontent != null) {
            tag.setDescription(richcontent.asXML());
        }
    }
    protected void setIcons(Tag tag, Element node) {
        XPath xpath = node.createXPath("icon");
        List<Element> icons = xpath.selectNodes(node);
        List<String> icons_to_add  = new ArrayList<String>();
        for (Element icon : icons) {
            String type = icon.attributeValue("BUILTIN");
            icons_to_add.add(type);
        }
        tag.setIcons(icons_to_add);
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
