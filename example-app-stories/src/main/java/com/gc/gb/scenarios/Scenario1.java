package com.gc.gb.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.junit.ParameterizableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a user has a tiddlywiki<p>
 * When it is parsed<p>
 * They will get a maven jar<p>
 * 
 * 
 * @see <a href="http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#Scenario1">See Scenario on the Wiki</a>
 */
public abstract class Scenario1  {

        public static final void Given_a_user_has_a_tiddlywiki(){};
        public static final void When_it_is_parsed(){};
        public static final void They_will_get_a_maven_jar(){};



    public String[] narrative() {
        return new String[] {
            "Given a user has a tiddlywiki",
                     "When it is parsed",
                     "They will get a maven jar",
                     ""
        };
    }
   
    public String name() {
        return "Scenario1";
    }

    public String linkUrl() {
        return "http://greenbridge.googlecode.com/svn/trunk/example-app-stories/stories.html#Scenario1";
    }

    public String linkName() {
       return "See Scenario on the Wiki";
    }

}