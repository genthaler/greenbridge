/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.manager.*;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author ryan
 */
public class MockTagManager implements TagManager {


    private HashMap<String,Tag> nameToTag = new HashMap<String,Tag>();
    


    public MockTagManager() {
        {
            Tag t = new Tag();
            t.setId(UUID.randomUUID().toString());
            t.setTagName("FollowUp");
            nameToTag.put(t.getTagName(), t);
        }
        {
            Tag t = new Tag();
            t.setId(UUID.randomUUID().toString());
            t.setTagName("ActionItem");
            nameToTag.put(t.getTagName(), t);
        }
        {

            Tag t = new Tag();
            t.setId(UUID.randomUUID().toString());
            t.setTagName("ActionItem");
            //t.setTagGroup(p);
            nameToTag.put(t.getTagName(), t);
        }
    }



    @Override
    public Tag findTagByName(String name, String project) {
        if (nameToTag.containsKey(name)) {
            return nameToTag.get(name);
        }
        else {
            Tag t = new Tag();
            t.setId(UUID.randomUUID().toString());
            t.setTagName(name);
            nameToTag.put(name, t);
            return t;
        }
    }




    @Override
    public List<Tag> listAllGlobalTags() {
        List<Tag> list = new ArrayList<Tag>();
        for (Tag tag : nameToTag.values()) {
            //if (tag.getTagGroup() == null) {
            //    list.add(tag);
            //}
        }
        return list;
    }

    @Override
    public List<Project> listAllProjectsWithTags() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Tag findTagByNameOrCreate(String name, String templateID) {
       return findTagByName(name, templateID);
    }

}
