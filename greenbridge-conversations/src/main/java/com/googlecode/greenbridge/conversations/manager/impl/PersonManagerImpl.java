/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.MediaTagSearchResults;
import com.googlecode.greenbridge.conversations.manager.PersonManager;
import com.googlecode.greenbridge.conversations.manager.SlugGenerator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ryan
 */
public class PersonManagerImpl implements PersonManager {

    private ConversationDao dao;
    private SlugGenerator slugger;

    public PersonManagerImpl(ConversationDao dao, SlugGenerator slugger) {
        this.dao = dao;
        this.slugger = slugger;
    }

   private String generateUUID() {
       return UUID.randomUUID().toString();
   }

    @Override
    public Person createPersonForEmailText(String text) {
        String name = text.split("@")[0];
        String slug = slugger.generateSlug(name);
        Person test = dao.findBySlug(slug);
        if (test != null) {
            // there is already a person with this slug. Just return.
            return null;
        }
        Person p = new Person();
        p.setEmail(text);
        p.setId(generateUUID());
        p.setName(name);
        p.setSlug(slug);
        dao.savePerson(p);
        return p;
    }

    @Override
    public  Person findPersonForText(String text) {
        if (isEmailAddress(text)) {
            return  dao.findPersonByEmail(text);
        } else {
            return dao.findBySlug(text);
        }
    }
    
    @Override
    public boolean isEmailAddress(String slug) {
       if (slug.contains("@")) return true;
       else return false;
    }
 
 
    @Override
    public MediaTagSearchResults searchForPersonTags(String personId, String tagName, String projectName, Integer page, Integer limit) throws Exception {
        Tag tag = null;
        if (tagName != null && projectName != null) {
            Project project = dao.findProjectByName(projectName);
            String projectId = project.getId();
            tag = dao.findTagByNameAndProjectId(tagName, projectId);
        }
        if (tagName != null && projectName == null) {
            tag = dao.findTagByNameAndProjectId(tagName, null);
        }
        List<MediaTag> tags = dao.findTagsByPerson(personId, tag);
        return SearchUtils.buildSearchResults(tags, page, limit);
    }

    
}
