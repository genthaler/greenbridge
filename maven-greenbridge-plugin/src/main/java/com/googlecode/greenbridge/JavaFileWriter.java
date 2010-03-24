/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

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
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryan
 */
public class JavaFileWriter {

    public static final String STORY_MODULE_TEMPLATE = "StoryModuleTemplate.ftl";

    public static final String ABSTRACT_SCENARIO_CLASS_TEMPLATE = "AbstractScenarioTemplate.ftl";
    public static final String SCENARIO_CLASS_TEMPLATE = "ScenarioTemplate.ftl";
    public static final String SCENARIO_REF_TEMPLATE   = "ScenarioRef.ftl";
    public static final String STORY_CLASS_TEMPLATE    = "StoryTemplate.ftl";
    public static final String STORY_REF_TEMPLATE      = "StoryRef.ftl";
    private Configuration config;
    private File scenarioDirectory;
    private File storyDirectory;
    private String packageName;
    private String projectArtifactId;
    private String projectGroupId;
    private String projectVersion;
    private String moduleName;

    public JavaFileWriter(String templatePath, File storyDirectory, File scenarioDirectory, String packageName, String projectArtifactId, String projectGroupId, String projectVersion) throws IOException {
        config = new Configuration();
        config.setClassForTemplateLoading(JavaFileWriter.class, templatePath);
        config.setObjectWrapper(new DefaultObjectWrapper());
        this.packageName = packageName;
        this.storyDirectory = storyDirectory;
        this.scenarioDirectory = scenarioDirectory;
        this.projectArtifactId = projectArtifactId;
        this.projectGroupId = projectGroupId;
        this.projectVersion = projectVersion;
        moduleName = JavaLanguageSupport.makeJavaIdentifier(projectArtifactId);
        moduleName = StringUtils.capitalize(moduleName);
    }


    public void writeModuleClass(List<StoryNarrative> stories) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".stories");

        if (scenarioDirectory.list().length > 0) {
            root.put("scenariopackageName", packageName + ".scenarios");
        }
        root.put("storyNarratives", stories);
        
        root.put("moduleName", moduleName);
        root.put("storyPackageName", projectGroupId + "-" + projectArtifactId);
        root.put("storyPackageVersion", projectVersion);

        Template t = config.getTemplate(STORY_MODULE_TEMPLATE);
        File storyFile = new File(storyDirectory, moduleName + ".java");
        FileWriter writer = new FileWriter(storyFile);
        try {
            t.process(root, writer);
        } catch (TemplateException ex) {
            Logger.getLogger(JavaFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        writer.flush();
        writer.close();
        
    }


    public int writeStoryClasses(List<StoryNarrative> stories) throws IOException {
        int writtenCount = 0;
        for (Iterator<StoryNarrative> it = stories.iterator(); it.hasNext();) {
            StoryNarrative storyNarrative = it.next();
            writeStoryClass(storyNarrative);
        }
        return writtenCount;
    }


    public int writeScenarioClasses(List<StoryNarrative> stories) throws IOException {
        int writtenCount = 0;
        for (StoryNarrative storyNarrative : stories) {
            if (storyNarrative.getScenarios() != null) {
                for (ScenarioNarrative scenarioNarrative : storyNarrative.getScenarios()) {
                    writeScenarioAbstractClass(scenarioNarrative);
                    writeScenarioClass(storyNarrative, scenarioNarrative);
                    writtenCount++;
                }
            }
        }
        return writtenCount;
    }


    public File writeStoryClass(StoryNarrative story) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".stories");
        root.put("story", story);
        root.put("storyModule", moduleName);
        
        Template t = config.getTemplate(STORY_CLASS_TEMPLATE);
        File storyFile = new File(storyDirectory, story.getId() + "_" + story.getVersion() + ".java");
        FileWriter writer = new FileWriter(storyFile);
        try {
            t.process(root, writer);
        } catch (TemplateException ex) {
            Logger.getLogger(JavaFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        writer.flush();
        writer.close();
        return storyFile;
    }

    public File writeScenarioAbstractClass(ScenarioNarrative scenario) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".scenarios");
        root.put("scenario", scenario);
        root.put("methods", convertNarrativeToMethodNames(scenario));
        Template t = config.getTemplate(ABSTRACT_SCENARIO_CLASS_TEMPLATE);
        File storyFile = new File(scenarioDirectory, scenario.getId()  +  ".java");
        FileWriter writer = new FileWriter(storyFile);
        try {
            t.process(root, writer);
        } catch (TemplateException ex) {
            Logger.getLogger(JavaFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        writer.flush();
        writer.close();
        return storyFile;
    }

    public File writeScenarioClass(StoryNarrative story, ScenarioNarrative scenario) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".scenarios");
        root.put("storiesPackageName", packageName + ".stories");
        root.put("scenario", scenario);
        root.put("story", story.getId() + "_" + story.getVersion());
        root.put("methods", convertNarrativeToMethodNames(scenario));
        Template t = config.getTemplate(SCENARIO_CLASS_TEMPLATE);
        File storyFile = new File(scenarioDirectory, scenario.getId()  + "_" + scenario.getVersion() +  ".java");
        FileWriter writer = new FileWriter(storyFile);
        try {
            t.process(root, writer);
        } catch (TemplateException ex) {
            Logger.getLogger(JavaFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException(ex);
        }
        writer.flush();
        writer.close();
        return storyFile;
    }

    public List<String> convertNarrativeToMethodNames(ScenarioNarrative scenario) {
        List<String> methods = new ArrayList<String>();
        for (Iterator<String> it = scenario.getNarrative().iterator(); it.hasNext();) {
            String line = it.next();
            String method = JavaLanguageSupport.makeJavaIdentifier(line);
            methods.add(method);
        }
        return methods;
    }


}
