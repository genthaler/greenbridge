/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.dao;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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



    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

}