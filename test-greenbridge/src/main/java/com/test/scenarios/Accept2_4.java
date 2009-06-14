package com.test.scenarios;

import com.googlecode.greenbridge.annotation.ScenarioRef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a logged in user <p>
 * And a quote with unsaved changes <p>
 * And a current user balance of $0.00 <p>
 * And one unsaved change is the amount outstanding <p>
 * And the amount outstanding is $100.00<p>
 * When the user saves the quote <p>
 * Then the amount outstanding on the quote will be 0 <p>
 * And the current user balance will be $100.00<p>
 * 
 * 
 * @see <a href="http://fg5664.nzl.prominagroup.com/display/~ryan_ramage/Accept2">Confluence: Accept2</a>
 */
public class Accept2_4 implements ScenarioRef {

        public static final void Given_a_logged_in_user_(){};
        public static final void And_a_quote_with_unsaved_changes_(){};
        public static final void And_a_current_user_balance_of_$0_00_(){};
        public static final void And_one_unsaved_change_is_the_amount_outstanding_(){};
        public static final void And_the_amount_outstanding_is_$100_00(){};
        public static final void When_the_user_saves_the_quote_(){};
        public static final void Then_the_amount_outstanding_on_the_quote_will_be_0_(){};
        public static final void And_the_current_user_balance_will_be_$100_00(){};




    @Override
    public String[] narrative() {
        return new String[] {
            "Given a logged in user ",
                     "And a quote with unsaved changes ",
                     "And a current user balance of $0.00 ",
                     "And one unsaved change is the amount outstanding ",
                     "And the amount outstanding is $100.00",
                     "When the user saves the quote ",
                     "Then the amount outstanding on the quote will be 0 ",
                     "And the current user balance will be $100.00",
                     ""
        };
    }

    @Override
    public String name() {
        return "Accept2";
    }

    @Override
    public int version() {
        return 4;
    }


}