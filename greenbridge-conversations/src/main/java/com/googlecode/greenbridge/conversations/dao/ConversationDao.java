/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.db4o.ObjectSet;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import org.springmodules.db4o.support.Db4oDaoSupport;

/**
 *
 * @author ryan
 */
public class ConversationDao extends Db4oDaoSupport {

    public Conversation load(long id) {
        Conversation c = new Conversation();
        c.setId(id);
        ObjectSet results =  getDb4oTemplate().get(c);
        return (Conversation) results.next();
    }

    public void save(Conversation c) {
        getDb4oTemplate().set(c, 4);
    }



}
