package ${packageName};

import org.rsquared.annob.annotation.StoryRef;

/**
 * <#list story.storyNarrative as narrative>${narrative}<p>
 * </#list>
 * <#if story.linkUrl??>
 * @see <a href="${story.linkUrl}">${story.linkName!"Here"}</a></#if>
 */
public class ${story.id}_${story.version} implements StoryRef {

    @Override
    public String[] narrative() {
        return new String[] {
            <#list story.storyNarrative as narrative>"${narrative}",
                     </#list>""
        };
    }

    @Override
    public String name() {
        return "${story.id}";
    }

    @Override
    public int version() {
        return ${story.version};
    }

}