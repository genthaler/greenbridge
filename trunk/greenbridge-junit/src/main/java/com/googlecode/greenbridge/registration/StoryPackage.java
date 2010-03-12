/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.registration;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public interface StoryPackage {
    public String getName();
    public String getVersion();
    public Map<StoryRef,List<ScenarioRef>> getStories();

}
