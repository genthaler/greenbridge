package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

public class Person implements Serializable {


    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Attendee> conversations = new HashSet<Attendee>();



    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.util.Set<Attendee> getConversations() {
        return this.conversations;
    }

    public void setConversations(java.util.Set<Attendee> conversations) {
        this.conversations = conversations;
    }

    /**
     * @return the uname
     */
    public String getId() {
        return id;
    }

    /**
     * @param uname the uname to set
     */
    public void setId(String id) {
        this.id = id;
    }



    
}
