/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.annotation;

import com.googlecode.greenbridge.annotation.Scenario;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import static com.googlecode.greenbridge.annotation.AMPEDM_128_3.*;
/**
 *
 * @author ryan
 */

public class ExampleTest {

    @Test @Scenario(AMPEDM_128_3.class)
    public void test() {
        String fName = "Bob";
        String lName = "Smith";
        String fullName = fName + " " + lName;
        givenAUser();{
            Map data = new HashMap();
        }
        whenISetTheFullNameToBobSmith();
        {
        }
        thenGetNameWillReturnBob();{
            //assertEquals(fName, u.getName());
        }
    }



}
