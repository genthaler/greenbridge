<map version="0.9.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<attribute_registry>
    <attribute_name NAME="tag" VISIBLE="true">
    <#list params.tags as tag>
        <attribute_value VALUE="${tag.tagName}"/>
    </#list>
    </attribute_name>
    <attribute_name NAME="person" VISIBLE="true">
    <#list params.people as person>
        <#if person.slug??>
        <attribute_value VALUE="${person.slug}"/>
        </#if>
    </#list>
    </attribute_name>
</attribute_registry>
<node CREATED="1254284804593" ID="ID_1216022750" MODIFIED="1254295035593" TEXT="${params.meetingName}">
    <#if params.projectName??>
        <attribute NAME="project" VALUE="${params.projectName}"/>
    </#if>
    <#if params.people??>
        <#list params.attending as person>
            <attribute NAME="person" VALUE="${person.slug}"/>
        </#list>
    </#if>
</node>
</map>
