/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.annotation;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.swizzle.confluence.User;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.rsquared.annob.annotation.AMPEDM_128_3.*;
/**
 *
 * @author ryan
 */

public class ExampleTest {

    @Test @Scenario(AMPEDM_128_3.class)
    public void test() {
        User u;
        String fName = "Bob";
        String lName = "Smith";
        String fullName = fName + " " + lName;
        givenAUser();{
            Map data = new HashMap();
            u = new User(data);
        }
        whenISetTheFullNameToBobSmith();
        {
            u.setFullname(fullName);
        }
        thenGetNameWillReturnBob();{
            //assertEquals(fName, u.getName());
        }
    }



}
