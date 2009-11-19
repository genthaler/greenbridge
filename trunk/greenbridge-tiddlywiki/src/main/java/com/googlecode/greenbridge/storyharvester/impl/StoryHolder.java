/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;

/**
 *
 * @author ryan
 */
public class StoryHolder {

    private String name;
    private Element source;
    private List<Element> scenarios = new ArrayList<Element>();

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
     * @return the source
     */
    public Element getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Element source) {
        this.source = source;
    }

    /**
     * @return the scenarios
     */
    public List<Element> getScenarios() {
        return scenarios;
    }
}
