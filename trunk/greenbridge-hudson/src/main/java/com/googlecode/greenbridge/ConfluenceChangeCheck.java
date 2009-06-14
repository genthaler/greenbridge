/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import com.googlecode.greenbridge.storyharvester.impl.ConfluenceInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.codehaus.swizzle.confluence.Page;
import org.codehaus.swizzle.confluence.PageSummary;

/**
 * A new instance of this class should be created for each check. It stores
 * state internal to the instance.
 *
 * @author ryan
 */
public class ConfluenceChangeCheck {

    private static final SimpleDateFormat[] formats;

    static {
        formats = new SimpleDateFormat[] { new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US), new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd HH:mmZ", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US),
                // XML-RPC spec compliant iso8601 formats
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.US), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US),
                new SimpleDateFormat("yyyy-MM-dd", Locale.US), new SimpleDateFormat("yyyyMMdd", Locale.US) };
    }


    private ConfluenceInterface confluence;
    private LastRunStorage lastRunStorage;
    private String spaceId;
    private String pageName;
    private Set thisRunPageIds;

    public ConfluenceChangeCheck(ConfluenceInterface confluence, LastRunStorage lastRunStorage, String spaceId, String pageName) {
        this.confluence = confluence;
        this.lastRunStorage = lastRunStorage;
        this.spaceId = spaceId;
        this.pageName = pageName;
        this.thisRunPageIds = new HashSet();
    }


    public boolean start(Date previousDate) throws Exception {
        this.thisRunPageIds = new HashSet();
        boolean changed = hasChanged(pageName, previousDate);
        if (!changed) {
            changed = anyPageDeleted();
        }
        lastRunStorage.setLastRunPageIds(thisRunPageIds);
        return changed;
    }


    /**
     * 
     * @param spaceId the spaveId to check
     * @param pageId the page to base our check on
     * @param lastChanged when to base our check on. If any page is greater than this date, we return true
     * @return if this page specified or its children have changed
     */
    protected boolean hasChanged(String currentPageName, Date previousDate) throws Exception {
        Page page = confluence.getPage(spaceId, currentPageName);
        Date modified = getDateFromPage(page);

        if (modified != null && modified.after(previousDate)) return true;
        thisRunPageIds.add(page.getId());
        List children = confluence.getChildren(page.getId());
        if (children == null) return false;
        for (Iterator it = children.iterator(); it.hasNext();) {
                PageSummary pageSummary = (PageSummary) it.next();
                Page child = confluence.getPage(pageSummary.getId());
                if (hasChanged(child.getTitle(), previousDate)) {
                    return true;
                }
        }
        return false;
    }


    private Date getDateFromPage(Page p) {
        // HACK ATTACK - Swizzle has a bug getting the date out of the hashmap
        // in confluence-swizzle 1.1 I will wait for a maven release
        Hashtable htable = p.toHashtable();
        Object modified =  htable.get("modified");
        if (modified instanceof Date) {
            return (Date) modified;
        }
        if (modified instanceof String) {
            return toDate((String)modified);
        }
        if (modified == null) return null;

        throw new IllegalStateException("Cant parse date from server");
    }


    private Date toDate(String value) {
        if (value == null || value.equals("")) return new Date();

        ParseException notParsable = null;
        for (int i = 0; i < formats.length; i++) {
            try {
                return formats[i].parse(value);
            } catch (ParseException e) {
                notParsable = e;
            }
        }

        notParsable.printStackTrace();
        return new Date();
    }

    private boolean anyPageDeleted() throws Exception {
        Set<String> oldPageIds = lastRunStorage.getLastRunPageIds();
        if (oldPageIds == null) {
            // first run, we ignore changes
            return false;
        }
        for (Iterator<String> it = oldPageIds.iterator(); it.hasNext();) {
            String oldPageId = it.next();
            if (!thisRunPageIds.contains(oldPageId)) {
                return true;
            }
        }
        return false;
    }

}
