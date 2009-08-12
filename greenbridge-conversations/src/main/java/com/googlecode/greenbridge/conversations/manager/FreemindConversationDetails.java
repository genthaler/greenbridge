/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.io.InputStream;

/**
 *
 * @author ryan
 */
public class FreemindConversationDetails {
    private Integer tagStartOffset;
    private Integer tagDuration;
    private InputStream freemindXMLStream;
    private String mediaUrl;

    /**
     * @return the freemindXMLStream
     */
    public InputStream getFreemindXMLStream() {
        return freemindXMLStream;
    }

    /**
     * @param freemindXMLStream the freemindXMLStream to set
     */
    public void setFreemindXMLStream(InputStream freemindXMLStream) {
        this.freemindXMLStream = freemindXMLStream;
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
     * @return the tagStartOffset
     */
    public Integer getTagStartOffset() {
        return tagStartOffset;
    }

    /**
     * @param tagStartOffset the tagStartOffset to set
     */
    public void setTagStartOffset(Integer tagStartOffset) {
        this.tagStartOffset = tagStartOffset;
    }

    /**
     * @return the tagDuration
     */
    public Integer getTagDuration() {
        return tagDuration;
    }

    /**
     * @param tagDuration the tagDuration to set
     */
    public void setTagDuration(Integer tagDuration) {
        this.tagDuration = tagDuration;
    }
    
}
