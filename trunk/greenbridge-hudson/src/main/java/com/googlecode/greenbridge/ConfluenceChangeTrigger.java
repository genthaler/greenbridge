/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import antlr.ANTLRException;
import com.googlecode.greenbridge.storyharvester.impl.ConfluenceAdaptor;
import hudson.Extension;
import hudson.model.BuildableItem;
import hudson.model.Cause;
import hudson.model.Item;
import hudson.scheduler.CronTabList;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.swizzle.confluence.Confluence;
import org.kohsuke.stapler.DataBoundConstructor;




/**
 *
 * @author ryan
 */

public class ConfluenceChangeTrigger extends Trigger<BuildableItem> {

    private final String confluenceEndpoint;
    private final String confluenceUsername;
    private final String confluencePassword;
    private final String confluenceSpace;
    private final String confluencePage;

    @DataBoundConstructor
    public ConfluenceChangeTrigger(String confluenceEndpoint, String confluenceUsername, String confluencePassword, String confluenceSpace, String confluencePage) {
        this.confluenceEndpoint = confluenceEndpoint;
        this.confluenceUsername = confluenceUsername;
        this.confluencePassword = confluencePassword;
        this.confluenceSpace    = confluenceSpace;
        this.confluencePage     = confluencePage;
    }


    /**
     * @return the confluenceEndpoint
     */
    public String getConfluenceEndpoint() {
        return confluenceEndpoint;
    }

    /**
     * @return the confluenceUsername
     */
    public String getConfluenceUsername() {
        return confluenceUsername;
    }

    /**
     * @return the confluencePassword
     */
    public String getConfluencePassword() {
        return confluencePassword;
    }

    /**
     * @return the confluenceSpace
     */
    public String getConfluenceSpace() {
        return confluenceSpace;
    }

    /**
     * @return the confluencePage
     */
    public String getConfluencePage() {
        return confluencePage;
    }


    @Override
    public void start(BuildableItem project, boolean newInstance) {

    	super.start(project, newInstance);
        try {
            this.tabs = CronTabList.create("* * * * *");
        } catch (ANTLRException ex) {
            Logger.getLogger(ConfluenceChangeTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private static final Logger LOGGER =
        Logger.getLogger(ConfluenceChangeTrigger.class.getName());


    @Override
    public void run() {
        try {
            LOGGER.info("Checking last run");
            FileBasedLastRunStorage lastRunStorage = new FileBasedLastRunStorage(job.getRootDir());
            Date lastRun = lastRunStorage.getLastRunDate();
            Date currentDate = new Date();
            if (lastRun == null) {
                // never run, we skip thia as changes
                lastRunStorage.setLastRunDate(currentDate);
                return;
            }
            LOGGER.info("Checking for changes between: " + lastRun + " and " + currentDate);
            Confluence confluence = new Confluence(confluenceEndpoint);
            confluence.login(confluenceUsername, confluencePassword);
            ConfluenceAdaptor confluenceAdaptor = new ConfluenceAdaptor(confluence);
            ConfluenceChangeCheck checker = new ConfluenceChangeCheck(confluenceAdaptor, lastRunStorage, confluenceSpace, confluencePage);

            if (checker.start(lastRun)) {
                LOGGER.info("Change detected on page : " + confluencePage + ", or one of it's children");
                job.scheduleBuild(cause);
            }
            else {
                LOGGER.info("No change detected");
            }
            lastRunStorage.setLastRunDate(currentDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
   private static final Cause cause = new Cause() {
        @Override
        public String getShortDescription() {
            return "Confluence Changed.";
        }
    };
    /**
     * Descriptor for {@link HelloWorldBuilder}.
     * The class is marked as public so that it can be accessed from views.
     *
     * <p>
     * See <tt>views/hudson/plugins/hello_world/HelloWorldBuilder/*.jelly</tt>
     * for the actual HTML fragment for the configuration screen.
     */
    // this annotation tells Hudson that this is the implementation of an extension point
    @Extension
    public static final class DescriptorImpl extends TriggerDescriptor {
        /**
         * To persist global configuration information,
         * simply store it in a field and call save().
         *
         * <p>
         * If you don't want fields to be persisted, use <tt>transient</tt>.
         */


        public DescriptorImpl() {
            load();
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        @Override
        public String getDisplayName() {
            return "Build when Confluence page(s) change.";
        }

        /**
         * Applicable to any kind of project.
         */
        @Override
        public boolean isApplicable(Item item) {
            return true;
        }



        

    }










}
