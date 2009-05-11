/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.ScenarioRef;

/**
 *
 * @author ryan
 */
public class MockScenarioRef implements ScenarioRef {

    @Override
    public String[] narrative() {
        return new String[] {
            "Given a",
            "When ",
            "Then"
        };
    }

    @Override
    public String name() {
        return "MockScenario";
    }

    @Override
    public int version() {
        return 1;
    }

}
