package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.hibernate.validator.NotNull;

@Entity
public class MediaTagExtraInfo implements Serializable {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private java.lang.Long id;

    private String prop;

    private String entry;

    @NotNull
    @ManyToOne
    @JoinColumn
    private MediaTag mediaTag;

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }
    public java.lang.String getProp() {
        return this.prop;
    }

    public void setProp(java.lang.String prop) {
        this.prop = prop;
    }

    public java.lang.String getEntry() {
        return this.entry;
    }

    public void setEntry(java.lang.String entry) {
        this.entry = entry;
    }

    public MediaTag getMediaTag() {
        return this.mediaTag;
    }

    public void setMediaTag(MediaTag mediaTag) {
        this.mediaTag = mediaTag;
    }

}