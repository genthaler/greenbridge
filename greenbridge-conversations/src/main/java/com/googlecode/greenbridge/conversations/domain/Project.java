package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public class Project implements Serializable {

    private java.lang.Long id;


    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    private String name;

    private Set<Tag> tags = new HashSet<Tag>();

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.util.Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(java.util.Set<Tag> tags) {
        this.tags = tags;
    }
}
