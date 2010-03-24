package ${packageName};

import com.googlecode.greenbridge.annotation.*;
import com.googlecode.greenbridge.junit.ParameterizableList;
import ${storiesPackageName}.*;
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
public class ${scenario.id}_${scenario.version?c} extends ${scenario.id} implements ScenarioRef {

    @Override
    public long version() {
        return ${scenario.version?c}l;
    }

    @Override
    public Class<? extends StoryRef> getStoryRef() {
        return ${story}.class;
    }


}