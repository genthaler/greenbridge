#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.googlecode.greenbridge.annotation.Scenario;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.junit.GreenbridgeRunner;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static ${packageQualifier}.scenarios.TestTable.*;

/**
 * Unit test for simple App.
 */
@RunWith(GreenbridgeRunner.class)
@Story(${packageQualifier}.stories.SampleStory1_2.class)
public class AppTest 
{


    /**
     * Rigourous Test :-)
     */
     @Test @Scenario(${packageQualifier}.scenarios.TestTable_4.class)
    public void testApp()
    {
        Given_a_tiddler_with_a_table();
        When_the_scenario_is_parsed();
        It_should_have_a_table_attached_to_it_();
        String prevBalance = DATA_TABLE.get(0).get("PrevBalance");
        assertEquals("0.00", prevBalance);
    }
}
