/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class TagUpdateDetails {
    private String name;
    private double startTime;
    private double endTime;
    private String shortDescription;
    private String longDescription;
    private List<String> icons;
    private Map<String,String> extraAttributes;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the startTime
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
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

    /**
     * @return the longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @param longDescription the longDescription to set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * @return the icons
     */
    public List<String> getIcons() {
        return icons;
    }

    /**
     * @param icons the icons to set
     */
    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    /**
     * @return the extraAttributes
     */
    public Map<String, String> getExtraAttributes() {
        return extraAttributes;
    }

    /**
     * @param extraAttributes the extraAttributes to set
     */
    public void setExtraAttributes(Map<String, String> extraAttributes) {
        this.extraAttributes = extraAttributes;
    }
}
