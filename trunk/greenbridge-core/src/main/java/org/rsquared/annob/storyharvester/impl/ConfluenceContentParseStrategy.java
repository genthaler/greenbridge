/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester.impl;

import java.util.List;

/**
 *
 * @author ryan
 */
public interface ConfluenceContentParseStrategy {
    public List<String> getNarrative(String content);
    public Datatable getDatatable(String content);
}
