/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.AppleChapterConversationDetails;
import com.googlecode.greenbridge.conversations.manager.ConversationManager;
import com.googlecode.greenbridge.conversations.manager.ConversationSearchResults;
import com.googlecode.greenbridge.conversations.manager.ConversationSummary;
import com.googlecode.greenbridge.conversations.manager.FreemindConversationDetails;
import com.googlecode.greenbridge.conversations.manager.FullConversationTagParsingStratgey;
import com.googlecode.greenbridge.conversations.manager.MediaTagManager;
import com.googlecode.greenbridge.conversations.manager.MediaTagSearchResults;
import com.googlecode.greenbridge.conversations.manager.MediaTagSummary;
import com.googlecode.greenbridge.conversations.manager.ProjectManager;
import com.googlecode.greenbridge.conversations.manager.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dom4j.Document;


/**
 *
 * @author ryan
 */
public class ConversationManagerMock implements ConversationManager, MediaTagManager {


    private long internalCount = 1;
    private Map<Long,Conversation> conversations = new HashMap<Long,Conversation>();

    private ChapterToolXMLTagParserStrategy tagParser;
    private FullConversationTagParsingStratgey freemindParser;
    private ProjectManager projectManager;

    public ConversationManagerMock(FullConversationTagParsingStratgey freemindParser, ChapterToolXMLTagParserStrategy tagParser) {
        this.freemindParser = freemindParser;
        this.tagParser = tagParser;
        Conversation c = new Conversation();
        c.setId(internalCount++);
        c.setName("Project1 kickoff");
        c.setDescription("All attedning:");
        c.setStartTime(new Date());

        Media m = new Media();
        m.setConversation(c);
        m.setMediaLength(361L);
        m.setUrl("test3.mp3");
        m.setMediaType(1);
        Set<Media> medias = new HashSet<Media>();
        medias.add(m);
        c.setMedia(medias);

        Tag t = new Tag();
        t.setId(1L);
        t.setTagName("FollowUp");
        t.setTagClass("builtin.FollowUp");
        List<MediaTag> mediaTags = new ArrayList<MediaTag>();
        t.setMediaTags(mediaTags);
        m.setMediaTags(mediaTags);
        {
            MediaTag mediaTag = new MediaTag();
            mediaTag.setId(1L);
            mediaTag.setMedia(m);
            mediaTag.setTag(t);
            mediaTag.setStartTime(10L);
            mediaTag.setEndTime(20L);
            mediaTags.add(mediaTag);
        }
        {
            MediaTag mediaTag = new MediaTag();
            mediaTag.setId(2L);
            mediaTag.setMedia(m);
            mediaTag.setTag(t);
            mediaTag.setStartTime(105L);
            mediaTag.setEndTime(132L);
            mediaTags.add(mediaTag);
        }
        conversations.put(c.getId(), c);

    }


    @Override
    public Conversation findConversation(Long id) {
        return conversations.get(id);
    }

    @Override
    public Conversation newConversation(FreemindConversationDetails conversationDetails) throws Exception {

        Document doc = freemindParser.parseDocument(conversationDetails.getFreemindXMLStream());

        Conversation c = new Conversation();
        c.setId(internalCount++);
        c.setName(freemindParser.getMeetingName(doc));
        c.setStartTime(freemindParser.getMeetingStart(doc));

        Media m = new Media();
        m.setConversation(c);
        m.setUrl(conversationDetails.getMediaUrl());
        m.setMediaType(1);
        m.setMediaLength((freemindParser.getMeetingEnd(doc).getTime() - c.getStartTime().getTime())/1000);
        Set<Media> medias = new HashSet<Media>();
        medias.add(m);
        c.setMedia(medias);

        String project  = freemindParser.getProject(doc);
        Long project_id = projectManager.findProjectByName(project);
        List<MediaTag> mediaTags = freemindParser.getTags(doc, project_id);
        for (MediaTag mediaTag : mediaTags) {
            mediaTag.setId(internalCount++);
        }
        m.setMediaTags(mediaTags);
        Utils.setAllMediaTagsMedia(mediaTags, m);
        conversations.put(c.getId(), c);
        return c;
    }


    @Override
    public Conversation newConversation(AppleChapterConversationDetails conversationDetails) throws Exception {


        Conversation c = new Conversation();
        c.setId(internalCount++);
        c.setName(conversationDetails.getName());
        c.setDescription(conversationDetails.getDescription());
        c.setStartTime(conversationDetails.getConversationDate());

        Media m = new Media();
        m.setConversation(c);
        m.setUrl(conversationDetails.getMediaUrl());
        m.setMediaType(1);
        Set<Media> medias = new HashSet<Media>();
        medias.add(m);
        c.setMedia(medias);
        Document doc = tagParser.parseDocument(conversationDetails.getAppleChapterXMLStream());
        List<MediaTag> mediaTags = tagParser.getTags(doc, conversationDetails.getProject_id());
        m.setMediaTags(mediaTags);
        Utils.setAllMediaTagsMedia(mediaTags, m);

        conversations.put(c.getId(), c);
        return c;
    }

    @Override
    public ConversationSearchResults listAllConversations(Integer offset, Integer limit) throws Exception {
        List<ConversationSummary> results = new ArrayList<ConversationSummary>();
        for (Conversation conversation : conversations.values()) {
            ConversationSummary summary = new ConversationSummary();
            summary.setId(conversation.getId());
            summary.setName(conversation.getName());
            summary.setDate(conversation.getStartTime());
            results.add(summary);
        }

        ConversationSearchResults sResults = new ConversationSearchResults();
        sResults.setConversations(results);
        sResults.setTotalConversationInResults(conversations.size());
        return sResults;
    }

    @Override
    public MediaTagSearchResults searchForGlobalTags(String tagName, Integer offset, Integer limit) throws Exception {
        MediaTagSearchResults results = new MediaTagSearchResults();

        for (Conversation conversation : conversations.values()) {
            for (MediaTag mediaTag: conversation.findFirstMedia().getMediaTags()) {
                if (mediaTag.getTag() != null && mediaTag.getTag().getTagName().equals(tagName)) {
                    MediaTagSummary summary = new MediaTagSummary();
                    summary.setConversationId(conversation.getId());
                    summary.setConversationName(conversation.getName());
                    summary.setMediaUrl(conversation.findFirstMedia().getUrl());
                    summary.setConversationTotalLength(conversation.findFirstMedia().getMediaLength());
                    long mediaTagCalculatedTimestamp = conversation.getStartTime().getTime() + (mediaTag.getStartTime() * 1000);
                    summary.setMediaTagCalculatedTimestamp(new Date(mediaTagCalculatedTimestamp));
                    summary.setMediaTagId(mediaTag.getId());
                    summary.setMediaTagName(tagName);
                    summary.setTagStartTime(mediaTag.getStartTime());
                    summary.setTagEndTime(mediaTag.getEndTime());
                    results.getMediaTags().add(summary);
                }
            }
        }
        results.setTotalMediaTagsInResults(results.getMediaTags().size());
        return results;
    }

    @Override
    public MediaTagSearchResults searchForGroupTags(String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MediaTagSearchResults searchForConversationTags(long conversationId, String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MediaTagSearchResults searchForAttendeeTags(long personId, String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
