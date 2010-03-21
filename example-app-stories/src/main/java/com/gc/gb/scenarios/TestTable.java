package com.gc.gb.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.junit.ParameterizableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a tiddler with a table<p>
 * When the scenario is parsed<p>
 * It should have a table attached to it.<p>
 * 
 * 
 * @see <a href="file://d:/rtemp/demo/example-app-stories/stories.html#TestTable">See Scenario on the Wiki</a>
 */
public abstract class TestTable  {

        public static final void Given_a_tiddler_with_a_table(){};
        public static final void When_the_scenario_is_parsed(){};
        public static final void It_should_have_a_table_attached_to_it_(){};


    public static enum COLUMN {
        PrevBalance, Deposit, NewBalance, 
    }
    public static final ParameterizableList<Map<String,String>> DATA_TABLE = new ParameterizableList<Map<String, String>>(COLUMN.class);
    static {
            {
                Map<String,String> row = new HashMap<String,String>();
                    row.put("NewBalance","100");
                    row.put("PrevBalance","0.00");
                    row.put("Deposit","100");
                DATA_TABLE.add(row);
            }
            {
                Map<String,String> row = new HashMap<String,String>();
                    row.put("NewBalance","105");
                    row.put("PrevBalance","100");
                    row.put("Deposit","5");
                DATA_TABLE.add(row);
            }
        }

    public String[] narrative() {
        return new String[] {
            "Given a tiddler with a table",
                     "When the scenario is parsed",
                     "It should have a table attached to it.",
                     ""
        };
    }
   
    public String name() {
        return "TestTable";
    }

    public String linkUrl() {
        return "file://d:/rtemp/demo/example-app-stories/stories.html#TestTable";
    }

    public String linkName() {
       return "See Scenario on the Wiki";
    }

}