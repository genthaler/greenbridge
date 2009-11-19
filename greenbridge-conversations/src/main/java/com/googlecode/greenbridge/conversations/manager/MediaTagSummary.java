/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.util.Date;

/**
 *
 * @author ryan
 */
public class MediaTagSummary {
    private String mediaTagId;
    private String mediaTagName;
    private String mediaUrl;
    private Date mediaTagCalculatedTimestamp;
    private String conversationId;
    private String conversationName;
    private long conversationTotalLength;
    private long tagStartTime;
    private long tagEndTime;
    private String shortDescription;

    /**
     * @return the mediaTagId
     */
    public String getMediaTagId() {
        return mediaTagId;
    }

    /**
     * @param mediaTagId the mediaTagId to set
     */
    public void setMediaTagId(String mediaTagId) {
        this.mediaTagId = mediaTagId;
    }

    /**
     * @return the mediaTagName
     */
    public String getMediaTagName() {
        return mediaTagName;
    }

    /**
     * @param mediaTagName the mediaTagName to set
     */
    public void setMediaTagName(String mediaTagName) {
        this.mediaTagName = mediaTagName;
    }

    /**
     * @return the mediaTagCalculatedTimestamp
     */
    public Date getMediaTagCalculatedTimestamp() {
        return mediaTagCalculatedTimestamp;
    }

    /**
     * @param mediaTagCalculatedTimestamp the mediaTagCalculatedTimestamp to set
     */
    public void setMediaTagCalculatedTimestamp(Date mediaTagCalculatedTimestamp) {
        this.mediaTagCalculatedTimestamp = mediaTagCalculatedTimestamp;
    }

    /**
     * @return the conversationId
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * @param conversationId the conversationId to set
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * @return the conversationName
     */
    public String getConversationName() {
        return conversationName;
    }

    /**
     * @param conversationName the conversationName to set
     */
    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    /**
     * @return the conversationTotalLength
     */
    public long getConversationTotalLength() {
        return conversationTotalLength;
    }

    /**
     * @param conversationTotalLength the conversationTotalLength to set
     */
    public void setConversationTotalLength(long conversationTotalLength) {
        this.conversationTotalLength = conversationTotalLength;
    }

    /**
     * @return the tagStartTime
     */
    public long getTagStartTime() {
        return tagStartTime;
    }

    /**
     * @param tagStartTime the tagStartTime to set
     */
    public void setTagStartTime(long tagStartTime) {
        this.tagStartTime = tagStartTime;
    }

    /**
     * @return the tagEndTime
     */
    public long getTagEndTime() {
        return tagEndTime;
    }

    /**
     * @param tagEndTime the tagEndTime to set
     */
    public void setTagEndTime(long tagEndTime) {
        this.tagEndTime = tagEndTime;
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
     * @return the shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription the shortDescription to set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}
