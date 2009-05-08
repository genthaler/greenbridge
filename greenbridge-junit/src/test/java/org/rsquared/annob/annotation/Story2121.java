/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.annotation;

/**
 *
 * @author ryan
 */
public class Story2121 implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
          "As a " ,
          "I want ",
          "So that"
        };
    }

    @Override
    public String name() {
        return "Story2121";
    }

    @Override
    public int version(){
        return 1;
    }
}
