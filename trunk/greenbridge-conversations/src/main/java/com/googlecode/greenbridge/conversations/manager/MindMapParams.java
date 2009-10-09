/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Tag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan
 */
public class MindMapParams {

    private String projectName;
    private List<Tag> tags = new ArrayList<Tag>();
    private List<Person> people  = new ArrayList<Person>();
    private List<Person> attending = new ArrayList<Person>();
    private String meetingName;

    public MindMapParams() {

    }

    /**
     * @return the projectId
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the people
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * @param people the people to set
     */
    public void setPeople(List<Person> people) {
        this.people = people;
    }

    /**
     * @return the meetingName
     */
    public String getMeetingName() {
        return meetingName;
    }

    /**
     * @param meetingName the meetingName to set
     */
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    /**
     * @return the attending
     */
    public List<Person> getAttending() {
        return attending;
    }

    /**
     * @param attending the attending to set
     */
    public void setAttending(List<Person> attending) {
        this.attending = attending;
    }

}
