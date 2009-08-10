/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.manager.*;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 *
 * @author ryan
 */
public class ChapterToolXMLTagParserStrategy implements XmlTagParserStrategy{

    private TagManager tagManager;

    public ChapterToolXMLTagParserStrategy(TagManager tagManager) {
        this.tagManager = tagManager;
    }


    @Override
    public Document parseDocument(InputStream stream) throws Exception {
        SAXReader reader = new SAXReader();
        return  reader.read(stream);
    }
    @Override
    public List<MediaTag> getTags(Document doc, Long project_id) throws Exception {
        List<MediaTag> mediaTags = new ArrayList<MediaTag>();
        Element e = doc.getRootElement();
        List chapters = e.elements("chapter");
        for (Object object : chapters) {
            MediaTag mediaTag = parseChapter((Element) object, project_id);
            mediaTags.add(mediaTag);
        }
        return mediaTags;
    }
    

    public MediaTag parseChapter(Element chapter, Long project) {
        MediaTag mediaTag = new MediaTag();
        String start = chapter.attributeValue("starttime");
        long startTime = Utils.getTimeInMilliseconds(start);
        mediaTag.setStartTime(startTime);
        String end = chapter.attributeValue("endtime");
        if (StringUtils.isNotEmpty(end)) {
            long endTime = Utils.getTimeInMilliseconds(end);
            mediaTag.setEndTime(endTime);
        }
        else {
            mediaTag.setEndTime(startTime);
        }
        String title = chapter.elementText("title");
        Tag tag = tagManager.findTagByName(title, project);
        mediaTag.setTag(tag);
        return mediaTag;
    }


}
