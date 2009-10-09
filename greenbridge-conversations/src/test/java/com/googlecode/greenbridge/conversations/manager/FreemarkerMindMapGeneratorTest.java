/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class FreemarkerMindMapGeneratorTest {

    public FreemarkerMindMapGeneratorTest() {
    }


    /**
     * Test of generateMindmap method, of class FreemarkerMindMapGenerator.
     */
    @Test
    public void testGenerateMindmap() throws IOException {

        MindMapParams params = new MindMapParams();
        params.setTags( new ArrayList<Tag>() );

        {
            Tag tag = new Tag();
            tag.setTagName("test");
            params.getTags().add(tag);
        }
        {
            Tag tag = new Tag();
            tag.setTagName("test [project2]");
            params.getTags().add(tag);
        }
        params.setProjectName("tech-lead");
        params.setMeetingName("meeting 1");


        params.setPeople( new ArrayList<Person>());
        {
            Person p = new Person();
            p.setEmail("ryan.ramage@someplace.com");
            params.getPeople().add(p);
        }
        {
            Person p = new Person();
            p.setEmail("testdude@someplace.com");
            params.getPeople().add(p);
        }




        File file = new File("target/freemindTest.mm");
        FileWriter writer = new FileWriter(file);
        FreemarkerMindMapGenerator instance = new FreemarkerMindMapGenerator();
        instance.generateMindmap(params, writer);

    }

}