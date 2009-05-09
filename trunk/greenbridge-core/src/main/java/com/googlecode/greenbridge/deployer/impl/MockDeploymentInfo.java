/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.deployer.impl;

import java.io.File;
import com.googlecode.greenbridge.deployer.DeploymentInfo;

/**
 *
 * @author ryan
 */
public class MockDeploymentInfo implements DeploymentInfo {

    private File file;

    public MockDeploymentInfo(File file) {
        this.file = file;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

}
