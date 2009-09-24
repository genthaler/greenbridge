/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.domain;

import java.util.List;

/**
 *
 * @author ryan
 */
public class ConversationTemplate {
    private String id;
    private String defaultProjectID;
    private List<Tag> tags;
    private String category;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the defaultProjectID
     */
    public String getDefaultProjectID() {
        return defaultProjectID;
    }

    /**
     * @param defaultProjectID the defaultProjectID to set
     */
    public void setDefaultProjectID(String defaultProjectID) {
        this.defaultProjectID = defaultProjectID;
    }
}
