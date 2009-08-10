package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.validator.NotNull;

@Entity
public class MediaTag implements Serializable {

    @Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private Long id;


    private Long startTime;

    private Long endTime;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Tag tag;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mediaTag")
    private List<MediaTagExtraInfo> mediaTagExtraInfos = new ArrayList<MediaTagExtraInfo>();

    @NotNull
    @ManyToOne
    @JoinColumn
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
