/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.io.File;
import java.io.FilenameFilter;


/**
 *
 * @author ryan
 */
public class ReportDataHarvester {


    public Report harvestData(String reportSourceDir) {
        File dir = reportDirFile(reportSourceDir);
        File[] files = findFiles(dir);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
        }
        return null;
    }

    

    


    private File[] findFiles(File dir) {
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".xml")) return true;
                return false;
            }
        });
    }

    private File reportDirFile(String reportSourceDir) {
        File file = new File(reportSourceDir);
        assert(file.isFile());
        return file;
    }

}
