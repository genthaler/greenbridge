/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.junit;

/**
 *
 * @author ryan
 */
public class OutputFactory {

    public Output outputForString(String string) {
        if ("txt".equalsIgnoreCase(string)) {
            return new TxtOutput();
        }
        return null;
    }
}
