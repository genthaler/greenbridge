/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class TagTest {

    public TagTest() {
    }




    /**
     * Test of setTagName method, of class Tag.
     */
    @Test
    public void testSetTagName() {
        System.out.println("setTagName");
        String tagName = "Alpha-1";
        Tag instance = new Tag();
        instance.setTagName(tagName);
        assertEquals("Alpha-1", instance.getTagName());
    }

}