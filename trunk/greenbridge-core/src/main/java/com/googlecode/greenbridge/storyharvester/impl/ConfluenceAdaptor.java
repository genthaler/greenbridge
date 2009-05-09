/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.List;
import org.codehaus.swizzle.confluence.Confluence;
import org.codehaus.swizzle.confluence.Page;

/**
 *
 * @author ryan
 */
public class ConfluenceAdaptor implements ConfluenceInterface {

    Confluence wrapped;

    public ConfluenceAdaptor(Confluence wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Page getPage(String spaceID, String pageName) throws Exception {
        return wrapped.getPage(spaceID, pageName);
    }

    @Override
    public List getChildren(String pageID) throws Exception {
        return wrapped.getChildren(pageID);
    }

    @Override
    public Page getPage(String pageID) throws Exception {
        return wrapped.getPage(pageID);
    }

}
