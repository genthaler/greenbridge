/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class ScenarioNarrative {

    private String id;
    private String linkUrl;
    private String linkName;
    private StoryNarrative storyNarrative;
	private List<String> narrative;
    private List<Map<String,String>> datatable;
    private List<String> datatableProperties;
    private int version;


	public ScenarioNarrative(String... narrative) {
		this.narrative = Arrays.asList(narrative);
	}

    public ScenarioNarrative(List<String> narrative) {
        this.narrative = narrative;
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
     * @return the storyNarrative
     */
    public StoryNarrative getStoryNarrative() {
        return storyNarrative;
    }

    /**
     * @param storyNarrative the storyNarrative to set
     */
    public void setStoryNarrative(StoryNarrative storyNarrative) {
        this.storyNarrative = storyNarrative;
    }

    /**
     * @return the narrative
     */
    public List<String> getNarrative() {
        return narrative;
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
     * @return the datatable
     */
    public List<Map<String, String>> getDatatable() {
        return datatable;
    }

    /**
     * @param datatable the datatable to set
     */
    public void setDatatable(List<Map<String, String>> datatable) {
        this.datatable = datatable;
    }

    /**
     * @return the datatableProperties
     */
    public List<String> getDatatableProperties() {
        return datatableProperties;
    }

    /**
     * @param datatableProperties the datatableProperties to set
     */
    public void setDatatableProperties(List<String> datatableProperties) {
        this.datatableProperties = datatableProperties;
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
