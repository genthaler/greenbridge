/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.SlugGenerator;
import com.googlecode.greenbridge.conversations.manager.TagManager;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ryan
 */
public class TagManagerImpl implements TagManager {

    private ConversationDao dao;
    private SlugGenerator slugGenerator;


   private String generateUUID() {
       return UUID.randomUUID().toString();
   }

    @Override
    public Tag findTagByName(String name, String templateID) {
        return dao.findTagByName(name, templateID);
    }


    @Override
    public Tag findTagByNameOrCreate(String name, String templateID) {
        String slug = getSlugGenerator().generateSlug(name);

        try {
            Tag t =  dao.findTagByName(slug, templateID);
            if (t  != null ) return t;
        } catch (Exception e) {}
        // create a tag
        Tag t = new Tag();
        t.setTagName(slug);
        t.setId(generateUUID());

        ConversationTemplate ct = null;
        if (templateID != null) {
            ct = dao.findTemplateByID(templateID);
            t.setProjectId(ct.getDefaultProjectID());
        }
        dao.saveTag(t);


        return t;
    }


    @Override
    public List<Tag> listAllGlobalTags() {
        return dao.findAllGlobalTags();
    }

    @Override
    public List<Project> listAllProjectsWithTags() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the dao
     */
    public ConversationDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(ConversationDao dao) {
        this.dao = dao;
    }

    /**
     * @return the slugGenerator
     */
    public SlugGenerator getSlugGenerator() {
        return slugGenerator;
    }

    /**
     * @param slugGenerator the slugGenerator to set
     */
    public void setSlugGenerator(SlugGenerator slugGenerator) {
        this.slugGenerator = slugGenerator;
    }


}
