package ${packageName};

import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.junit.ParameterizableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * <#list scenario.narrative as narrative>${narrative}<p>
 * </#list>
 * <#if scenario.linkUrl??>
 * @see <a href="${scenario.linkUrl}">${scenario.linkName!"Here"}</a></#if>
 */
public abstract class ${scenario.id}  {

    <#list methods as method>
        public static final void ${method}(){};
    </#list>


   <#if scenario.datatableProperties??>
    public static enum COLUMN {
        <#list scenario.datatableProperties as property>${property}, </#list>
    }
   </#if>
   <#if scenario.datatable??>
    public static final ParameterizableList<Map<String,String>> DATA_TABLE = new ParameterizableList<Map<String, String>>(COLUMN.class);
    static {
        <#list scenario.datatable as row>
            {
                Map<String,String> row = new HashMap<String,String>();
                <#list row?keys as prop>
                    row.put("${prop}","${row[prop]}");
                </#list>
                DATA_TABLE.add(row);
            }
        </#list>
        }
    </#if>

    public String[] narrative() {
        return new String[] {
            <#list scenario.narrative as narrative>"${narrative}",
                     </#list>""
        };
    }

    public String name() {
        return "${scenario.id}";
    }


}