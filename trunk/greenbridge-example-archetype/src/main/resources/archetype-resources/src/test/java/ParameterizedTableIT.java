#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};


import com.googlecode.greenbridge.annotation.RunWithDelegate;
import com.googlecode.greenbridge.annotation.Scenario;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.junit.GreenbridgeRunner;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static ${packageQualifier}.scenarios.TestTable.*;
/**
 *
 * @author ryan
 */
@RunWith(GreenbridgeRunner.class)
@RunWithDelegate(Parameterized.class)
@Story(${packageQualifier}.stories.SampleStory1_2.class)
public class ParameterizedTableIT {
    private BigDecimal prevBalance;
    private BigDecimal deposit;
    private BigDecimal newBalance;


    @Parameters
    public static Collection generateParameters()  {
        return DATA_TABLE.generateParameters();
    }

    public ParameterizedTableIT(String prevBalance, String deposit, String newBalance) {
        this.prevBalance = new BigDecimal(prevBalance);
        this.deposit = new BigDecimal(deposit);
        this.newBalance = new BigDecimal(newBalance);
    }

    @Test
    @Scenario(${packageQualifier}.scenarios.TestTable_4.class)
    public void ensureAdeposit(){
        BigDecimal newAmount = prevBalance.add(deposit);
        System.out.println("New Balance: " + newAmount);
        System.out.println("shouldBe: " + newBalance);
        Assert.assertTrue(prevBalance.add(deposit).compareTo(newBalance) == 0);
    }

}
