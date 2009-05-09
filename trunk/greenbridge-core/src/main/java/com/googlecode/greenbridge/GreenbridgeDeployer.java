/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.io.File;
import java.util.List;
import com.googlecode.greenbridge.deployer.Deployer;
import com.googlecode.greenbridge.deployer.DeploymentInfo;
import com.googlecode.greenbridge.jargen.JarGen;
import com.googlecode.greenbridge.storyharvester.StoryHarvester;

/**
 *
 * @author ryan
 */
public class GreenbridgeDeployer {
    private StoryHarvester storyHarvester;
    private JarGen jarGenerator;
    private Deployer deployer;


    public GreenbridgeDeployer(StoryHarvester storyHarvester, JarGen jarGenerator, Deployer deployer) {
        this.storyHarvester = storyHarvester;
        this.jarGenerator = jarGenerator;
        this.deployer = deployer;
    }


    public DeploymentInfo runDeployment() throws FailedDeploymentException {
        try {
            List<StoryNarrative> stories = storyHarvester.gather();
            File jarFile = jarGenerator.generateRequrementsJar(stories);
            return deployer.deployAsCurrentRequirements(jarFile);
        } catch (Exception e) {
            throw new FailedDeploymentException(e);
        }
    }
}
