/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.domain.Project;
import java.util.List;

/**
 *
 * @author ryan
 */
public interface TagManager {

    
    public Tag findTagByName(String name, Long project_id);

    public Tag findTagByName(String name, String project);

    public List<Tag> listAllGlobalTags();

    public List<Project> listAllProjectsWithTags();
}
