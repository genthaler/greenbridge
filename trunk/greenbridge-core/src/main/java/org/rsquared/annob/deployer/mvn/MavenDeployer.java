/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.deployer.mvn;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.rsquared.annob.deployer.Deployer;
import org.rsquared.annob.deployer.DeploymentInfo;

/**
 *
 * @author ryan
 */
public class MavenDeployer implements Deployer {

    private String groupId;
    private String artifactId;
    private String version;
    private String url;
    private String repositoryId;
    private String description;
    

    public MavenDeployer(String groupId, String artifactId, String version, String url) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.url = url;
    }

    @Override
    public DeploymentInfo deployAsCurrentRequirements(File requirementsJar) {
        try {
            List<String> cmd = MavenUtils.buildCommands(groupId, artifactId, version, url, requirementsJar, getDescription(), getRepositoryId());
            for (String string : cmd) {
                System.out.print(string + " ");
            }
            FileOutputStream log = new FileOutputStream("out.log");
            ProcessBuilder builder = new ProcessBuilder(cmd)
                    .redirectErrorStream(true);
            builder.environment().putAll(System.getenv());
            Process p = builder.start();
            IOUtils.copy(p.getInputStream(), log);
            p.waitFor();
        } catch (Exception ex) {
            Logger.getLogger(MavenDeployer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the repositoryId
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * @param repositoryId the repositoryId to set
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
