/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.deployer.impl;

import java.io.File;
import org.rsquared.annob.deployer.Deployer;
import org.rsquared.annob.deployer.DeploymentInfo;

/**
 *
 * @author ryan
 */
public class MockDeployer implements Deployer{

    @Override
    public DeploymentInfo deployAsCurrentRequirements(File requirementsJar) {
        return new MockDeploymentInfo(requirementsJar);
    }

}
