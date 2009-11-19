/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.io.File;

/**
 *
 * @author ryan
 */
public interface ReportGenerator {

    public void generateReport(Report report, File destination) throws Exception;
}
