package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;

public class Attendee implements Serializable {


    private Long id;



    private Boolean present;

    private Person person;

    private Conversation conversation;


    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.Boolean getPresent() {
        return this.present;
    }

    public void setPresent(java.lang.Boolean present) {
        this.present = present;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }



}
