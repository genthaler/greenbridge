/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import com.googlecode.greenbridge.junit.report.ModuleReport;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.googlecode.greenbridge.util.JavaLanguageSupport;
import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.ScenarioNarrative;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author ryan
 */
public class ReportFileWriter {

    public static final String REPORT_TEMPLATE = "report.ftl";

    private Configuration config;
    private File reportDirectory;


        public ReportFileWriter(String templatePath, File reportDirectory) throws IOException {
        config = new Configuration();
        config.setClassForTemplateLoading(ReportFileWriter.class, templatePath);
        config.setObjectWrapper(new DefaultObjectWrapper());
        this.reportDirectory = reportDirectory;

    }


    public void writeReport(ModuleReport module) throws IOException {

        Map root = new HashMap();
        root.put("module", module);

        Template t = config.getTemplate(REPORT_TEMPLATE);
        File reportFile = new File(reportDirectory, "report.html");
        FileWriter writer = new FileWriter(reportFile);
        try {
            t.process(root, writer);
            createSupportingResource();
        } catch (Exception ex) {
            Logger.getLogger(ReportFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        writer.flush();
        writer.close();
        
    }


    public void createSupportingResource() throws IOException {
        File scripts = new File(reportDirectory, "scripts");
        scripts.mkdirs();
        copyFromClassPath("/scripts/jquery.min.js");
        copyFromClassPath("/scripts/style.css");
        copyFromClassPath("/scripts/jquery.autocomplete.css");
        copyFromClassPath("/scripts/jquery.parsequery.js");
        copyFromClassPath("/scripts/jquery-jtemplates.js");
        copyFromClassPath("/scripts/jquery.corner.js");
        copyFromClassPath("/scripts/jquery.autocomplete.min.js");

        File images = new File(reportDirectory, "images");
        images.mkdirs();
        copyFromClassPath("/images/logo.png");
        copyFromClassPath("/images/external.png");
    }


    private void copyFromClassPath(String classpathSrc) throws IOException {
        InputStream res = getClass().getResourceAsStream(classpathSrc);

        
        File dest = new File(reportDirectory, classpathSrc);
        CopyUtils.copy(res, new FileOutputStream(dest));
    }

}
