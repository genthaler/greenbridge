package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaTag implements Serializable {


    private String id;

    private Date date;

    private Long startTime;

    private Long endTime;

    private String shortDescription;

    private Tag tag;

    private List<MediaTagExtraInfo> mediaTagExtraInfos = new ArrayList<MediaTagExtraInfo>();

    private Media media;



    public java.lang.Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(java.lang.Long startTime) {
        this.startTime = startTime;
    }

    public java.lang.Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.lang.Long endTime) {
        this.endTime = endTime;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public java.util.List<MediaTagExtraInfo> getMediaTagExtraInfos() {
        return this.mediaTagExtraInfos;
    }

    public void setMediaTagExtraInfos(java.util.List<MediaTagExtraInfo> mediaTagExtraInfos) {
        this.mediaTagExtraInfos = mediaTagExtraInfos;
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
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

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
