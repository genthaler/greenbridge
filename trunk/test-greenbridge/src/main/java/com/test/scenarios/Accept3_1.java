package com.test.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a new account<p>
 * When the first deposit is made<p>
 * The message generated should be as below<p>
 * 
 * 
 * @see <a href="http://fg5664.nzl.prominagroup.com/display/~ryan_ramage/Accept3">Confluence: Accept3</a>
 */
public class Accept3_1 implements ScenarioRef {

        public static final void Given_a_new_account(){};
        public static final void When_the_first_deposit_is_made(){};
        public static final void The_message_generated_should_be_as_below(){};


    public static enum COLUMN {
        message, 
    }
    public static final List<Map<COLUMN,String>> DATA_TABLE = new ArrayList<Map<COLUMN, String>>();
    static {
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                    row.put(COLUMN.message,"Welcome to Online Banking!");
                DATA_TABLE.add(row);
            }
        }


    @Override
    public String[] narrative() {
        return new String[] {
            "Given a new account",
                     "When the first deposit is made",
                     "The message generated should be as below",
                     ""
        };
    }

    @Override
    public String name() {
        return "Accept3";
    }

    @Override
    public int version() {
        return 1;
    }


}