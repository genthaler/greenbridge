/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.annotation;


/**
 *  "Given a User",
 *  "When I set the full name to 'Bob Smith'",
 *  "then get name will return 'Bob'"
 *
 * @see <a href="http://google.com/AMPEDM_128_3">Google</a>
 * @author ryan
 */
public class AMPEDM_128_3 implements ScenarioRef {

    public static final void givenAUser(){};
    public static final void whenISetTheFullNameToBobSmith(){};
    public static final void thenGetNameWillReturnBob(){};

    @Override
    public String[] narrative() {
        return new String[] {
            "Given a User",
            "When I set the full name to 'Bob Smith'",
            "then get name will return 'Bob'"
        };
    }

    @Override
    public String name() {
        return "AMPEDM_128";
    }

    @Override
    public int version() {
        return 3;
    }



}
