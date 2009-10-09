package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;

public class Attendee implements Serializable {


    private Boolean present;

    private Boolean resolvedToPerson;
    
    private String personSlug;

    private String personId;

    private Conversation conversation;



    public java.lang.Boolean getPresent() {
        return this.present;
    }

    public void setPresent(java.lang.Boolean present) {
        this.present = present;
    }



    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * @return the resolvedToPerson
     */
    public Boolean getResolvedToPerson() {
        return resolvedToPerson;
    }

    /**
     * @param resolvedToPerson the resolvedToPerson to set
     */
    public void setResolvedToPerson(Boolean resolvedToPerson) {
        this.resolvedToPerson = resolvedToPerson;
    }

    /**
     * @return the personSlug
     */
    public String getPersonSlug() {
        return personSlug;
    }

    /**
     * @param personSlug the personSlug to set
     */
    public void setPersonSlug(String personSlug) {
        this.personSlug = personSlug;
    }

    /**
     * @return the personId
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(String personId) {
        this.personId = personId;
    }



}
