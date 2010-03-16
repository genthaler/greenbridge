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
public interface StoryModule {
    public String getName();
    public String getVersion();
    public Map<StoryRef,List<ScenarioRef>> getStories();

}
