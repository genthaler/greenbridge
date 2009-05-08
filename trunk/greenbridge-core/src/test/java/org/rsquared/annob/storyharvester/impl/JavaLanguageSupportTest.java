/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester.impl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class JavaLanguageSupportTest {

    public JavaLanguageSupportTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of makeJavaIdentifier method, of class JavaLanguageSupport.
     */
    @Test
    public void testMakeJavaIdentifier() {
        JavaLanguageSupport supprt = new JavaLanguageSupport();
        System.out.println("makeJavaIdentifier");
        String identifier = "some.text";
        String expResult = "some_text";
        String result = JavaLanguageSupport.makeJavaIdentifier(identifier);
        assertEquals(expResult, result);
    }

    @Test
    public void shouldFixJavaKeyword() {
        System.out.println("makeJavaIdentifier");
        String identifier = "while";
        String expResult = "while_";
        String result = JavaLanguageSupport.makeJavaIdentifier(identifier);
        assertEquals(expResult, result);
    }
}