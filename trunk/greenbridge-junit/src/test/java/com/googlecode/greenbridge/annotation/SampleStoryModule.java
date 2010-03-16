/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.annotation;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class SampleStoryModule implements StoryModule {

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<StoryRef, List<ScenarioRef>> getStories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
