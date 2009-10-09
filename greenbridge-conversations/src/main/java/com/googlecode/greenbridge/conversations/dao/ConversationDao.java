/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public interface ConversationDao {

    public void deleteTag(Tag current);

    public Person findPersonByEmail(String slug);

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
    List<Tag> findTagsByProject(String projectId);

    void deleteMediaTagById(String mediaTagId);

    MediaTag findMediaTagById(String mediaTagId);

    List<MediaTag> findAllMediaTagByTag(Tag tag);
    List<MediaTag> findTagsByPerson(String personId, Tag tag);

    void saveMediaTag(MediaTag tag);

    void saveTag(Tag tag);

    Tag findTagByNameAndProjectId(String name, String projectId);

    Project findProjectById(String id);
    Project findProjectByName(String name);

    void saveProject(Project project);

    List<Project> findAllProjects();

    List<Person> findAllPeople();

    void savePerson(Person p);

    void deletePerson(Person p);

    void saveMediaTagExtraInfo(MediaTagExtraInfo info);
    void deleteMediaTagExtraInfo(MediaTagExtraInfo info);

    Person findBySlug(String slug);

}
