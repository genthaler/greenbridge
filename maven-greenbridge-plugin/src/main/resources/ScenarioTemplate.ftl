package ${packageName};

import com.googlecode.greenbridge.annotation.ScenarioRef;
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
public class ${scenario.id}_${scenario.version} implements ScenarioRef {

    <#list methods as method>
        public static final void ${method}(){};
    </#list>


   <#if scenario.datatableProperties??>
    public static enum COLUMN {
        <#list scenario.datatableProperties as property>${property}, </#list>
    }
   </#if>
   <#if scenario.datatable??>
    public static final List<Map<COLUMN,String>> DATA_TABLE = new ArrayList<Map<COLUMN, String>>();
    static {
        <#list scenario.datatable as row>
            {
                Map<COLUMN,String> row = new HashMap<COLUMN,String>();
                <#list row?keys as prop>
                    row.put(COLUMN.${prop},"${row[prop]}");
                </#list>
                DATA_TABLE.add(row);
            }
        </#list>
        }
    </#if>


    @Override
    public String[] narrative() {
        return new String[] {
            <#list scenario.narrative as narrative>"${narrative}",
                     </#list>""
        };
    }

    @Override
    public String name() {
        return "${scenario.id}";
    }

    @Override
    public int version() {
        return ${scenario.version};
    }


}