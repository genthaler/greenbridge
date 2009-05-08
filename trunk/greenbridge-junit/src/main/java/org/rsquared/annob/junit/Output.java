/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.junit;

import java.io.PrintWriter;

/**
 *
 * @author ryan
 */
public interface Output {

    String getExtension();

    void write(StoryResults results, PrintWriter stream);

}
