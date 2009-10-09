/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

/**
 *
 * @author ryan
 */
public interface MediaTagManager {


    public MediaTagSearchResults searchForGlobalTags(String tagName, Integer page, Integer limit) throws Exception;
    public MediaTagSearchResults searchForProjectTags(String tagName, String projectName, Integer page, Integer limit) throws Exception ;
    public MediaTagSearchResults searchForPersonTags(String personId, String tagName, String projectName, Integer page, Integer limit) throws Exception;




}
