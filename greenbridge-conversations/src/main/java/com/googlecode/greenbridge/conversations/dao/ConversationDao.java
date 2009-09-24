/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public interface ConversationDao {

    Conversation loadConversationById(String id);

    ConversationTemplate findTemplateByID(String id);

    List<Conversation> loadBetweenDatesAndContainsSlug(Date start, Date end, String slug);

    void save(Conversation c);

    void save(Media media);

    int countConversations() ;
    
    List<Conversation> findAllConversations(Integer offset, Integer limit);

    Tag findTagById(String id);

    Tag findTagByName(String name, String templateID);

    List<Tag> findAllGlobalTags();
    List<Tag> queryTagsByLike(String prefix);
    List<Tag> findAllTags();

    void deleteMediaTagById(String mediaTagId);

    MediaTag findMediaTagById(String mediaTagId);

    List<MediaTag> findAllMediaTagByTag(Tag tag);

    void saveMediaTag(MediaTag tag);

    void saveTag(Tag tag);

    Tag findTagByNameAndProjectId(String name, String projectId);

    Project findProjectById(String id);

}
