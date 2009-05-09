/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.jargen.impl;

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
import com.googlecode.greenbridge.ScenarioNarrative;
import com.googlecode.greenbridge.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.impl.JavaLanguageSupport;

/**
 *
 * @author ryan
 */
public class JavaFileWriter {

    public static final String SCENARIO_CLASS_TEMPLATE = "ScenarioTemplate.ftl";
    public static final String SCENARIO_REF_TEMPLATE   = "ScenarioRef.ftl";
    public static final String STORY_CLASS_TEMPLATE    = "StoryTemplate.ftl";
    public static final String STORY_REF_TEMPLATE      = "StoryRef.ftl";
    private Configuration config;
    private File scenarioDirectory;
    private File storyDirectory;
    private File interfaceDirectory;
    private String packageName;

    public JavaFileWriter(String templatePath, File interfaceDirectory, File storyDirectory, File scenarioDirectory, String packageName) throws IOException {
        config = new Configuration();
        config.setClassForTemplateLoading(JavaFileWriter.class, templatePath);
        config.setObjectWrapper(new DefaultObjectWrapper());
        this.packageName = packageName;
        this.storyDirectory = storyDirectory;
        this.scenarioDirectory = scenarioDirectory;
        this.interfaceDirectory = interfaceDirectory;
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
        List<ScenarioNarrative> scenarios = buildScenarioList(stories);
        for (Iterator<ScenarioNarrative> it = scenarios.iterator(); it.hasNext();) {
            ScenarioNarrative scenarioNarrative = it.next();
            writeScenarioClass(scenarioNarrative);
            writtenCount++;
        }
        return writtenCount;
    }

    public void writeReferenceInterfaces() throws IOException {
        Map root = new HashMap();
        {
            Template t = config.getTemplate(SCENARIO_REF_TEMPLATE);
            File storyFile = new File(interfaceDirectory,  "ScenarioRef.java");
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
        {
            Template t = config.getTemplate(STORY_REF_TEMPLATE);
            File storyFile = new File(interfaceDirectory,  "StoryRef.java");
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



    }
    public File writeStoryClass(StoryNarrative story) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".stories");
        root.put("story", story);
        
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

    public File writeScenarioClass(ScenarioNarrative scenario) throws IOException {
        Map root = new HashMap();
        root.put("packageName", packageName + ".scenarios");
        root.put("scenario", scenario);
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


    protected List<ScenarioNarrative> buildScenarioList(List<StoryNarrative> stories) {
        List<ScenarioNarrative> scenarioNarratives = new ArrayList<ScenarioNarrative>();
        for (StoryNarrative storyNarrative : stories) {
            if (storyNarrative.getScenarios() != null) {
                scenarioNarratives.addAll(storyNarrative.getScenarios());
            }
        }
        return scenarioNarratives;
    }

}
