package com.gc.gb.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.junit.ParameterizableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a user who is logged in<p>
 * When they do stuff<p>
 * Then other stuff will happen<p>
 * 
 * 
 * @see <a href="http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#Scenario2">See Scenario on the Wiki</a>
 */
public abstract class Scenario2  {

        public static final void Given_a_user_who_is_logged_in(){};
        public static final void When_they_do_stuff(){};
        public static final void Then_other_stuff_will_happen(){};



    public String[] narrative() {
        return new String[] {
            "Given a user who is logged in",
                     "When they do stuff",
                     "Then other stuff will happen",
                     ""
        };
    }
   
    public String name() {
        return "Scenario2";
    }

    public String linkUrl() {
        return "http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#Scenario2";
    }

    public String linkName() {
       return "See Scenario on the Wiki";
    }

}