/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.io.File;
import java.util.List;
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

        System.out.println("handleResults");
        StoryResults results = StoryResultsFactory.createMockResults();
        ResultHandler instance = new ResultHandler();
        File root = instance.setupScenarioOutputDir();
        List<File> files = instance.writeScenarioOutput(root, results);
        assertEquals(1, files.size());
    }
}
