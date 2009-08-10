package com.googlecode.greenbridge.conversations.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import org.hibernate.validator.NotNull;

@Entity
public class Attendee implements Serializable {


    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id")
    private Long id;



    private Boolean present;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Person person;

    @NotNull
    @ManyToOne
    @JoinColumn
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
