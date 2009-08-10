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

/**
 *
 * @author ryan
 */
public class MockTagManager implements TagManager {

    private long currentId = 1;
    private HashMap<String,Tag> nameToTag = new HashMap<String,Tag>();
    private List<Project> projects = new ArrayList<Project>();


    public MockTagManager() {
        {
            Tag t = new Tag();
            t.setId(currentId++);
            t.setTagName("FollowUp");
            nameToTag.put(t.getTagName(), t);
        }
        {
            Tag t = new Tag();
            t.setId(currentId++);
            t.setTagName("ActionItem");
            nameToTag.put(t.getTagName(), t);
        }
        {
            Project p = new Project();
            p.setId(1L);
            p.setName("Project1");
            Set<Tag> tags = new HashSet<Tag>();
            p.setTags(tags);
            projects.add(p);

            Tag t = new Tag();
            t.setId(currentId++);
            t.setTagName("ActionItem");
            t.setTagGroup(p);
            tags.add(t);
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
            t.setId(currentId++);
            t.setTagName(name);
            nameToTag.put(name, t);
            return t;
        }
    }

    @Override
    public Tag findTagByName(String name, Long project_id) {
        if (nameToTag.containsKey(name)) {
            return nameToTag.get(name);
        }
        else {
            Tag t = new Tag();
            t.setId(currentId++);
            t.setTagName(name);
            nameToTag.put(name, t);
            return t;
        }
    }


    @Override
    public List<Tag> listAllGlobalTags() {
        List<Tag> list = new ArrayList<Tag>();
        for (Tag tag : nameToTag.values()) {
            if (tag.getTagGroup() == null) {
                list.add(tag);
            }
        }
        return list;
    }

    @Override
    public List<Project> listAllProjectsWithTags() {
        return projects;
    }

}
