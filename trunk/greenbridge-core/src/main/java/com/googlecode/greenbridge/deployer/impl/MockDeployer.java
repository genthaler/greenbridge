/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.deployer.impl;

import java.io.File;
import com.googlecode.greenbridge.deployer.Deployer;
import com.googlecode.greenbridge.deployer.DeploymentInfo;

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
