/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester;

import java.util.List;
import com.googlecode.greenbridge.StoryNarrative;

/**
 *
 * @author ryan
 */
public interface StoryHarvester {

    public List<StoryNarrative> gather();

}
