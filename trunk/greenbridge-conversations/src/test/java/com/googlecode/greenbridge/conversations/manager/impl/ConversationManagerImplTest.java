/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.dao.Db4oConversationDao;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.manager.AppleChapterConversationDetails;
import com.googlecode.greenbridge.conversations.manager.ConversationSearchResults;
import com.googlecode.greenbridge.conversations.manager.FreemindConversationDetails;
import com.googlecode.greenbridge.conversations.manager.TagUpdateDetails;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring/applicationContext.xml")
public class ConversationManagerImplTest  implements ApplicationContextAware {

    private String testMediaURL = "32/23302-3232-3232-323.mp3";
    private String testFreemindURL = "66/666-66-6--6666-6.mm";
    private ApplicationContext context;

    public ConversationManagerImplTest() {
       // Db4oConversationDao instance = (Db4oConversationDao)context.getBean("conversationDao");

    }

    /**
     * Test of getDayAfter method, of class ConversationManagerImpl.
     */
    @Test
    public void testGetDayAfter() {
        System.out.println("getDayAfter");
        int day = 8;
        int month = 8;
        int year = 2009;
        ConversationManagerImpl imp = (ConversationManagerImpl)context.getBean("conversationManager");

        Calendar c = Calendar.getInstance();
        c.set(year, month, 9);
        Date expResult = c.getTime();
        Date result = imp.getDayAfter(day, month, year);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDayBefore() {
        System.out.println("getDayBefore");
        int day = 8;
        int month = 8;
        int year = 2009;
        ConversationManagerImpl imp = (ConversationManagerImpl)context.getBean("conversationManager");
        Calendar c = Calendar.getInstance();
        c.set(year, month, 7);
        Date expResult = c.getTime();
        Date result = imp.getDayBefore(day, month, year);
        assertEquals(expResult, result);
    }

    protected FreemindConversationDetails createBaseDetails() {

        FreemindConversationDetails cd = new FreemindConversationDetails();
        cd.setTagDuration(30);
        cd.setTagStartOffset(0);
        cd.setMediaUrl(testMediaURL);
        cd.setFreemindUrl(testFreemindURL);
        InputStream xml = getClass().getResourceAsStream("/TestMeeting.mm");
        cd.setFreemindXMLStream(xml);
        return cd;
    }

    @Test
    public void testNewFreemindConversation() throws Exception {


        ConversationManagerImpl imp = (ConversationManagerImpl)context.getBean("conversationManager");
       
        FreemindConversationDetails cd = createBaseDetails();
        Conversation c = imp.newConversation(cd);

        assertNotNull(c.getId());
        System.out.println("Saved conversation: " + c.getId());
        assertEquals("Test Meeting", c.getName());
        assertEquals("test-meeting", c.getSlug());
        assertNull(c.getCategory());

        Db4oConversationDao dao = (Db4oConversationDao)context.getBean("conversationDao");
        

        Conversation c2 = dao.loadConversationById(c.getId());
        Media media = c2.findFirstMedia();
        assertNotNull(media);
        assertEquals(testMediaURL, media.getUrl());



        dao.deleteConversation(c.getId());


    }

    @Test
    public void testAddTag() throws Exception {
        ConversationManagerImpl imp = (ConversationManagerImpl)context.getBean("conversationManager");

        FreemindConversationDetails cd = createBaseDetails();
        Conversation c = imp.newConversation(cd);
        TagUpdateDetails details = new TagUpdateDetails();
        details.setStartTime(10);
        details.setEndTime(20);

        imp.addTag(c.getId(), details);

        //check if the one in memory has changed
        List<MediaTag> tags = c.findFirstMedia().getMediaTags();

        assertEquals(3, tags.size());

        MediaTag added = tags.get(2);
        assertEquals("a-test-name", added.getTag().getTagName());
        assertNotNull(added.getId());
        
    }

    @Test
    public void listAllTest() throws Exception {
        ConversationManagerImpl imp = (ConversationManagerImpl)context.getBean("conversationManager");
        ConversationSearchResults results = imp.listAllConversations(1, 2);
        System.out.println("There are " + results.getConversations().size() + " of " + results.getTotalConversationInResults());

    }

    
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

}