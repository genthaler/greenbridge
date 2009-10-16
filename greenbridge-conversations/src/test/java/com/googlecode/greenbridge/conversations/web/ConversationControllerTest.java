/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.web;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class ConversationControllerTest {

    public ConversationControllerTest() {
    }



    @Test
    public void testGetMP3length() throws Exception {
        Long answer = 72l;
        File f = new File("src/test/resources/Test2.mm.wav.mp3");

        ConversationController controller = new ConversationController(null, null, null);

        Long result = controller.getMP3length(f);
        assertEquals(answer, result);



    }



}