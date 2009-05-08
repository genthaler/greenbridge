/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.rsquared.annob.annotation.Scenario;
import static org.junit.Assert.*;
import org.rsquared.annob.storyharvester.StoryHarvester;

/**
 *
 * @author ryan
 */
public class CommandlineRunnerTest {

    public CommandlineRunnerTest() {
    }

    /**
     * Test of main method, of class CommandlineRunner.
     */
    
    @Test 
    public void testMain() {
        System.out.println("main");
        String[] args = new String[] {
            "http://fg5664/rpc/xmlrpc",
            "confluence_application",
            "password",
            "~ryan_ramage",
            "My New App",
            "com.test",
            "com.test",
            "test2-functional",
            "1.0-SNAPSHOT",
            "http://host:8081/nexus/content/repositories/inhouse.snapshot"
        };
        CommandlineRunner.main(args);
        
    }

}