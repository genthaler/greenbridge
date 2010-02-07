/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author ryan
 */
public class ConversationControllerImplTest extends TestCase {
    
    public ConversationControllerImplTest(String testName) {
        super(testName);
    }

    public void testFindServerURL() {
        String test = "http://localhost:5984/parking_tickets";
        ConversationControllerImpl impl = new ConversationControllerImpl(null, null, null,null);
        System.out.println("The test: " + test);

        String result = impl.findServerURL(test);
        System.out.println("The result: " + result);
        assertEquals("http://localhost:5984", result);
    }

    public void testFindDatabase() {
        String test = "http://localhost:5984/parking_tickets";
        ConversationControllerImpl impl = new ConversationControllerImpl(null, null, null, null);
        System.out.println("The test: " + test);

        String result = impl.findDB(test);
        System.out.println("The result: " + result);
        assertEquals("parking_tickets", result);
    }


    public void nottestUploadMedia() throws Exception {
        String server = "http://localhost:5984/conversations";
        File mp3 = new File("C://rtemp/TechLead-2009.mm.wav");
        ConversationControllerImpl impl = new ConversationControllerImpl(null, null, null, null);
        impl.uploadMedia(mp3, server);
    }

}
