/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class SluggerImplTest {

    public SluggerImplTest() {
    }

    /**
     * Test of generateSlug method, of class SluggerImpl.
     */
    @Test
    public void testGenerateSlug() {
        System.out.println("generateSlug");
        String text = "This is & not a # friendly";
        SluggerImpl instance = new SluggerImpl();
        String expResult = "this-is---not-a---friendly";
        String result = instance.generateSlug(text);
        assertEquals(expResult, result);

    }

}