/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;

/**
 *
 * @author ryan
 */
public class MockScenarioRef implements ScenarioRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "Given a",
            "When ",
            "Then"
        };
    }

    @Override
    public String name() {
        return "MockScenario";
    }

    @Override
    public long version() {
        return 1;
    }

    @Override
    public Class<? extends StoryRef> getStoryRef() {
        return MockStoryRef.class;
    }

    @Override
    public String linkUrl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String linkName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
