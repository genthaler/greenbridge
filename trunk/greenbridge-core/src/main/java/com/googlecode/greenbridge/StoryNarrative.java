/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.util.List;

/**
 *
 * @author ryan
 */
public class StoryNarrative {
    private String id;
    private int version;
    private List<String> storyNarrative;
    private List<ScenarioNarrative> scenarios;
    private String linkUrl;
    private String linkName;







    /**
     * @return the scenarios
     */
    public List<ScenarioNarrative> getScenarios() {
        return scenarios;
    }

    /**
     * @param scenarios the scenarios to set
     */
    public void setScenarios(List<ScenarioNarrative> scenarios) {
        this.scenarios = scenarios;
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
     * @return the linkUrl
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * @param linkUrl the linkUrl to set
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /**
     * @return the linkName
     */
    public String getLinkName() {
        return linkName;
    }

    /**
     * @param linkName the linkName to set
     */
    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    /**
     * @return the storyNarrative
     */
    public List<String> getStoryNarrative() {
        return storyNarrative;
    }

    /**
     * @param storyNarrative the storyNarrative to set
     */
    public void setStoryNarrative(List<String> storyNarrative) {
        this.storyNarrative = storyNarrative;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
