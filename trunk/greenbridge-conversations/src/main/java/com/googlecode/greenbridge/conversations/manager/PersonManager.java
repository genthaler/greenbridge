/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Person;

/**
 *
 * @author ryan
 */
public interface PersonManager {

        /**
         * Finds a person by text. The text is assumed to be either 
         * - The persons email address or
         * - The persons 'slug'
         * 
         * @param text email address or slug
         * @return the person if found, otherwise null
         */
        public Person findPersonForText(String text);

        public Person createPersonForEmailText(String text);

        public boolean isEmailAddress(String slug);
 
        public MediaTagSearchResults searchForPersonTags(String personId, String tagName, String projectName, Integer page, Integer limit) throws Exception ;
}
