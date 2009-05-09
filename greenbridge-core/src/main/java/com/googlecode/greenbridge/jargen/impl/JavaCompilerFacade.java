/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.jargen.impl;

import java.io.IOException;
import java.io.File;


/**
 *
 * @author ryan
 */
public class JavaCompilerFacade {

    public void compileDir(String packageName, File src, File dest) throws IOException, InterruptedException {

        String cmd = "javac -sourcepath " + src.toString() + " " + generatePath(packageName, src);
        Runtime.getRuntime().exec(cmd).waitFor();

    }


    protected String generatePath(String packageName, File src) {
        String[] packages = parsePackageNames(packageName);
        StringBuffer srcString = new StringBuffer(src.toString() + File.separator);
        for (int i = 0; i < packages.length; i++) {
            String string = packages[i];
            srcString.append(string + File.separator);
        }
        srcString.append("*.java");
        return srcString.toString();
    }

    protected String[] parsePackageNames(String packageName) {
        return packageName.split("\\.");
    }
}
