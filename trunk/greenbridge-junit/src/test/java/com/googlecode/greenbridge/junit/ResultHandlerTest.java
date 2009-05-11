/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class ResultHandlerTest {

    public ResultHandlerTest() {
    }

    /**
     * Test of handleResults method, of class ResultHandler.
     */
    @Test
    public void testHandleResults() {
        File root = new File("target/greenbridge");
        root.mkdir();

        System.out.println("handleResults");
        StoryResults results = StoryResultsFactory.createMockResults();
        ResultHandler instance = new ResultHandler();
        File result = instance.setupOutputDir();
        instance.writeScenarioOutput(root, results);
        assertTrue(result.exists());   
    }
}
