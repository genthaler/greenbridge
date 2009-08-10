/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public class AppleChapterConversationDetails {

    private Date conversationDate;
    private String name;
    private String description;
    private InputStream appleChapterXMLStream;
    private String mediaUrl;
    private Long project_id;
    private List<Integer> attendeePersonIDs = new ArrayList();

    /**
     * @return the conversationDate
     */
    public Date getConversationDate() {
        return conversationDate;
    }

    /**
     * @param conversationDate the conversationDate to set
     */
    public void setConversationDate(Date conversationDate) {
        this.conversationDate = conversationDate;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the appleChapterXMLStream
     */
    public InputStream getAppleChapterXMLStream() {
        return appleChapterXMLStream;
    }

    /**
     * @param appleChapterXMLStream the appleChapterXMLStream to set
     */
    public void setAppleChapterXMLStream(InputStream appleChapterXMLStream) {
        this.appleChapterXMLStream = appleChapterXMLStream;
    }

    /**
     * @return the mediaUrl
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * @param mediaUrl the mediaUrl to set
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * @return the attendeePersonIDs
     */
    public List<Integer> getAttendeePersonIDs() {
        return attendeePersonIDs;
    }

    /**
     * @param attendeePersonIDs the attendeePersonIDs to set
     */
    public void setAttendeePersonIDs(List<Integer> attendeePersonIDs) {
        this.attendeePersonIDs = attendeePersonIDs;
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
     * @return the project_id
     */
    public Long getProject_id() {
        return project_id;
    }

    /**
     * @param project_id the project_id to set
     */
    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }
    


}
