/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.util.Date;

/**
 *
 * @author ryan
 */
public class TagDetails {
    private Date tagDate;
    private String tag;
    private String description;

    /**
     * @return the tagDate
     */
    public Date getTagDate() {
        return tagDate;
    }

    /**
     * @param tagDate the tagDate to set
     */
    public void setTagDate(Date tagDate) {
        this.tagDate = tagDate;
    }

    /**
     * @return the name
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param name the name to set
     */
    public void setTag(String tag) {
        this.tag = tag;
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
}
