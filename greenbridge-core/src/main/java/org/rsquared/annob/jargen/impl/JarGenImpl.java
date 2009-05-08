/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.jargen.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rsquared.annob.StoryNarrative;
import org.rsquared.annob.jargen.JarGen;

/**
 *
 * @author ryan
 */
public class JarGenImpl implements JarGen{
    private File tempDir;
    private String packageName;
    private JavaFileWriter writer;
    private File src;
    private File scenarioFolder;
    private File storyFolder;
    private File interfaceFolder;

    /**
     * Constructor.
     * @param templatePath the freemarker template root
     * @param tempDir the directory we can use to write and store the jar
     * @param packageName the package name used to prefix all generated classes
     */
    public JarGenImpl(String templatePath, File tempDir, String packageName) throws IOException {
        this.tempDir = tempDir;
        this.packageName = packageName;
        createFileStructure();
        writer = new JavaFileWriter(templatePath, interfaceFolder, storyFolder, scenarioFolder, packageName);


    }

    protected File createFileStructure() {
        src = new File(tempDir, "src");
        File current = src;
        mkdirOnlyIfNotExists(current);

        String[] annoDir = new String[] {"org", "rsquared", "annob", "annotation"};
        for (int i = 0; i < annoDir.length; i++) {
            String thisPackageName = annoDir[i];
            current = new File(current, thisPackageName);
            mkdirOnlyIfNotExists(current);
        }
        interfaceFolder = current;
        current = src;

        String[] packageNames = parsePackageNames();
        for (int i = 0; i < packageNames.length; i++) {
            String thisPackageName = packageNames[i];
            current = new File(current, thisPackageName);
            mkdirOnlyIfNotExists(current);
        }
        scenarioFolder = new File(current, "scenarios");
        mkdirOnlyIfNotExists(scenarioFolder);
        cleanFolder(scenarioFolder);
        storyFolder = new File(current, "stories");
        mkdirOnlyIfNotExists(storyFolder);
        cleanFolder(storyFolder);




        return current;
    }


    protected void cleanFolder(File folder) {
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) file.delete();
        }
    }




    protected void mkdirOnlyIfNotExists(File file) {
        if (!file.exists()) {
            file.mkdir();
        }
    }

    protected String[] parsePackageNames() {
        return packageName.split("\\.");
    }

    @Override
    public File generateRequrementsJar(List<StoryNarrative> stories) throws IOException {
        writer.writeScenarioClasses(stories);
        writer.writeStoryClasses(stories);
        writer.writeReferenceInterfaces();
        
        JavaCompilerFacade compiler = new JavaCompilerFacade();
        try {
            compiler.compileDir("org.rsquared.annob.annotation", src, src);
            compiler.compileDir(packageName + ".scenarios", src, src);
            compiler.compileDir(packageName + ".stories", src, src);
            JarTask jarTask = new JarTask();
            File jarFile = new File(tempDir, "specs-" + timestamp() + ".jar");
            jarFile.createNewFile();
            return jarTask.generateZip(src, jarFile);
        } catch (InterruptedException ex) {
            Logger.getLogger(JarGenImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        
        
    }

    public String timestamp() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        return format.format(new Date());
    }

}
