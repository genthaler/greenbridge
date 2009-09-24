package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Media implements Serializable {


    private String url;

    private Date startTime;

    private Long mediaLength;

    private Integer mediaType;

    private Conversation conversation;


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
