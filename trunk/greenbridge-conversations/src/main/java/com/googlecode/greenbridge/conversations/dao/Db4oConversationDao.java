/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfoPerson;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springmodules.db4o.support.Db4oDaoSupport;

/**
 *
 * @author ryan
 */
public class Db4oConversationDao extends Db4oDaoSupport implements ConversationDao {

    @Override
    public int countConversations() {
        Query q = getDb4oTemplate().query();
        q.constrain(Conversation.class);
        return q.execute().size();
    }


    public void deleteConversation(String id) {
        Conversation c = loadConversationById(id);
        getDb4oTemplate().delete(c);
        getDb4oTemplate().getObjectContainer().commit();
    }


    @Override
    public List<Conversation> findAllConversations(Integer offset, Integer limit) {
        Query q = getDb4oTemplate().query();
        q.constrain(Conversation.class);
        
        ArrayList<Conversation> list = new ArrayList<Conversation>();
        ObjectSet result = q.execute();
        if (result.size() >= offset) {
            int current = offset;
            int bound = offset + limit;
            while((current < (offset + limit)) && (current < result.size()) ) {
                Conversation c = (Conversation)result.get(current);
                getDb4oTemplate().activate(c, 2);
                list.add(c);
                current++;
            }
        }
        return list;
    }


    @Override
    public List<Conversation> loadBetweenDatesAndContainsSlug(Date start, Date end, String slug) {
        Query q = getDb4oTemplate().query();
        q.constrain(Conversation.class);
        q.descend("startTime").constrain(start).greater();
        q.descend("startTime").constrain(end).smaller();
        q.descend("slug").constrain(slug).equal();
        return (List<Conversation>)q.execute();
    }

    @Override
    public void save(Conversation c) {
        getDb4oTemplate().set(c, 5);

        getDb4oTemplate().getObjectContainer().commit();

    }
    @Override
    public void save(Media media) {
        getDb4oTemplate().set(media, 3);
        getDb4oTemplate().getObjectContainer().commit();
        getDb4oTemplate().refresh(media, 10);
    }


    @Override
    public ConversationTemplate findTemplateByID(String id) {
        ConversationTemplate template = new ConversationTemplate();
        template.setId(id);
        return (ConversationTemplate) getDb4oTemplate().get(template).get(0);
    }

    @Override
    public Conversation loadConversationById(String id) {
        Conversation template = new Conversation();
        template.setId(id);
        Conversation c = (Conversation) getDb4oTemplate().get(template).get(0);
        getDb4oTemplate().activate(c, 7);
        return c;
    }

    @Override
    public Tag findTagById(String id) {
        Tag template = new Tag();
        template.setId(id);
        List<Tag> tags = getDb4oTemplate().get(template);
        //there really should only be one
        if (tags == null) return null;
        if (tags.size() == 0 ) return null;
        return (Tag) tags.get(0);
    }


    @Override
    public MediaTag findMediaTagById(String mediaTagId) {
        MediaTag template = new MediaTag();
        template.setId(mediaTagId);
        return (MediaTag) getDb4oTemplate().get(template).get(0);
    }

    @Override
    public List<MediaTag> findAllMediaTagByTag(Tag tag) {
        Query q = getDb4oTemplate().query();
        q.constrain(MediaTag.class);
        q.descend("tag").descend("id").constrain(tag.getId()).equal();
        q.descend("date").orderDescending();
        return (List<MediaTag>)q.execute();
    }
    @Override
    public List<MediaTag> findTagsByPerson(final String personId, final Tag tag) {
        Query q = getDb4oTemplate().query();
        q.constrain(MediaTagExtraInfoPerson.class);
        q.descend("prop").constrain(MediaTagExtraInfoPerson.getPROPERTY_NAME()).equal();
        q.descend("entry").constrain(personId).equal();
        Query mediaTagQuery = q.descend("mediaTag");
        if (tag != null) {
            mediaTagQuery.descend("tag").constrain(tag).equal();
        }
        return  mediaTagQuery.execute();
    }


