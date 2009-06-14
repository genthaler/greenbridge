/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import hudson.util.XStream2;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ryan
 */
public class FileBasedLastRunStorage implements LastRunStorage {


    private static final String PAGE_ID_FILENAME  = "last-run-page-ids";
    private static final String LAST_RUN_FILENAME = "last-run-date";
    private File root;

    public FileBasedLastRunStorage(File rootFile) {
        this.root = rootFile;
    }


    public Date getLastRunDate() throws Exception {
        File in_file = new File(root, LAST_RUN_FILENAME);
        if (!in_file.exists()) {
            // we assume it does not exist because it is the first run.
            return null;
        }
        FileReader reader = new FileReader(in_file);
        XStream2 xe = new XStream2();
        return ( Date ) xe.fromXML(reader);
    }


    public void setLastRunDate(Date lastRun) throws Exception {
        File out_file = new File(root, LAST_RUN_FILENAME);
        FileWriter writer = new FileWriter(out_file);
        XStream2 xe = new XStream2();
        xe.toXML(lastRun, writer);
    }

    @Override
    public Set<String> getLastRunPageIds() throws Exception {
        File in_file = new File(root, PAGE_ID_FILENAME);
        if (!in_file.exists()) {
            // we assume it does not exist because it is the first run
            return null;
        }
        FileReader reader = new FileReader(in_file);
        XStream2 xe = new XStream2();
        return ( Set<String>) xe.fromXML(reader);
    }

    @Override
    public void setLastRunPageIds(Set<String> pageIds)  throws Exception {
        File out_file = new File(root, PAGE_ID_FILENAME);
        FileWriter writer = new FileWriter(out_file);
        XStream2 xe = new XStream2();
        xe.toXML(pageIds, writer);
    }

}
