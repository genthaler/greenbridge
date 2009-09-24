package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;

public class Tag implements Serializable {


    private String id;
    private String tagName;
    private String projectId;


    public java.lang.String getTagName() {
        return this.tagName;
    }

    public void setTagName(java.lang.String tagName) {
        this.tagName = tagName;
    }

    /**
     * @return the projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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


}
