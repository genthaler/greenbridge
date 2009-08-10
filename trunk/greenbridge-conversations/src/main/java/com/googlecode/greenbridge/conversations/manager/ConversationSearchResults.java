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
public class ConversationSearchResults {
    private List<ConversationSummary> conversations = new ArrayList<ConversationSummary>();
    private int totalConversationInResults;

    /**
     * @return the conversations
     */
    public List<ConversationSummary> getConversations() {
        return conversations;
    }

    /**
     * @param conversations the conversations to set
     */
    public void setConversations(List<ConversationSummary> conversations) {
        this.conversations = conversations;
    }

    /**
     * @return the totalConversationInResults
     */
    public int getTotalConversationInResults() {
        return totalConversationInResults;
    }

    /**
     * @param totalConversationInResults the totalConversationInResults to set
     */
    public void setTotalConversationInResults(int totalConversationInResults) {
        this.totalConversationInResults = totalConversationInResults;
    }
}
