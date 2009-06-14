/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import net.java.textilej.parser.Attributes;
import net.java.textilej.parser.DocumentBuilder.BlockType;
import net.java.textilej.parser.DocumentBuilder.SpanType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class TextileConfluenceDatatableParserTest {

    public TextileConfluenceDatatableParserTest() {
    }



    /**
     * Test of getDatatable method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testGetDatatable() {
        System.out.println("getDatatable");
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        Datatable expResult = null;
        Datatable result = instance.getDatatable();
        assertEquals(expResult, result);
    }






    /**
     * Test of beginSpan method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testBeginSpan() {
        System.out.println("beginSpan");
        SpanType type = SpanType.BOLD;
        Attributes attributes = new Attributes();
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.beginSpan(type, attributes);
    }

    /**
     * Test of endSpan method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testEndSpan() {
        System.out.println("endSpan");
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.endSpan();
    }

    /**
     * Test of beginHeading method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testBeginHeading() {
        System.out.println("beginHeading");
        int level = 0;
        Attributes attributes = new Attributes();
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.beginHeading(level, attributes);

    }

    /**
     * Test of endHeading method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testEndHeading() {
        System.out.println("endHeading");
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.endHeading();
    }

    /**
     * Test of entityReference method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testEntityReference() {
        System.out.println("entityReference");
        String entity = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.entityReference(entity);
    }

    /**
     * Test of image method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testImage() {
        System.out.println("image");
        Attributes attributes = null;
        String url = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.image(attributes, url);
    }

    /**
     * Test of link method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testLink() {
        System.out.println("link");
        Attributes attributes = null;
        String hrefOrHashName = "";
        String text = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.link(attributes, hrefOrHashName, text);
    }

    /**
     * Test of imageLink method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testImageLink() {
        System.out.println("imageLink");
        Attributes linkAttributes = null;
        Attributes imageAttributes = null;
        String href = "";
        String imageUrl = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.imageLink(linkAttributes, imageAttributes, href, imageUrl);
    }

    /**
     * Test of acronym method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testAcronym() {
        System.out.println("acronym");
        String text = "";
        String definition = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.acronym(text, definition);
    }



    /**
     * Test of charactersUnescaped method, of class TextileConfluenceDatatableParser.
     */
    @Test
    public void testCharactersUnescaped() {
        System.out.println("charactersUnescaped");
        String literal = "";
        TextileConfluenceDatatableParser instance = new TextileConfluenceDatatableParser();
        instance.charactersUnescaped(literal);
    }

}