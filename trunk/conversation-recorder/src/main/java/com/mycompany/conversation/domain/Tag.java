/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public class Tag {
    private String tag;
    private int mediaOffset;
    private int duration;
    private Date start;
    private Conversation conversation;
    private String description;
    private List<Attendee> attendees;
    private List<String> icons;

    /**
     * @return the mediaOffset
     */
    public int getMediaOffset() {
        return mediaOffset;
    }

    /**
     * @param mediaOffset the mediaOffset to set
     */
    public void setMediaOffset(int mediaOffset) {
        this.mediaOffset = mediaOffset;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the conversation
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * @param conversation the conversation to set
     */
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the attendees
     */
    public List<Attendee> getAttendees() {
        return attendees;
    }

    /**
     * @param attendees the attendees to set
     */
    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    /**
     * @return the icons
     */
    public List<String> getIcons() {
        return icons;
    }

    /**
     * @param icons the icons to set
     */
    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

}
