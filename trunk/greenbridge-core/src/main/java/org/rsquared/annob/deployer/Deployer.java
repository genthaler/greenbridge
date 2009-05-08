/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.deployer;

import java.io.File;


/**
 *
 * @author ryan
 */
public interface Deployer {
    DeploymentInfo deployAsCurrentRequirements(File requirementsJar);
}
