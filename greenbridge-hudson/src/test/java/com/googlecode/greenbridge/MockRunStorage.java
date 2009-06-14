/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author ryan
 */
public class MockRunStorage implements LastRunStorage {

    private Set<String> lastRunPageIds = new HashSet<String>();

    public Set<String> getLastRunPageIds() {
        return lastRunPageIds;
    }

    public void setLastRunPageIds(Set<String> pageIds) {
        this.lastRunPageIds = pageIds;
    }

}
