/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.web;

import java.io.File;
import java.io.InputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.Assert.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ryan
 */
public class StoreMediaStrategyImplTest {

    public StoreMediaStrategyImplTest() {
    }

    /**
     * Test of store method, of class StoreMediaStrategyImpl.
     */
    @Test
    public void testStore() throws Exception {
        System.out.println("store");
        InputStream data = getClass().getResourceAsStream("/appleChapter.xml");
        MockMultipartFile media = new MockMultipartFile("appleChapter.xml", "appleChapter.xml", "text/xml", data);
        
        File rootDir = new File("target/example");
        rootDir.mkdirs();
        StoreMediaStrategyImpl instance = new StoreMediaStrategyImpl(rootDir);
        String result = instance.store(media);

        String prefix = result.substring(0, 2);
        String uuid_prefix = result.substring(3, 5);
        assertEquals(prefix, uuid_prefix);





    }

}