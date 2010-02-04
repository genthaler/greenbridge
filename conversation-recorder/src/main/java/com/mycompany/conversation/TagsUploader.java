/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import com.mycompany.conversation.domain.Conversation;

/**
 *
 * @author ryan
 */
public interface TagsUploader {
    public void upload(String server, String db, Conversation conversation);
}
