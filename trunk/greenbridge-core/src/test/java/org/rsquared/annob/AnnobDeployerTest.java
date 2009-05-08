/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rsquared.annob.deployer.impl.MockDeployer;
import org.rsquared.annob.deployer.impl.MockDeploymentInfo;
import org.rsquared.annob.jargen.impl.MockJarGen;
import org.rsquared.annob.storyharvester.impl.MockStoryHarvester;

/**
 *
 * @author ryan
 */
public class AnnobDeployerTest {

    public AnnobDeployerTest() {
    }

    /**
     * Test of runDeployment method, of class AnnobDeployer.
     */
    @Test
    public void testRunDeployment() throws Exception {
        System.out.println("runDeployment");

        MockStoryHarvester harvester = new MockStoryHarvester(false);
        File mockJar = new File("src/test/resources/specs-mock.jar");
        MockJarGen jarGen = new MockJarGen(mockJar);

        MockDeployer deployer = new MockDeployer();

        AnnobDeployer instance = new AnnobDeployer(harvester, jarGen, deployer);

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

        AnnobDeployer instance = new AnnobDeployer(harvester2, jarGen, deployer);
        instance.runDeployment();
    }
}