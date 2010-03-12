package ${packageName};

import ${scenariopackageName}.*;
import com.googlecode.greenbridge.registration.*;
import com.googlecode.greenbridge.annotation.ScenarioRef;
import com.googlecode.greenbridge.annotation.StoryRef;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ${javaStoryPackageName} implements StoryPackage {

    {
        registerStories();
        StoryRegistration.registerStoryPackage(${javaStoryPackageName}.class);
    }

    private static Map<StoryRef,List<ScenarioRef>> map = new HashMap<StoryRef,List<ScenarioRef>>();

    public String getName() {
        return "${storyPackageName}";
    }
    public String getVersion() {
        return "${storyPackageVersion}";
    }

    public Map<StoryRef,List<ScenarioRef>> getStories() {
        return map;
    }

    public static void registerStories() {
        <#list storyNarratives as storyNarrative>
        {
            StoryRef storyRef = new ${storyNarrative.id}_${storyNarrative.version}();
            List<ScenarioRef> list = new ArrayList<ScenarioRef>();
            <#list storyNarrative.scenarios as scenarioNarrative>
            list.add(new ${scenarioNarrative.id}_${scenarioNarrative.version}());
            </#list>
            map.put(storyRef, list);
        }
        </#list>
    }
}
