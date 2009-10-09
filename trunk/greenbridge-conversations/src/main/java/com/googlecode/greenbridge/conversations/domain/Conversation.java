package com.googlecode.greenbridge.conversations.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Conversation implements Serializable {


    private String id;
    private String slug;

    private String name;

    private String description;

    private String category;
    private Date startTime;
    private String freemindUrl;

    
    public Media findFirstMedia() {
        if (media != null && media.size() > 0) {
            return media.iterator().next();
        }
        else return null;
    }


    private List<Attendee> attendees = new ArrayList<Attendee>();

    private Set<Media> media = new HashSet<Media>();

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getCategory() {
        return this.category;
    }

    public void setCategory(java.lang.String category) {
        this.category = category;
    }

    public java.util.Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }



    public java.util.List<Attendee> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(java.util.List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public java.util.Set<Media> getMedia() {
        return this.media;
    }

    public void setMedia(java.util.Set<Media> media) {
        this.media = media;
    }


    /**
     * @return the freemindUrl
     */
    public String getFreemindUrl() {
        return freemindUrl;
    }

    /**
     * @param freemindUrl the freemindUrl to set
     */
    public void setFreemindUrl(String freemindUrl) {
        this.freemindUrl = freemindUrl;
    }

    /**
     * @return the slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug the slug to set
     */
    public void setSlug(String slug) {
        this.slug = slug;
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
