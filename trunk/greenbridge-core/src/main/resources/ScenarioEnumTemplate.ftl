
package ${packageName};

public enum ScenarioRef {

    <#list scenarios as scenario>
    /**
     * <#list scenario.narrative as narrative>${narrative}<p>
     * </#list>
     * <#if scenario.linkUrl??>
     * @see <a href="${scenario.linkUrl}">${scenario.linkName!"Here"}</a></#if>
     */
     ${scenario.id} (
                     <#list scenario.narrative as narrative>"${narrative}",
                     </#list>""
                    ),
    </#list>
    ;

	private String[] narrative;

	ScenarioRef(String... narrative) {
		this.narrative = narrative;
	}

	public String[] getNarrative() {
		return narrative;
	}

}
