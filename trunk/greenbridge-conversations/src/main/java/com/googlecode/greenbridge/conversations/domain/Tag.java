package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.hibernate.validator.NotNull;

@Entity
public class Tag implements Serializable {


    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private java.lang.Long id;

    @NotNull
    private String tagName;

    private String tagClass;


    @ManyToOne
    @JoinColumn
    private Project tagGroup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tag")
    private List<MediaTag> mediaTags = new ArrayList<MediaTag>();

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getTagName() {
        return this.tagName;
    }

    public void setTagName(java.lang.String tagName) {
        assert(tagName.equals(URLEncoder.encode(tagName)));
        this.tagName = tagName;
    }

    public Project getTagGroup() {
        return this.tagGroup;
    }

    public void setTagGroup(Project tagGroup) {
        this.tagGroup = tagGroup;
    }

    public java.util.List<MediaTag> getMediaTags() {
        return this.mediaTags;
    }

    public void setMediaTags(java.util.List<MediaTag> mediaTags) {
        this.mediaTags = mediaTags;
    }

    /**
     * @return the tagClass
     */
    public String getTagClass() {
        return tagClass;
    }

    /**
     * @param tagClass the tagClass to set
     */
    public void setTagClass(String tagClass) {
        this.tagClass = tagClass;
    }

}
