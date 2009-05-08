/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.swizzle.confluence.Confluence;
import org.rsquared.annob.deployer.DeploymentInfo;
import org.rsquared.annob.deployer.mvn.MavenDeployer;
import org.rsquared.annob.jargen.JarGen;
import org.rsquared.annob.jargen.impl.JarGenImpl;
import org.rsquared.annob.storyharvester.StoryHarvester;
import org.rsquared.annob.storyharvester.impl.ConfluenceAdaptor;
import org.rsquared.annob.storyharvester.impl.ConfluenceContentParseStrategy;
import org.rsquared.annob.storyharvester.impl.ConfluenceStoryHarvester;
import org.rsquared.annob.storyharvester.impl.DefaultConfluenceContentParseStrategy;
import org.rsquared.annob.storyharvester.impl.TextileConfluenceContentParseStrategy;



/**
 *
 * @author ryan
 */
public class CommandlineRunner {

    // Required
    private String endpoint;
    private String username;
    private String password;
    private String spaceID;
    private String pageName;
    private String packageName;
    private String groupId;
    private String artifactId;
    private String version;
    private String url;
    
    // Optional
    private File tempDir;

    public CommandlineRunner(String[] args) {
        System.out.println(args[0]);
        endpoint    = args[0];
        username    = args[1];
        password    = args[2];
        spaceID     = args[3];
        pageName    = args[4];
        packageName = args[5];
        groupId     = args[6];
        artifactId  = args[7];
        version     = args[8];
        url         = args[9];
    }

    public void execute() throws IOException, MalformedURLException, Exception {
        StoryHarvester storyHarvester = setupStoryHarvester();
        tempDir = new File("temp");
        tempDir.mkdir();
        
        JarGen jarGenerator = new JarGenImpl("/", tempDir, packageName);
        MavenDeployer deployer = new MavenDeployer(groupId, artifactId, version, url);
        deployer.setRepositoryId("inhouse.snapshot");
        AnnobDeployer d = new AnnobDeployer(storyHarvester, jarGenerator, deployer);
        DeploymentInfo info = d.runDeployment();

    }

    protected StoryHarvester setupStoryHarvester() throws MalformedURLException, Exception {

        Confluence confluence = new Confluence(endpoint);
        confluence.login(username, password);
        ConfluenceAdaptor confluenceAdaptor = new ConfluenceAdaptor(confluence);
        ConfluenceContentParseStrategy contentParser = new TextileConfluenceContentParseStrategy();
        ConfluenceStoryHarvester storyHarvester = new ConfluenceStoryHarvester(confluenceAdaptor, spaceID, pageName, contentParser);
        return storyHarvester;
    }

    public static final void main(String[] args) {
        CommandlineRunner runner = new CommandlineRunner(args);
        try {
            runner.execute();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommandlineRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommandlineRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommandlineRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
