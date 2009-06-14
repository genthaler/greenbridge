/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;


import com.googlecode.greenbridge.storyharvester.impl.Datatable;
import com.googlecode.greenbridge.storyharvester.impl.TextileConfluenceContentParseStrategy;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class TextileConfluenceContentParseStrategyTest {

    String content;
    String scenario_content;

    public TextileConfluenceContentParseStrategyTest() {
    }

    @Before
    public  void setUp() throws Exception {
        ConfluencePages pages = new ConfluencePages();
        content = pages.getSampleStoryPageContent();
        scenario_content = pages.getSampleScenarioPageContent();
    }


    /**
     * Test of getDatatable method, of class TextileConfluenceContentParseStrategy.
     */
    @Test
    public void testGetDatatable() {
        System.out.println("getDatatable");
        TextileConfluenceContentParseStrategy instance = new TextileConfluenceContentParseStrategy();
        Datatable table = instance.getDatatable(scenario_content);
        assertNotNull(table);
        assertEquals("balance", table.getDatatableProperties().get(0));
        assertEquals("deposit", table.getDatatableProperties().get(1));
        assertEquals("newBalance", table.getDatatableProperties().get(2));

        assertEquals("0", table.getDatatable().get(0).get("balance"));
        assertEquals("100", table.getDatatable().get(0).get("deposit"));
        assertEquals("100", table.getDatatable().get(0).get("newBalance"));

        assertEquals("100", table.getDatatable().get(1).get("balance"));
        assertEquals("50",  table.getDatatable().get(1).get("deposit"));
        assertEquals("150", table.getDatatable().get(1).get("newBalance"));

    }

}