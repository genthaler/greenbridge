package com.test.stories;

import com.googlecode.greenbridge.annotation.StoryRef;

/**
 * As a User I want a widget that saves my quote in progress<p>
 * So that I can come back to it later for viewing, and see changes.<p>
 * 
 * 
 * @see <a href="http://fg5664.nzl.prominagroup.com/display/~ryan_ramage/VOLFOUND_7">Confluence: VOLFOUND_7</a>
 */
public class VOLFOUND_7_21 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "As a User I want a widget that saves my quote in progress",
                     "So that I can come back to it later for viewing, and see changes.",
                     ""
        };
    }

    @Override
    public String name() {
        return "VOLFOUND_7";
    }

    @Override
    public int version() {
        return 21;
    }

}