package com.test.stories;

import com.googlecode.greenbridge.annotation.StoryRef;

/**
 * As a User I want a widget that saves my quote in progress<p>
 * So that I can come back to it later.<p>
 * 
 * 
 * @see <a href="http://fg5664.nzl.prominagroup.com/display/~ryan_ramage/VOLFOUND_7">Confluence: VOLFOUND_7</a>
 */
public class VOLFOUND_7_19 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "As a User I want a widget that saves my quote in progress",
                     "So that I can come back to it later.",
                     ""
        };
    }

    @Override
    public String name() {
        return "VOLFOUND_7";
    }

    @Override
    public int version() {
        return 19;
    }

}