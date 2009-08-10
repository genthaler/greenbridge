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
    
}
