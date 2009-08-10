/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;

/**
 *
 * @author ryan
 */
public interface FullConversationTagParsingStratgey extends XmlTagParserStrategy {

    List<String> getAttendeeList(Document doc);

    Date getMeetingEnd(Document doc) throws ParseException;

    String getMeetingName(Document doc);

    String getProject(Document doc);

    Date getMeetingStart(Document doc) throws ParseException;

}
