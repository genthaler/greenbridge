/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.deployer.mvn;

import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class MavenUtilsTest {

    public MavenUtilsTest() {
    }

    /**
     * Test of buildCommands method, of class MavenUtils.
     */
    @Test
    public void testBuildCommands() throws Exception {
        System.out.println("buildCommands");
        String groupId = "a";
        String artifactId = "b";
        String version = "c";
        String url = "d";
        File file = new File("target");
        String description = "e";
        String repositoryId = "d";
        List<String> result = MavenUtils.buildCommands(groupId, artifactId, version, url, file, description, repositoryId);
        assertTrue(result.get(0).contains("mvn"));
        assertTrue(result.get(1).contains("deploy:deploy-file"));
        assertTrue(result.get(2).contains("-DuniqueVersion=false"));
        
    }

    /**
     * Test of findMavenExecutable method, of class MavenUtils.
     */
    @Test
    public void testFindMavenExecutable() {
        System.out.println("findMavenExecutable");
        String result = MavenUtils.findMavenExecutable();
        assertTrue(result.contains("mvn"));
    }

}