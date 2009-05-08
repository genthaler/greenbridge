/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.deployer.mvn;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class MavenUtils {

    /**
     * Build the mvn command
     * @param groupId group id
     * @param artifactId artifact id
     * @param version version
     * @param url the url to the repo
     * @param file thr file (jar) to upload
     * @param description the description of the jar
     * @param repositoryId the repo id
     * @return a list of strings that can be run
     * @throws java.net.MalformedURLException
     * @throws java.net.URISyntaxException
     */
    public static List<String> buildCommands(String groupId, String artifactId, String version, String url, File file, String description, String repositoryId) throws MalformedURLException, URISyntaxException {
        java.util.List<String> cmd = new ArrayList<String>();
        cmd.add(findMavenExecutable());
        cmd.add("deploy:deploy-file");
        cmd.add("-DuniqueVersion=false");

        // required
        cmd.add("-DgroupId=" + groupId);
        cmd.add("-DartifactId=" + artifactId);
        cmd.add("-Dversion=" + version);
        cmd.add("-Dpackaging=jar" );
        cmd.add("-Dfile=" + file.getAbsolutePath());
        cmd.add("-Durl=" + url);



        // optional description
        
        if (description != null && description.trim().length() > 0) {
            cmd.add("-Ddescription=" + description);
        }

        // optional repo ID

        if (repositoryId != null && repositoryId.trim().length() > 0) {
            cmd.add("-DrepositoryId=" + repositoryId);
        }

        return cmd;
    }

    public static String findMavenExecutable() {
        String script = "mvn";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            script += ".bat";
        }
        for (String path : System.getenv("PATH").split(File.pathSeparator)) {
            File file = new File(path, script);
            if (file.exists() && file.isFile() && file.canRead()) {
                return file.getAbsolutePath();
            }
        }
        throw new IllegalStateException(String.format("Unable to find Maven executable '%s' in PATH: %s", script, System.getenv("PATH")));
    }
}
