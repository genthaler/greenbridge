/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class MediaTagSearchResults {
    private List<MediaTagSummary> mediaTags = new ArrayList<MediaTagSummary>();
    private int totalMediaTagsInResults;



    /**
     * @return the mediaTags
     */
    public List<MediaTagSummary> getMediaTags() {
        return mediaTags;
    }

    /**
     * @param mediaTags the mediaTags to set
     */
    public void setMediaTags(List<MediaTagSummary> mediaTags) {
        this.mediaTags = mediaTags;
    }

    /**
     * @return the totalMediaTagsInResults
     */
    public int getTotalMediaTagsInResults() {
        return totalMediaTagsInResults;
    }

    /**
     * @param totalMediaTagsInResults the totalMediaTagsInResults to set
     */
    public void setTotalMediaTagsInResults(int totalMediaTagsInResults) {
        this.totalMediaTagsInResults = totalMediaTagsInResults;
    }

}
