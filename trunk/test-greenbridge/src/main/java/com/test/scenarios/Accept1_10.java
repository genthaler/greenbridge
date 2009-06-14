package com.test.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a account with a balance<p>
 * When I deposit<p>
 * Then the new balance will be<p>
 * 
 * 
 * @see <a href="http://fg5664.nzl.prominagroup.com/display/~ryan_ramage/Accept1">Confluence: Accept1</a>
 */
public class Accept1_10 implements ScenarioRef {

        public static final void Given_a_account_with_a_balance(){};
        public static final void When_I_deposit(){};
        public static final void Then_the_new_balance_will_be(){};


    public static enum COLUMN {
        balance, deposit, newBalance, 
    }
    public static final List<Map<COLUMN,String>> DATA_TABLE = new ArrayList<Map<COLUMN, String>>();
    static {
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                    row.put(COLUMN.balance,"0");
                    row.put(COLUMN.deposit,"100");
                    row.put(COLUMN.newBalance,"100");
                DATA_TABLE.add(row);
            }
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                    row.put(COLUMN.balance,"100");
                    row.put(COLUMN.deposit,"50");
                    row.put(COLUMN.newBalance,"150");
                DATA_TABLE.add(row);
            }
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                    row.put(COLUMN.balance,"-200");
                    row.put(COLUMN.deposit,"50");
                    row.put(COLUMN.newBalance,"-150");
                DATA_TABLE.add(row);
            }
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                    row.put(COLUMN.balance,"-200");
                    row.put(COLUMN.deposit,"250");
                    row.put(COLUMN.newBalance,"50");
                DATA_TABLE.add(row);
            }
        }


    @Override
    public String[] narrative() {
        return new String[] {
            "Given a account with a balance",
                     "When I deposit",
                     "Then the new balance will be",
                     ""
        };
    }

    @Override
    public String name() {
        return "Accept1";
    }

    @Override
    public int version() {
        return 10;
    }


}