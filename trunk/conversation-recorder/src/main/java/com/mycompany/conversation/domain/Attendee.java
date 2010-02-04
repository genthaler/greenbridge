/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.domain;

/**
 *
 * @author ryan
 */
public class Attendee {

    private String id;
    private String name;
    private boolean present;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the present
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * @param present the present to set
     */
    public void setPresent(boolean present) {
        this.present = present;
    }
}
