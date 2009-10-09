/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.List;
import java.util.Map;


/**
 *
 * @author ryan
 */
public interface ConversationManager {

    public Conversation findById(String conversationID);
    public Conversation findConversation(int day, int month, int year, String slug);
    public Conversation newConversation(AppleChapterConversationDetails conversationDetails) throws Exception;
    public Conversation newConversation(FreemindConversationDetails conversationDetails) throws Exception;
    public ConversationSearchResults listAllConversations(Integer offset, Integer limit) throws Exception;
    public Long addTag(String conversationId, TagUpdateDetails details);
    public void updateTag(String conversationId, String mediaTagId, TagUpdateDetails details);
    public void deleteTag(String mediaTagId);
    public TagUpdateDetails loadTag(String conversationId, String mediaTagId);
    public Map<String,String> findAllTagsByLike(String prefix);
    public Map<String,String> findAllTags();


}
