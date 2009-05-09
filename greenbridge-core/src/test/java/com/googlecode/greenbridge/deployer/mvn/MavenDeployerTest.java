/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.deployer.mvn;

import com.googlecode.greenbridge.deployer.DeploymentInfo;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class MavenDeployerTest {

    public MavenDeployerTest() {
    }

    @Test
    public void testBeanStuff() {
        String groupId = "a";
        String artifactId = "b";
        String version = "c";
        String url = "d";
        String description = "e";
        String repoId = "f";
        MavenDeployer deployer = new MavenDeployer(groupId, artifactId, version, url);
        deployer.setDescription(description);
        assertEquals(description, deployer.getDescription());
        deployer.setRepositoryId(repoId);
        assertEquals(repoId, deployer.getRepositoryId());
    }

    /**
     * Test of deployAsCurrentRequirements method, of class MavenDeployer.
     */
    @Test
    public void testDeployAsCurrentRequirements() {
        System.out.println("deployAsCurrentRequirements");
        File requirementsJar = null;
        MavenDeployer instance = null;
        DeploymentInfo expResult = null;
        //DeploymentInfo result = instance.deployAsCurrentRequirements(requirementsJar);

    }





}