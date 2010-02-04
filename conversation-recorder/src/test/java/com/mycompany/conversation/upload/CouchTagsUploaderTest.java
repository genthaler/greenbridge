/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.upload;

import com.mycompany.conversation.domain.Conversation;
import com.mycompany.conversation.domain.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import junit.framework.TestCase;
import net.sf.json.JSON;

/**
 *
 * @author ryan
 */
public class CouchTagsUploaderTest extends TestCase {

    CouchTagsUploader uploader;

    public CouchTagsUploaderTest(String testName) {
        super(testName);
        uploader = new CouchTagsUploader();
    }

    public void testConvertConversationOnNull() {
        Conversation c = null; // new Conversation();
        JSON j = uploader.convert(c);
        assertNull(j);
    }
    public void testConvertConversationOnNotNull() {
        Conversation c = new Conversation();
        c.setId("123");
        JSON j = uploader.convert(c);
        assertNotNull(j);
        String result = j.toString();
        assertTrue(result.contains(c.getId()));
        System.out.println(result);
        
    }

     public void testConvertTagOnNull() {
        Tag t = null; // new Conversation();
        Map j = uploader.convert(t);
        assertNull(j);
    }
     public void testConvertTagNotNull() {
        Conversation c = createConversation();
        Tag t = createTag(c);
        Map j = uploader.convert(t);
        assertNotNull(j);
        assertEquals(j.get("conversation"), t.getConversation().getId());
    }

    private Conversation createConversation() {
        Conversation c = new Conversation();
        c.setId("conversation" + System.currentTimeMillis());
        return c;
    }

    private Tag createTag(Conversation c) {
        Tag t =  new Tag();
        t.setConversation(c);
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(t);
        c.setTags(tags);
        t.setIcons(Arrays.asList("icon","icon2"));
        t.setStart(new Date());
        t.setDuration(25);
        t.setMediaOffset(10);
        t.setTag("green");
        return t;
    }


     public void testPostConversation() {
        Conversation c = new Conversation();
        c.setId("conversation" + System.currentTimeMillis());
        c.setDescription("Test");
        createTag(c);
        uploader.upload("http://localhost:5984", "parking_tickets", c);
        

     }



}
