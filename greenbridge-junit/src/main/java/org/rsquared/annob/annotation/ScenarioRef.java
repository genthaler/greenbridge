package org.rsquared.annob.annotation;

/**
`* All scenarios have to extend this base class.
 * 
 * @author ryan
 */
public interface ScenarioRef {

	public String[] narrative();
    public String name();
    public int version();
}
