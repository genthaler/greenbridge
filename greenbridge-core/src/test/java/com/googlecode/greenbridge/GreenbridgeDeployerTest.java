/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import com.googlecode.greenbridge.deployer.impl.MockDeployer;
import com.googlecode.greenbridge.deployer.impl.MockDeploymentInfo;
import com.googlecode.greenbridge.jargen.impl.MockJarGen;
import com.googlecode.greenbridge.storyharvester.impl.MockStoryHarvester;

/**
 *
 * @author ryan
 */
public class GreenbridgeDeployerTest {

    public GreenbridgeDeployerTest() {
    }

    /**
     * Test of runDeployment method, of class GreenbridgeDeployer.
     */
    @Test
    public void testRunDeployment() throws Exception {
        System.out.println("runDeployment");

        MockStoryHarvester harvester = new MockStoryHarvester(false);
        File mockJar = new File("src/test/resources/specs-mock.jar");
        MockJarGen jarGen = new MockJarGen(mockJar);

        MockDeployer deployer = new MockDeployer();

        GreenbridgeDeployer instance = new GreenbridgeDeployer(harvester, jarGen, deployer);

        MockDeploymentInfo result = (MockDeploymentInfo)instance.runDeployment();
        assertEquals(mockJar, result.getFile());
    }


    @Test(expected=FailedDeploymentException.class)
    public void testRunDeploymentThatFails() throws Exception {
        System.out.println(" fail Deployment");

        MockStoryHarvester harvester2 = new MockStoryHarvester(true);
        File mockJar = new File("src/test/resources/specs-mock.jar");
        MockJarGen jarGen = new MockJarGen(mockJar);

        MockDeployer deployer = new MockDeployer();

        GreenbridgeDeployer instance = new GreenbridgeDeployer(harvester2, jarGen, deployer);
        instance.runDeployment();
    }
}