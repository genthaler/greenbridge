package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;

public class MediaTagExtraInfo implements Serializable {

    private String prop;

    private String entry;

    private MediaTag mediaTag;


    public java.lang.String getProp() {
        return this.prop;
    }

    public void setProp(java.lang.String prop) {
        this.prop = prop;
    }

    public java.lang.String getEntry() {
        return this.entry;
    }

    public void setEntry(java.lang.String entry) {
        this.entry = entry;
    }

    public MediaTag getMediaTag() {
        return this.mediaTag;
    }

    public void setMediaTag(MediaTag mediaTag) {
        this.mediaTag = mediaTag;
    }

}
