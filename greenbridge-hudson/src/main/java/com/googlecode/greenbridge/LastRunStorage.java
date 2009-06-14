/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge;

import java.util.Set;

/**
 *
 * @author ryan
 */
public interface LastRunStorage {


    public Set<String> getLastRunPageIds() throws Exception;

    public void setLastRunPageIds(Set<String> pageIds) throws Exception;

}
