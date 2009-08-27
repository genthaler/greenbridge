package com.googlecode.greenbridge.conversations.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


public class Conversation implements Serializable {


    private Long id;

    private String name;

    private String description;

    private String category;


    private Date startTime;

    
    public Media findFirstMedia() {
        if (media != null && media.size() > 0) {
            return media.iterator().next();
        }
        else return null;
    }


    private Set<Attendee> attendees = new HashSet<Attendee>();

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



    public java.util.Set<Attendee> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(java.util.Set<Attendee> attendees) {
        this.attendees = attendees;
    }

    public java.util.Set<Media> getMedia() {
        return this.media;
    }

    public void setMedia(java.util.Set<Media> media) {
        this.media = media;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }



}
