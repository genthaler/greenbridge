/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.deployer.impl.itest;


import java.io.File;
import java.net.URL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.rsquared.annob.deployer.mvn.MavenDeployer;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class MavenDeployTest {

    public MavenDeployTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void shouldDeployToMaven() {
        MavenDeployer deployer = new MavenDeployer("com.test", "stories", "1.0-SNAPSHOT", "http://host:8081/nexus/content/repositories/inhouse.snapshot");
        deployer.setDescription("The story and scenarios for testing vol.");
        deployer.setRepositoryId("inhouse.snapshot");
        URL url = this.getClass().getResource("/specs-mock.jar");
        File toDeploy = new File(url.getFile());
        assertTrue(toDeploy.exists());
        assertTrue(toDeploy.isFile());
        deployer.deployAsCurrentRequirements(toDeploy);



    }

}