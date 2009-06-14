/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import com.googlecode.greenbridge.storyharvester.impl.*;
import java.net.URL;
import java.util.List;
import org.codehaus.swizzle.confluence.Confluence;

/**
 *
 * The only purpose of this class is to allow others to use the
 * ConfluenceStoryHarvester without much extra setup and config.
 * This class just has the required things as fields, and does
 * the login in the constructor.
 *
 * @author ryan
 */
public class DefaultConfluenceStoryHarverster extends ConfluenceStoryHarvester {

    /**
     * The confluence endpoint
     * @parameter
     * @required
     */
    private String confluenceEndpoint;


    /**
     * The confluence username
     * @parameter
     * @required
     */
    private String confluenceUsername;

    /**
     * The confluence password
     * @parameter
     * @required
     */
    private String confluencePassword;

    /**
     * The confluence space
     * @parameter
     * @required
     */
    private String confluenceSpace;

    /**
     * The confluence page
     * @parameter
     * @required
     */
    private String confluencePage;



    public DefaultConfluenceStoryHarverster() throws Exception {


    }

    /**
     * This gather creates a swizzle confluece connection, logs in
     * and uses the textile content parser.
     *
     * @throws java.lang.Exception
     */
    @Override
    public List<StoryNarrative> gather() throws Exception {
       System.out.println("Endpoint: " + confluenceEndpoint);
       Confluence confluence = new Confluence(confluenceEndpoint);
       confluence.login(confluenceUsername, confluencePassword);
       ConfluenceAdaptor confluenceAdaptor = new ConfluenceAdaptor(confluence);
       ConfluenceContentParseStrategy contentParser = new TextileConfluenceContentParseStrategy();
       setConfluence(confluenceAdaptor);
       this.setSpaceID(confluenceSpace);
       this.setPageName(confluencePage);
       this.setContentParser(contentParser);
       return super.gather();

    }




    /**
     * @return the confluenceEndpoint
     */
    public String getConfluenceEndpoint() {
        return confluenceEndpoint;
    }

    /**
     * @param confluenceEndpoint the confluenceEndpoint to set
     */
    public void setConfluenceEndpoint(String confluenceEndpoint) {
        this.confluenceEndpoint = confluenceEndpoint;
    }

    /**
     * @return the confluenceUsername
     */
    public String getConfluenceUsername() {
        return confluenceUsername;
    }

    /**
     * @param confluenceUsername the confluenceUsername to set
     */
    public void setConfluenceUsername(String confluenceUsername) {
        this.confluenceUsername = confluenceUsername;
    }

    /**
     * @return the confluencePassword
     */
    public String getConfluencePassword() {
        return confluencePassword;
    }

    /**
     * @param confluencePassword the confluencePassword to set
     */
    public void setConfluencePassword(String confluencePassword) {
        this.confluencePassword = confluencePassword;
    }

    /**
     * @return the confluenceSpace
     */
    public String getConfluenceSpace() {
        return confluenceSpace;
    }

    /**
     * @param confluenceSpace the confluenceSpace to set
     */
    public void setConfluenceSpace(String confluenceSpace) {
        this.confluenceSpace = confluenceSpace;
    }

    /**
     * @return the confluencePage
     */
    public String getConfluencePage() {
        return confluencePage;
    }

    /**
     * @param confluencePage the confluencePage to set
     */
    public void setConfluencePage(String confluencePage) {
        this.confluencePage = confluencePage;
    }


}
