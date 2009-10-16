/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.domain;

/**
 *
 * @author ryan
 */
public class MediaTagExtraInfoPerson extends MediaTagExtraInfo {
    private static String PROPERTY_NAME = "personId";

    /**
     * @return the PROPERTY_NAME
     */
    public static String getPROPERTY_NAME() {
        return PROPERTY_NAME;
    }

    /**
     * @param aPROPERTY_NAME the PROPERTY_NAME to set
     */
    public static void setPROPERTY_NAME(String aPROPERTY_NAME) {
        PROPERTY_NAME = aPROPERTY_NAME;
    }
    private Person person;

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    
}
