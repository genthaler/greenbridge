/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.manager.MediaTagSearchResults;
import com.googlecode.greenbridge.conversations.manager.MediaTagSummary;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public class SearchUtils {

    private SearchUtils() {};

    public static MediaTagSearchResults buildSearchResults(List<MediaTag> tags, Integer page, Integer limit)throws Exception {
        MediaTagSearchResults results = new MediaTagSearchResults();
        List<MediaTagSummary> tagSummary = new ArrayList<MediaTagSummary>();
        results.setMediaTags(tagSummary);
        for (MediaTag mediaTag : tags) {
            MediaTagSummary summary = new MediaTagSummary();
            summary.setConversationId(mediaTag.getMedia().getConversation().getId());
            summary.setConversationName(mediaTag.getMedia().getConversation().getName());

            Date conversationStart = mediaTag.getMedia().getConversation().getStartTime();
            Date tagDate = new Date(conversationStart.getTime() + (mediaTag.getStartTime() * 1000) );

            summary.setMediaTagCalculatedTimestamp(tagDate);
            summary.setShortDescription(mediaTag.getShortDescription());
            //summary.setConversationTotalLength(1000);
            summary.setMediaTagId(mediaTag.getId());
            summary.setMediaTagName(mediaTag.getTag().getTagName());
            summary.setMediaUrl(mediaTag.getMedia().getUrl());
            summary.setTagStartTime(mediaTag.getStartTime());
            summary.setTagEndTime(mediaTag.getEndTime());
            tagSummary.add(summary);

        }
        results.setTotalMediaTagsInResults(tags.size());
        return results;
    }

}
