/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.Attendee;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Project;
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
import com.googlecode.greenbridge.conversations.manager.PersonManager;
import com.googlecode.greenbridge.conversations.manager.SlugGenerator;
import com.googlecode.greenbridge.conversations.manager.TagUpdateDetails;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

/**
 *
 * @author ryan
 */
public class ConversationManagerImpl implements ConversationManager, MediaTagManager {

    private ConversationDao dao;
    private SlugGenerator slugger;
    private ChapterToolXMLTagParserStrategy tagParser;
    private FullConversationTagParsingStratgey freemindParser;
    private PersonManager personManager;


    public ConversationManagerImpl(ConversationDao dao, SlugGenerator slugger, ChapterToolXMLTagParserStrategy tagParser, FullConversationTagParsingStratgey freemindParser, PersonManager personManager) {
        this.dao = dao;
        this.slugger = slugger;
        this.tagParser = tagParser;
        this.freemindParser = freemindParser;
        this.personManager = personManager;
    }


   @Override
    public Conversation findConversation(int day, int month, int year, String slug) {
        Date before = getDayBefore(day, month, year);
        Date after  = getDayAfter(day, month, year);
        List<Conversation> conversations = dao.loadBetweenDatesAndContainsSlug(before, after, slug);
        if (conversations.size() > 0) {
            return conversations.get(0);
        } else {
            throw new RuntimeException("No conversation found");
        }
    }

   protected final Date getDayBefore(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
   }
   protected final Date getDayAfter(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
   }

    private List<Attendee> convertToAttendees(List<String> people, Conversation c) {
        List<Attendee> attendees = new ArrayList<Attendee>();
        for (String slug : people) {
            Person p = personManager.findPersonForText(slug);
            if (p == null && personManager.isEmailAddress(slug)) {
                // no person. Create a new one only if it is an email address, and there is not an existing person
                p = personManager.createPersonForEmailText(slug);
            }
            Attendee attendee = createAttendee(p, slug, c);
            attendees.add(attendee);
        }
        return attendees;
    }

    private Attendee createAttendee(Person p, String slug, Conversation c) {
        Attendee attendee = new Attendee();
        attendee.setConversation(c);
        if (p == null) {
            // there was no match, and no new person was created. set the slug on
            // the attendee but dont set as resolved to a person.
            attendee.setPersonSlug(slug);
            attendee.setResolvedToPerson(false);
        } else {
            attendee.setPersonId(p.getId());
            attendee.setResolvedToPerson(true);
        }
        return attendee;
    }



   private String generateUUID() {
       return UUID.randomUUID().toString();
   }

   public final void setAllMediaTagsMedia(List<MediaTag> mediaTags, Media media) {
       for (MediaTag mediaTag : mediaTags) {
           mediaTag.setMedia(media);
           mediaTag.setId(generateUUID());
       }
   }

    @Override
    public Conversation newConversation(AppleChapterConversationDetails conversationDetails) throws Exception {
        Conversation c = new Conversation();
        c.setId(generateUUID());
        c.setName(conversationDetails.getName());
        c.setDescription(conversationDetails.getDescription());
        c.setStartTime(conversationDetails.getConversationDate());
        ConversationTemplate template = null;
        String conversationTemplateID = conversationDetails.getTempateID();
        if (conversationTemplateID != null) {
            template =  dao.findTemplateByID(conversationTemplateID);
            c.setCategory(template.getCategory());
        }

        Media m = new Media();
        m.setStartTime(conversationDetails.getConversationDate());
        m.setMediaLength(conversationDetails.getCalculatedDurationInSec());
        m.setConversation(c);
        m.setUrl(conversationDetails.getMediaUrl());
        m.setMediaType(1);
        Set<Media> medias = new HashSet<Media>();
        medias.add(m);
        c.setMedia(medias);
        List<MediaTag> mediaTags = null;
        if (conversationDetails.getAppleChapterXMLStream() != null) {
             Document doc = tagParser.parseDocument(conversationDetails.getAppleChapterXMLStream());
             mediaTags = tagParser.getTags(doc, conversationDetails.getTempateID(), null, null);
        } else {
            mediaTags = new ArrayList<MediaTag>();
        }

        m.setMediaTags(mediaTags);
        setAllMediaTagsMedia(mediaTags, m);
        setAllMediaTagsDate(mediaTags, c.getStartTime());
        dao.save(c);
        return c;
    }

