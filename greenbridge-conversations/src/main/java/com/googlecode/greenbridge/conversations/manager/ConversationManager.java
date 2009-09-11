/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Conversation;


/**
 *
 * @author ryan
 */
public interface ConversationManager {

    public Conversation findConversation(Long id);
    public Conversation newConversation(AppleChapterConversationDetails conversationDetails) throws Exception;
    public Conversation newConversation(FreemindConversationDetails conversationDetails) throws Exception;
    public ConversationSearchResults listAllConversations(Integer offset, Integer limit) throws Exception;
    public Long addTag(Long conversationId, TagUpdateDetails details);
    public void updateTag(Long conversationId, Long tagId, TagUpdateDetails details);
    public void deleteTag(Long tagId);
    public TagUpdateDetails loadTag(long conversationId, long tagId);


}
