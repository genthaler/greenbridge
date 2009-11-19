/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class JSONReportGeneratorTest {

    public JSONReportGeneratorTest() {
    }


    @Test
    public void testGenerateReport() throws Exception {
        JSONReportGenerator reportGen = new JSONReportGenerator();
        Report report = ReportDataSamples.loadDataset1();
        File root = new File("target");
        reportGen.generateReport(report, root);

        File index_json = new File(root, "index.json");
        assertTrue(index_json.isFile());
    }



}