/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.domain;

import java.util.Date;

/**
 *
 * @author ryan
 */
public class Media {
    private String url;
    private String media;
    private Date startdate;
    private int mediaLength;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the media
     */
    public String getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(String media) {
        this.media = media;
    }

    /**
     * @return the startdate
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * @param startdate the startdate to set
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * @return the mediaLength
     */
    public int getMediaLength() {
        return mediaLength;
    }

    /**
     * @param mediaLength the mediaLength to set
     */
    public void setMediaLength(int mediaLength) {
        this.mediaLength = mediaLength;
    }
}
