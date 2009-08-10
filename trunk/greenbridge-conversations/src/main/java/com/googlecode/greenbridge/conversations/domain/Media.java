package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

@Entity
public class Media implements Serializable {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private java.lang.Long id;


    @NotNull
    private String url;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    private Long mediaLength;

    private Integer mediaType;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Conversation conversation;

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "media")
    private List<MediaTag> mediaTags = new ArrayList<MediaTag>();

    public java.lang.String getUrl() {
        return this.url;
    }

    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    public java.util.Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.lang.Long getMediaLength() {
        return this.mediaLength;
    }

    public void setMediaLength(java.lang.Long mediaLength) {
        this.mediaLength = mediaLength;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public java.util.List<MediaTag> getMediaTags() {
        return this.mediaTags;
    }

    public void setMediaTags(java.util.List<MediaTag> mediaTags) {
        this.mediaTags = mediaTags;
    }

    /**
     * @return the mediaType
     */
    public Integer getMediaType() {
        return mediaType;
    }

    /**
     * @param mediaType the mediaType to set
     */
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }


}
