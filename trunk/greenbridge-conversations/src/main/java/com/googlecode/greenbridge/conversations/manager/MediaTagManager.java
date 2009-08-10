/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

/**
 *
 * @author ryan
 */
public interface MediaTagManager {


    public MediaTagSearchResults searchForGlobalTags(String tagName, Integer offset, Integer limit) throws Exception;
    public MediaTagSearchResults searchForGroupTags(String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception;
    public MediaTagSearchResults searchForConversationTags(long conversationId, String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception;
    public MediaTagSearchResults searchForAttendeeTags(long personId, String tagName, String tagGroupName, Integer offset, Integer limit) throws Exception;




}
