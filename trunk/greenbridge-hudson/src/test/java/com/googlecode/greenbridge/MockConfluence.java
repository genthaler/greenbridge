/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import com.googlecode.greenbridge.storyharvester.impl.ConfluenceInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.swizzle.confluence.Page;

/**
 *
 * @author ryan
 */
public class MockConfluence implements ConfluenceInterface {

    
    private Map<String,Page> pageNameToPage;
    private Map<String,Page> pageIdToPage;
    private Map<String,List<Page>> parentPageIdToChildrenPage;
    List<Page> firstChildren ;


    public MockConfluence(Date lastChange) {
        pageNameToPage = new HashMap<String, Page>();
        pageIdToPage = new HashMap<String, Page>();
        parentPageIdToChildrenPage = new HashMap<String, List<Page>>();
        setupPages(lastChange);
    }
    

    private void setupPages(Date lastChanged) {
        Page p1 = createPage("ROOT", "R1", "1", lastChanged);
        Page p2 = createPage("A", "1", "1", lastChanged);
        Page p3 = createPage("B", "2", "1", lastChanged);

        firstChildren = new ArrayList<Page>();
        firstChildren.add(p2);
        firstChildren.add(p3);
        parentPageIdToChildrenPage.put(p1.getId(), firstChildren);

        Page p4 = createPage("A1", "11", "1", lastChanged);
        Page p5 = createPage("A2", "22", "1", lastChanged);
        List<Page> secondChildren = new ArrayList<Page>();
        secondChildren.add(p4);
        secondChildren.add(p5);
        parentPageIdToChildrenPage.put(p2.getId(), secondChildren);
    }

    protected void addPageToFirstChildren(String pageName, String pageId, String version, Date when) {
        Page p = createPage(pageName, pageId, version, when);
        firstChildren.add(p);
    }


    private Page createPage(String pageName, String pageId, String version, Date lastChanged) {
        Map data = new HashMap();
        data.put("title", pageName);
        data.put("version", version);
        Page p = new Page(data);
        p.setId(pageId);
        pageNameToPage.put(pageName, p);
        pageIdToPage.put(pageId, p);
        p.setModified(lastChanged);
        return p;
    }


    public void setModifiedDate(String pageID, Date newModifiedDate) {
        Page page = pageIdToPage.get(pageID);
        page.setModified(newModifiedDate);
    }

    public void deletePage(String pageID) {
        Page page = pageIdToPage.get(pageID);
        pageIdToPage.remove(pageID);
        pageNameToPage.remove(page.getTitle());
        parentPageIdToChildrenPage.remove(pageID);
        Collection<List<Page>> allKids = parentPageIdToChildrenPage.values();
        for (Iterator<List<Page>> it = allKids.iterator(); it.hasNext();) {
            List<Page> list = it.next();
            list.remove(page);
        }
    }




    public Page getPage(String spaceID, String pageName) throws Exception {
        return pageNameToPage.get(pageName);
    }

    public Page getPage(String pageID) throws Exception {
        return pageIdToPage.get(pageID);
    }

    public List getChildren(String pageID) throws Exception {
        return parentPageIdToChildrenPage.get(pageID);
    }

}
