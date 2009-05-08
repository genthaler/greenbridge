/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester.impl;

import net.java.textilej.parser.Attributes;
import net.java.textilej.parser.DocumentBuilder.BlockType;
import net.java.textilej.parser.DocumentBuilder.SpanType;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.EventLoggingDocumentBuilder;
import net.java.textilej.parser.markup.confluence.ConfluenceDialect;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class TextileConfluenceContentParserTest {

    @Test
    public void testParsing() throws Exception {
        ConfluencePages pages = new ConfluencePages();
        String scenario_content = pages.getSampleScenarioPageContent();
        EventLoggingDocumentBuilder builder = new EventLoggingDocumentBuilder();
        MarkupParser parser = new MarkupParser(new ConfluenceDialect());
        parser.setBuilder(builder);

        parser.parse(scenario_content);
    }

}