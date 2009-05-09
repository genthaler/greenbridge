/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.jargen.impl;

import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Jar;
/**
 *
 * @author ryan
 */
public class JarTask {
    public File generateZip(File src, File dest) {
        Project p = new Project();
        p.init();
        Jar zip = new Jar();
        zip.setProject(p);
        zip.setBasedir(src);
        zip.setDestFile(dest);
        zip.execute();
        return dest;
    }
}
