/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import java.util.Date;
import org.dom4j.Element;

/**
 *
 * @author ryan
 */
public interface FreemindNodeToMediaTagStrategy {

     public MediaTag createMediaTagFromNode(Element node, Date meetingStart, Long project_id, Integer tagStartOffset, Integer tagDuration) ;

}
