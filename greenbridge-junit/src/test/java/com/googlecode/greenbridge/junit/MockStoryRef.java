/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.StoryRef;

/**
 *
 * @author ryan
 */
public class MockStoryRef implements StoryRef {

    @Override
    public String[] narrative() {
       return new String[] {
           "Given a",
           "When, ",
           "Then"
       };
    }

    @Override
    public String name() {
        return "MockStory";
    }

    @Override
    public int version() {
        return 1;
    }

}
