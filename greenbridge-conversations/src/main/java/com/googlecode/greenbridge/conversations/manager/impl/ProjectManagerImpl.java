/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.ProjectManager;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ryan
 */
public class ProjectManagerImpl implements ProjectManager {

    private ConversationDao dao;


    public ProjectManagerImpl(ConversationDao dao) {
        this.dao = dao;
    }



    @Override
    public void newProject(Project project) {
        project.setId(generateUUID());
        dao.saveProject(project);
    }

    @Override
    public void saveProject(Project project) {
        project.setId(generateUUID());
        dao.saveProject(project);
    }


    @Override
    public List<Project> listAllProjects() throws Exception {
        return dao.findAllProjects();
    }




    private String generateUUID() {
       return UUID.randomUUID().toString();
   }

    @Override
    public Project loadById(String id) {
        return dao.findProjectById(id);
    }

    @Override
    public Project loadByName(String name) {
        return dao.findProjectByName(name);
    }

    @Override
    public List<Tag> findAllTagsForProject(String projectId) {
        return dao.findTagsByProject(projectId);
    }

    @Override
    public void addProjectTag(Tag tag) {
        Tag exists = dao.findTagByNameAndProjectId(tag.getTagName(), tag.getProjectId());
        if (exists != null) throw new RuntimeException("The tag already exists");
        if (tag.getId() == null) {
            tag.setId(generateUUID());
        }
        dao.saveTag(tag);
    }
    
    @Override
    public void saveProjectTag(Tag tag) { 
        Tag current = dao.findTagById(tag.getId());
        if (current == null) throw new RuntimeException("The tag does not exist.");
        current.setTagName(tag.getTagName());
        current.setProjectId(tag.getProjectId());
        dao.saveTag(current);
    }

    @Override
    public void deleteProjectTag(String tagId, boolean cascade) {
        Tag current = dao.findTagById(tagId);
        if (current == null) throw new RuntimeException("The tag does not exist.");
        dao.deleteTag(current);
    }




}
