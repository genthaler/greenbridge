/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.MediaTag;
import java.io.InputStream;
import java.util.List;
import org.dom4j.Document;

/**
 *
 * @author ryan
 */
public interface XmlTagParserStrategy {

    public Document parseDocument(InputStream stream) throws Exception;

    /**
     *
     * @param doc
     * @param project_id the project id that the tags should be under if a name resolution has to occur.
     * @return
     * @throws java.lang.Exception
     */
    public List<MediaTag> getTags(Document doc, String template_id, Integer tagStartOffset, Integer tagDuration) throws Exception;

}
