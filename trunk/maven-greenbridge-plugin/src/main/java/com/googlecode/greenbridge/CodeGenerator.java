/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 *
 * @author ryan
 */
public class CodeGenerator {
    private String srcDir;
    private String packageName;
    private JavaFileWriter writer;
    private File src;
    private File scenarioFolder;
    private File storyFolder;

    /**
     * Constructor.
     * @param templatePath the freemarker template root
     * @param tempDir the directory we can use to write and store the jar
     * @param packageName the package name used to prefix all generated classes
     */
    public CodeGenerator(String templatePath, String srcDir, String packageName,String projectArtifactId, String projectGroupId, String projectVersion) throws IOException {
        this.srcDir = srcDir;
        this.packageName = packageName;
        createFileStructure();
        writer = new JavaFileWriter(templatePath,  storyFolder, scenarioFolder, packageName, projectArtifactId, projectGroupId, projectVersion);
    }

    protected File createFileStructure() {
        src = new File(srcDir);
        File current = src;
        mkdirOnlyIfNotExists(current);

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
            file.mkdirs();
        }
    }

    protected String[] parsePackageNames() {
        return packageName.split("\\.");
    }


    public void generateRequrementsJavaCode(List<StoryNarrative> stories) throws IOException {
        writer.writeScenarioClasses(stories);
        writer.writeStoryClasses(stories);
        writer.writeModuleClass(stories);
    }

    public String timestamp() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        return format.format(new Date());
    }

}
