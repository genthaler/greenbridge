/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.util.Date;
import org.jfree.date.DateUtilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class ConfluenceChangeCheckTest {


    public static final String ROOT_PAGE_NAME = "ROOT";
    public static final String ROOT_PAGE_ID =   "R1";

    


    MockRunStorage runStorage;

    public ConfluenceChangeCheckTest() {
        runStorage = new MockRunStorage();
    }



    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testWhenTheRootChanges() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start(previousDate));

        Date changeDate = DateUtilities.createDate(2009, 06, 01, 00, 10);

        confluence.setModifiedDate(ROOT_PAGE_ID, changeDate);
        
        assertTrue(instance.start(previousDate));

    }

    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testWhenAChildChanges() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start(previousDate));

        Date changeDate = DateUtilities.createDate(2009, 06, 01, 00, 10);

        confluence.setModifiedDate("11", changeDate);

        assertTrue(instance.start( previousDate));

    }


    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testNoChanges() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start(previousDate));

        previousDate = DateUtilities.createDate(2009, 06, 01, 00, 10);
        assertFalse(instance.start(previousDate));
        previousDate = DateUtilities.createDate(2009, 06, 01, 00, 20);
        assertFalse(instance.start(previousDate));
    }

    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testAChangeAndASubsequentNoChange() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start( previousDate));

        Date changeDate = DateUtilities.createDate(2009, 06, 01, 00, 10);

        confluence.setModifiedDate("11", changeDate);

        Date current = DateUtilities.createDate(2009, 06, 01, 00, 20);
        assertTrue(instance.start( previousDate));
        previousDate = current;

        assertFalse(instance.start( previousDate));


    }

    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testChildDeleted() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start( previousDate));

        confluence.deletePage("11");

        assertTrue(instance.start(previousDate));

    }
    
    /**
     * Test of start method, of class ConfluenceChangeCheck.
     */
    @Test
    public void testChildAdded() throws Exception {
        Date previousDate    = DateUtilities.createDate(2009, 06, 01, 00, 00);
        MockConfluence confluence = new MockConfluence(previousDate);
        ConfluenceChangeCheck instance = new ConfluenceChangeCheck(confluence, runStorage, "", ROOT_PAGE_NAME);
        assertFalse(instance.start( previousDate));

        Date added = DateUtilities.createDate(2009, 06, 01, 00, 10);
        confluence.addPageToFirstChildren("C1", "44", "1", added);

        assertTrue(instance.start(previousDate));

    }

}