    private void setAllMediaTagsDate(List<MediaTag> mediaTags, Date conversationStart) {
        for (MediaTag mediaTag : mediaTags) {
            Date tagDate = new Date(conversationStart.getTime() + mediaTag.getStartTime());
            mediaTag.setDate(tagDate);
        }
    }



    @Override
    public Conversation newConversation(FreemindConversationDetails conversationDetails) throws Exception {

        Document doc = freemindParser.parseDocument(conversationDetails.getFreemindXMLStream());

        Conversation c = new Conversation();
        c.setId(generateUUID());
        c.setName(freemindParser.getMeetingName(doc));
        c.setStartTime(freemindParser.getMeetingStart(doc));
        c.setFreemindUrl(conversationDetails.getFreemindUrl());
        c.setSlug(slugger.generateSlug(c.getName()));

        ConversationTemplate template = null;
        String conversationTemplateID = freemindParser.getTemplateID(doc);
        if (conversationTemplateID != null) {
            template =  dao.findTemplateByID(conversationTemplateID);
            c.setCategory(template.getCategory());
        }

        Media m = new Media();
        m.setConversation(c);
        m.setUrl(conversationDetails.getMediaUrl());
        m.setMediaType(1);
        m.setMediaLength((freemindParser.getMeetingEnd(doc).getTime() - c.getStartTime().getTime())/1000);
        Set<Media> medias = new HashSet<Media>();
        medias.add(m);
        c.setMedia(medias);

        // needs to happen before the tags
        List<String> people = freemindParser.getAttendeeList(doc);
        List<Attendee> attendees = convertToAttendees(people, c);
        c.setAttendees(attendees);

        List<MediaTag> mediaTags = freemindParser.getTags(doc,conversationTemplateID , conversationDetails.getTagStartOffset(), conversationDetails.getTagDuration());
        m.setMediaTags(mediaTags);
        setAllMediaTagsMedia(mediaTags, m);


        dao.save(c);
        return c;
    }

    @Override
    public ConversationSearchResults listAllConversations(Integer page, Integer limit) throws Exception {
        int offset = (page-1) * limit;

        List<Conversation> result = dao.findAllConversations(offset, limit);
        ConversationSearchResults csr = new ConversationSearchResults();
        csr.setTotalConversationInResults(dao.countConversations());
        List<ConversationSummary> summaryList = new ArrayList<ConversationSummary>();
        for (Conversation conversation : result) {
            ConversationSummary summary = new ConversationSummary();
            summary.setId(conversation.getId());
            summary.setName(conversation.getName());
            summary.setSlug(conversation.getSlug());
            summary.setDate(conversation.getStartTime());
            summaryList.add(summary);
        }
        csr.setConversations(summaryList);
        return csr;
    }

    @Override
    public Long addTag(String conversationId, TagUpdateDetails details) {
        Conversation c = dao.loadConversationById(conversationId);
        MediaTag mediaTag = new MediaTag();
        Tag tag = null;
        if (details.getTagId() == null) {
            tag = new Tag();
            tag.setId(generateUUID());
            tag.setTagName(slugger.generateSlug(details.getTagName()));
            if (StringUtils.isNotBlank(details.getTagProjectId())) {
                tag.setProjectId(details.getTagProjectId());
            }
            dao.saveTag(tag);
        } else {
            String projectId = null;
            if (details.getTagProjectId() != null) projectId = details.getTagProjectId();
            else if (details.getTagProjectName() != null) {
                   projectId = dao.findProjectById(details.getTagProjectName()).getId();
            }
            System.out.println("In dao: " + details.getTagName());
              System.out.println("In dao: " + projectId);
            tag = dao.findTagByNameAndProjectId(details.getTagName(), projectId);
            System.out.println("In dao: " + tag.getTagName());

        }


        mediaTag.setId(generateUUID());
        mediaTag.setTag(tag);
        mediaTag.setStartTime((long)details.getStartTime());
        mediaTag.setEndTime((long)details.getEndTime());
        mediaTag.setShortDescription(details.getShortDescription());
        Media media = c.findFirstMedia();


        mediaTag.setMedia(media);
        media.getMediaTags().add(mediaTag);

        dao.saveMediaTag(mediaTag);
        dao.save(media);
        dao.save(c);
        
        return null;
    }

