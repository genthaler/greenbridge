/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.List;

/**
 *
 * @author ryan
 */
public interface ProjectManager {

    public void newProject(Project project);
    public void saveProject(Project project);
    List<Project> listAllProjects() throws Exception ;
    Project loadById(String id);
    Project loadByName(String name);
    List<Tag> findAllTagsForProject(String projectId);
    void addProjectTag(Tag tag);
    void saveProjectTag(Tag tag);
    void deleteProjectTag(String tagId, boolean cascade);

}
