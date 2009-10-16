/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfo;
import com.googlecode.greenbridge.conversations.domain.MediaTagExtraInfoPerson;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.impl.ConversationManagerImplTest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class Db4oConversationDaoTest implements ApplicationContextAware {

    private ApplicationContext context;

    public Db4oConversationDaoTest() {
    }


    
    @BeforeClass
    public static void clearDB() {
        ConversationManagerImplTest.clearAnyDB();
    }
    @AfterClass
    public static void clearFinishedDB() {
        ConversationManagerImplTest.clearAnyDB();
    }


    @Test
    public void testFindByID() throws Exception {
        String name = "A great meeting";
        String slug = "Test";
        String description = "A test of what we did last";
        String category = "TechLead";
        String freemindURL = "/ds/ddsds.mm";
        String UUID = "A-UUID-STRING";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        Date between = sdf.parse("02009.September.15 AD 04:25 PM");
        Db4oConversationDao instance = (Db4oConversationDao)context.getBean("conversationDao");

        Conversation c = new Conversation();
        c.setName(name);
        c.setSlug(slug);
        c.setStartTime(between);
        c.setDescription(description);
        c.setCategory(category);
        c.setFreemindUrl(freemindURL);
        c.setId(UUID);

        instance.save(c);
        assertNotNull(c.getId());

        Conversation result = instance.loadConversationById(UUID);
        assertEquals(c.getId(), result.getId());

        //clean upp
        instance.deleteConversation(c.getId());
    }


    /**
     * Test of loadBySlug method, of class Db4oConversationDao.
     */
    @Test
    public void testLoadBySlug() throws ParseException {

        String name = "A great meeting";
        String slug = "Test";
        String description = "A test of what we did last";
        String category = "TechLead";
        String freemindURL = "/ds/ddsds.mm";
        String UUID = "A-UUID-STRING";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");

        Date start   = sdf.parse("02009.September.15 AD 04:20 PM");
        Date between = sdf.parse("02009.September.15 AD 04:25 PM");
        Date end     = sdf.parse("02009.September.15 AD 04:40 PM");

        Db4oConversationDao instance = (Db4oConversationDao)context.getBean("conversationDao");

        Conversation c = new Conversation();
        c.setName(name);
        c.setSlug(slug);
        c.setStartTime(between);
        c.setDescription(description);
        c.setCategory(category);
        c.setFreemindUrl(freemindURL);
        c.setId(UUID);

        instance.save(c);
        assertNotNull(c.getId());
        System.out.println("The ID is " + c.getId());
        List<Conversation> result = instance.loadBetweenDatesAndContainsSlug(start, end, slug);
        assertEquals(1, result.size());
        System.out.println("There are " + result.size() + " results");
        for (Conversation conversation : result) {
            assertEquals(slug, conversation.getSlug());
            System.out.println("date of c: " + sdf.format(conversation.getStartTime()));
        }

        //clean upp
        instance.deleteConversation(c.getId());

    }

    @Test
    public void testFindTagsByPerson() {
        Db4oConversationDao instance = (Db4oConversationDao)context.getBean("conversationDao");

        MediaTag tag = new MediaTag();
        tag.setId("ABCD");
        List<MediaTagExtraInfo> list = new ArrayList<MediaTagExtraInfo>();
        tag.setMediaTagExtraInfos(list);
        MediaTagExtraInfoPerson person = new MediaTagExtraInfoPerson();
        person.setProp(MediaTagExtraInfoPerson.getPROPERTY_NAME());
        person.setEntry("3333");
        person.setMediaTag(tag);

        instance.saveMediaTag(tag);
        instance.saveMediaTagExtraInfo(person);

        List<MediaTag> results = instance.findTagsByPerson(person.getEntry(), null);
        assertEquals(1, results.size());

        instance.deleteMediaTagById(tag.getId());
        instance.deleteMediaTagExtraInfo(person);
    }

    @Test
    public void testFindTagsByPersonAndTag() {
        Db4oConversationDao instance = (Db4oConversationDao)context.getBean("conversationDao");

        Tag tag = new Tag();
        tag.setId("A");
        tag.setTagName("winner");
        instance.saveTag(tag);

            MediaTag mediaTag = new MediaTag();
            mediaTag.setId("ABCD");

            List<MediaTagExtraInfo> list = new ArrayList<MediaTagExtraInfo>();
            mediaTag.setMediaTagExtraInfos(list);
            MediaTagExtraInfoPerson person = new MediaTagExtraInfoPerson();
            person.setProp(MediaTagExtraInfoPerson.getPROPERTY_NAME());
            person.setEntry("3333");
            person.setMediaTag(mediaTag);
            instance.saveMediaTag(mediaTag);
            instance.saveMediaTagExtraInfo(person);


            MediaTag mediaTag2 = new MediaTag();
            mediaTag2.setId("EFGD");
            mediaTag2.setTag(tag);
            List<MediaTagExtraInfo> list2 = new ArrayList<MediaTagExtraInfo>();
            mediaTag2.setMediaTagExtraInfos(list2);
            MediaTagExtraInfoPerson person2 = new MediaTagExtraInfoPerson();
            person2.setProp(MediaTagExtraInfoPerson.getPROPERTY_NAME());
            person2.setEntry("3333");
            person2.setMediaTag(mediaTag2);
            instance.saveMediaTag(mediaTag2);
            instance.saveMediaTagExtraInfo(person2);



        List<MediaTag> results = instance.findTagsByPerson("3333", tag);
        assertEquals(1, results.size());
        assertEquals("EFGD", results.get(0).getId());

        instance.deleteTag(tag);
        instance.deleteMediaTagById("ABCD");
        instance.deleteMediaTagById("EFGD");
        instance.deleteMediaTagExtraInfo(person);
        instance.deleteMediaTagExtraInfo(person2);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

}