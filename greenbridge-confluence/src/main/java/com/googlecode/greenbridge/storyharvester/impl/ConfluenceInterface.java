/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.List;
import org.codehaus.swizzle.confluence.Page;

/**
 * This interface allows for easier mocks to be made for unit testing.
 *
 * @author ryan
 */
public interface ConfluenceInterface {
    /**
     * Gets the confluence page
     * @param spaceID the confluence space ID
     * @param pageName the page name. Cane include spaces.
     * @returni page
     */
    public Page getPage(String spaceID, String pageName) throws Exception;

    /**
     * Return a page by the internal confluence ID
     * @param pageID the internal confluence id (not the title)
     * @return the page
     * @throws java.lang.Exception
     */
    public Page getPage(String pageID) throws Exception;

    /**
     * Gets the immediate children of the page. List type is a
     * PageSummary
     * @param pageID the page id (not the name)
     * @return a List of PageSummarys
     */
    public List getChildren(String pageID) throws Exception;
}
