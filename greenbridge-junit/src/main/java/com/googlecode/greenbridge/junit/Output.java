/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author ryan
 */
public interface Output {

    String getExtension();

    /**
     * Allow the output to produce something on the scenario granularity.
     * @param result The scenario result
     * @param basedirectory the base output directory
     * @return the file created
     */
    File write(ScenarioResult result, File basedirectory);
    void write(StoryResults results, PrintWriter stream);

}
