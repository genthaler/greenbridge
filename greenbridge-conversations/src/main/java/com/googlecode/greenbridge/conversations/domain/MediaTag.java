package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MediaTag implements Serializable {

    private Long id;


    private Long startTime;

    private Long endTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
