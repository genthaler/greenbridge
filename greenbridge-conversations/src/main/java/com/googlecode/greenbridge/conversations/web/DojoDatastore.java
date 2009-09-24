package com.googlecode.greenbridge.conversations.web;

import java.util.Map;


/**
 * A simple holder of name value pairs that can be rendered into json.
 * @author ryan
 */
public class DojoDatastore {
    private Map nameValues;


    /**
     * Constructor with the name values to use.
     * @param nameValues
     */
    public DojoDatastore(Map nameValues) {
        this.nameValues = nameValues;
    }

    /**
     * @return the nameValues
     */
    public Map getNameValues() {
        return nameValues;
    }


}

