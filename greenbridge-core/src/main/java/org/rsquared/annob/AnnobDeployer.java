/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob;

import java.io.File;
import java.util.List;
import org.rsquared.annob.deployer.Deployer;
import org.rsquared.annob.deployer.DeploymentInfo;
import org.rsquared.annob.jargen.JarGen;
import org.rsquared.annob.storyharvester.StoryHarvester;

/**
 *
 * @author ryan
 */
public class AnnobDeployer {
    private StoryHarvester storyHarvester;
    private JarGen jarGenerator;
    private Deployer deployer;


    public AnnobDeployer(StoryHarvester storyHarvester, JarGen jarGenerator, Deployer deployer) {
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