    @Override
    public void updateTag(String conversationId, TagUpdateDetails details) {
        MediaTag mediaTag = dao.findMediaTagById(details.getMediaTagId());
        Tag tag = null;
        if (details.getTagId() == null) {
            tag = new Tag();
            tag.setId(generateUUID());
            tag.setTagName(slugger.generateSlug(details.getTagName()));
            if (StringUtils.isNotBlank(details.getTagProjectId())) {
                tag.setProjectId(details.getTagProjectId());
            }
            dao.saveTag(tag);
        } else {
            String projectId = null;
            if (details.getTagProjectId() != null) projectId = details.getTagProjectId();
            else if (details.getTagProjectName() != null) {
                   projectId = dao.findProjectById(details.getTagProjectName()).getId();
            }
            System.out.println("In dao: " + details.getTagName());
              System.out.println("In dao: " + projectId);
            tag = dao.findTagByNameAndProjectId(details.getTagName(), projectId);
            System.out.println("In dao: " + tag.getTagName());
        }

        
        mediaTag.setTag(tag);
        mediaTag.setStartTime((long)details.getStartTime());
        mediaTag.setEndTime((long)details.getEndTime());
        mediaTag.setShortDescription(details.getShortDescription());
        dao.saveMediaTag(mediaTag);
    }

    @Override
    public void deleteTag(String mediaTagID) {
        MediaTag mediaTag = dao.findMediaTagById(mediaTagID);
        Media media = mediaTag.getMedia();
        media.getMediaTags().remove(mediaTag);
        dao.save(media);
        dao.deleteMediaTagById(mediaTagID);
    }

    private TagUpdateDetails convert(MediaTag tag) {
        if (tag == null) return null;
        TagUpdateDetails details = new TagUpdateDetails();
        details.setTagId(tag.getTag().getId());
        details.setMediaTagId(tag.getId());
        details.setTagName(tag.getTag().getTagName());
        details.setStartTime((double)tag.getStartTime());
        details.setEndTime((double)tag.getEndTime());
        details.setShortDescription(tag.getShortDescription());
        return details;

    }

    @Override
    public TagUpdateDetails loadTag(String conversationId, String mediaTagId) {
        MediaTag mediaTag = dao.findMediaTagById(mediaTagId);
        return convert(mediaTag);
    }



    @Override
    public MediaTagSearchResults searchForProjectTags(String tagName, String projectName, Integer page, Integer limit) throws Exception {
        Project project = dao.findProjectByName(projectName);
        Tag tag = dao.findTagByNameAndProjectId(tagName, project.getId());
        return findAllMediaForTag(tag, page, limit);
    }

    @Override
    public MediaTagSearchResults searchForGlobalTags(String tagName, Integer page, Integer limit) throws Exception {
        Tag tag = dao.findTagByNameAndProjectId(tagName, null);
        return findAllMediaForTag(tag, page, limit);
    }




    protected MediaTagSearchResults findAllMediaForTag(Tag tag, Integer page, Integer limit) throws Exception {   
        List<MediaTag> tags = dao.findAllMediaTagByTag(tag);
        return SearchUtils.buildSearchResults(tags, page, limit);
    }


    @Override
    public Map<String,String> findAllTagsByLike(String prefix) {
        List<Tag> tags = dao.queryTagsByLike(prefix);
        return createMapFromTags(tags);
    }

    @Override
    public Map<String,String> findAllTags() {
        List<Tag> tags = dao.findAllTags();
        return createMapFromTags(tags);

    }

    protected Map<String,String> createMapFromTags(List<Tag> tags) {
        Map<String,String> data = new TreeMap<String,String>();
        for (Tag tag : tags) {
            StringBuffer name = new StringBuffer(tag.getTagName());
            if (tag.getProjectId() != null) {
                Project p = dao.findProjectById(tag.getProjectId());
                if (p != null) {
                    name.append(" [").append(p.getName()).append("]");
                }
            }
            data.put( name.toString(), name.toString());
        }

        return data;
    }













    @Override
    public Conversation findById(String conversationID) {
        Conversation c = dao.loadConversationById(conversationID);
        orderMediaTags(c);
        return c;
    }

    protected void orderMediaTags(Conversation c) {
        Collections.sort(c.findFirstMedia().getMediaTags(), mediaTagComparator);
    }

    public  MediaTagComparator mediaTagComparator = new MediaTagComparator();


    private class MediaTagComparator implements Comparator<MediaTag> {
        @Override
        public int compare(MediaTag o1, MediaTag o2) {
             return (int) ( o1.getStartTime() - o2.getStartTime());
        }
    };

}
