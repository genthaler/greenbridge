/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.io.PrintWriter;

/**
 *
 * @author ryan
 */
public interface Output {

    String getExtension();

    void write(StoryResults results, PrintWriter stream);

}
