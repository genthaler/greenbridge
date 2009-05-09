package com.googlecode.greenbridge.annotation;

/**
`* All scenarios have to extend this base class.
 * 
 */
public interface ScenarioRef {

	public String[] narrative();
    public String name();
}