    @Override
    public void deleteMediaTagById(String mediaTagId) {
        MediaTag tag = findMediaTagById(mediaTagId);
        getDb4oTemplate().delete(tag);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public void saveMediaTag(MediaTag tag) {
        getDb4oTemplate().set(tag);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public void saveMediaTagExtraInfo(MediaTagExtraInfo info) {
        getDb4oTemplate().set(info);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public void deleteMediaTagExtraInfo(MediaTagExtraInfo info) {
        getDb4oTemplate().delete(info);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public void saveTag(Tag t) {
        getDb4oTemplate().set(t);
        getDb4oTemplate().getObjectContainer().commit();
    }


    @Override
    public Tag findTagByNameAndProjectId(String name, String projectId) {
        Tag template = new Tag();
        template.setTagName(name);
        template.setProjectId(projectId);
        List<Tag> tags = getDb4oTemplate().get(template);
        //there really should only be one
        if (tags == null) return null;
        if (tags.size() == 0 ) return null;
        return (tags.get(0));
    }

    @Override
    public Project findProjectById(String id) {
        Project template = new Project();
        template.setId(id);
        List<Project> projects = getDb4oTemplate().get(template);
        //there really should only be one
        if (projects == null) return null;
        if (projects.size() == 0 ) return null;
        return (projects.get(0));
    }

    @Override
    public Project findProjectByName(String name) {
        Project template = new Project();
        template.setName(name);
        List<Project> projects = getDb4oTemplate().get(template);
        //there really should only be one
        if (projects == null) return null;
        if (projects.size() == 0 ) return null;
        return (projects.get(0));
    }

    @Override
    public List<Tag> findAllTags() {
        Query q = getDb4oTemplate().query();
        q.constrain(Tag.class);
        q.descend("tagName").orderAscending();
        return (List<Tag>)q.execute();
    }

    @Override
    public List<Tag> queryTagsByLike(String prefix) {
        Query q = getDb4oTemplate().query();
        q.constrain(Tag.class);
        q.descend("tagName").constrain(prefix).like();
        return (List<Tag>)q.execute();
    }

    @Override
    public Tag findTagByName(String name, String templateID) {
        if (templateID != null) {
            ConversationTemplate ct = findTemplateByID(templateID);
            Tag tag = findTagInTagList(name, ct.getTags());
            if (tag != null) return tag;
            else {
                if (ct.getDefaultProjectID() != null) {
                    Tag template = new Tag();
                    template.setTagName(name);
                    template.setProjectId(ct.getDefaultProjectID());
                    List<Tag> tags = getDb4oTemplate().get(template);
                    if (tags == null || tags.size() == 0) return null;
                    if (tags.size() >= 1) return tags.get(0);
                }
            }
        }
        // do a global tag search
        Tag template = new Tag();
        template.setTagName(name);

        List<Tag> tags = getDb4oTemplate().get(template);
        if (tags == null || tags.size() == 0) return null;
        if (tags.size() >= 1) {
            return tags.get(0);
        }

        // the kitchen sinik
        return null;
    }





    protected Tag findTagInTagList(String tagName, List<Tag> tags) {
        for (Tag tag : tags) {
                if (tag.getTagName().equals(tagName)) {
                    return tag;
                }
        }
        return null;
    }



    @Override
    public List<Tag> findAllGlobalTags() {
        Query q = getDb4oTemplate().query();
        q.constrain(Tag.class);
        q.descend("projectId").constrain(null).equal();
        return (List<Tag>)q.execute();
    }

    @Override
    public List<Tag> findTagsByProject(String projectId) {
       Query q = getDb4oTemplate().query();
       q.constrain(Tag.class);
       q.descend("projectId").constrain(projectId).equal();
       return (List<Tag>)q.execute();
    }


 


    @Override
    public void saveProject(Project project) {
       getDb4oTemplate().set(project);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public List<Project> findAllProjects() {
        Query q = getDb4oTemplate().query();
        q.constrain(Project.class);
        return (List<Project>)q.execute();
    }

    @Override
    public void deleteTag(Tag tag) {
        getDb4oTemplate().delete(tag);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public List<Person> findAllPeople() {
        Query q = getDb4oTemplate().query();
        q.constrain(Person.class);
        return (List<Person>)q.execute();
    }

    @Override
    public void savePerson(Person p) {
       getDb4oTemplate().set(p);
       getDb4oTemplate().getObjectContainer().commit();
    }
    
    @Override
    public Person findBySlug(String slug) {
        Person template = new Person();
        template.setSlug(slug);
        List<Person> people = getDb4oTemplate().get(template);
        //there really should only be one
        if (people == null) return null;
        if (people.size() == 0 ) return null;

        System.out.println("Find person by slug returned: " + people.size());
        System.out.println("with id: " + people.get(0).getId());

        return (people.get(0));
    }

    @Override
    public Person findPersonByEmail(String email) {
        Person template = new Person();
        template.setEmail(email);
        List<Person> people = getDb4oTemplate().get(template);
        //there really should only be one
        if (people == null) return null;
        if (people.size() == 0 ) return null;

        System.out.println("Find person by email returned: " + people.size());
        System.out.println("with id: " + people.get(0).getId());
        return (people.get(0));
    }

    @Override
    public void deletePerson(Person p) {
        getDb4oTemplate().delete(p);
        getDb4oTemplate().getObjectContainer().commit();
    }


}
