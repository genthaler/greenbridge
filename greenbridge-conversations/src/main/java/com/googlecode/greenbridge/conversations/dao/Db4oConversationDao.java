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
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.ArrayList;
import java.util.Comparator;
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
        System.out.println("There are " + result.size() + " conversations found (dao)");
        System.out.println("The offset is: " + offset);
        if (result.size() >= offset) {
            int current = offset;
            int bound = offset + limit;
            System.out.println("Offset + limit" + bound);
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
        getDb4oTemplate().set(c);

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
        System.out.println("We found a tag in dao by id: " + id);
        System.out.println("We found a tag in dao by id: " + tags.size());
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
    public void deleteMediaTagById(String mediaTagId) {
        
        MediaTag tag = findMediaTagById(mediaTagId);


        System.out.println("Deleteing: " + mediaTagId + " with name " + tag.getTag().getTagName());
        getDb4oTemplate().delete(tag);
        getDb4oTemplate().getObjectContainer().commit();
    }

    @Override
    public void saveMediaTag(MediaTag tag) {
        getDb4oTemplate().set(tag);
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
    public List<Tag> findAllTags() {
        Query q = getDb4oTemplate().query();
        q.constrain(Tag.class);
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
        System.out.println("We found " + tags.size() + " tags with the name " + name);
        if (tags == null || tags.size() == 0) return null;
        if (tags.size() >= 1) {
            System.out.println("Returning the first tag");
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



}
