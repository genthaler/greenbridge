package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.validator.NotNull;

@Entity
public class Project implements Serializable {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private java.lang.Long id;


    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagGroup")
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
