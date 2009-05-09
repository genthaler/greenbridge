/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.deployer;

import java.io.File;


/**
 *
 * @author ryan
 */
public interface Deployer {
    DeploymentInfo deployAsCurrentRequirements(File requirementsJar);
}
