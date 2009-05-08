/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.jargen;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.rsquared.annob.StoryNarrative;

/**
 *
 * @author ryan
 */
public interface JarGen {

    /**
     * Given a list of stories, the implementation will
     * generate a java jar that has the following: 
     * - A Story annotation
     * - An ennumeration of all the Stories
     * - A Scenario annotation
     * - A ennumeration of all the Scenarios.
     * @param stories the stories to use to generate the jar
     * @return the file that is the jar.
     */
    File generateRequrementsJar(List<StoryNarrative> stories) throws IOException;
}
