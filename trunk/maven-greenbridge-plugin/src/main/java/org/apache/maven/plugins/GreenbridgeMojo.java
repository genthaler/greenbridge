package org.apache.maven.plugins;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.googlecode.greenbridge.CodeGenerator;
import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.StoryHarvester;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.swizzle.confluence.Confluence;

/**
 * Goal which touches a timestamp file.
 *
 * @goal stories
 * @phase generate-sources
 */
public class GreenbridgeMojo
    extends AbstractMojo
{


    private Log log;


    /**
     * @parameter expression="${project.artifactId}"
     */
    private String projectArtifactId;

    /**
     * @parameter expression="${project.groupId}"
     */
    private String projectGroupId;

     /**
     * @parameter expression="${project.version}"
     */
    private String projectVersion;


    /**
     * @parameter expression="${project.compileSourceRoots}"
     */
    private List roots;

    /**
     * The Story harvester
     * @parameter
     * @required
     */
    private StoryHarvester storyHarvester;


    /**
     * The java package name
     * @parameter
     * @required
     */
    private String packageName;


    // need someway to disable this if offline?
    public void execute()
        throws MojoExecutionException
    {
        log = getLog();
        try {


            log.info("Gathering stories for: " + generateStoryPackage());
            List<StoryNarrative> stories = storyHarvester.gather();
            log.info("Stories Retrieved: " + stories.size());
            setStoryPackage(stories);

            String srcDir = (String)roots.get(0);
            CodeGenerator generator = new CodeGenerator("/", srcDir, packageName);
            generator.generateRequrementsJavaCode(stories);
        } catch (Exception ex) {
            Logger.getLogger(GreenbridgeMojo.class.getName()).log(Level.SEVERE, null, ex);
            throw new MojoExecutionException("", ex);
        }
    }

    private void setStoryPackage(List<StoryNarrative> stories) {
        for (StoryNarrative storyNarrative : stories) {
            storyNarrative.setStoryPackage(generateStoryPackage());
        }
    }


    private String generateStoryPackage() {
        return projectGroupId + ":" + projectArtifactId + ":" + projectVersion;
    }




}
