/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester;

import java.util.List;
import org.rsquared.annob.StoryNarrative;

/**
 *
 * @author ryan
 */
public interface StoryHarvester {

    public List<StoryNarrative> gather();

}
