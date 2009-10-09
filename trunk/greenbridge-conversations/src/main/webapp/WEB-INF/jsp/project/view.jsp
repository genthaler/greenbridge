<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");

    function showAddTag() {
        dijit.byId("tagDialog").show();
    }
</script>

<h2 style="margin-top: 2px;"><img src="<c:url value='/images/tables.png' />"/> ${project.name}</h2>


<a href="<c:url value='/project/mm.do?name=${project.name}'/>" />Template</a>


<h3>Tags</h3>

<table class="itemlist">
    <col class="tagname" />
    <thead>
        <tr>
            <th>Tag</th><th></th><th></th>
        </tr>
    </thead>
    <tfoot>
        <tr colspan="3"><button class="add" onclick="showAddTag()">Add</button></tr>
    </tfoot>
    <tbody>
        <c:forEach items="${tags}" var="tag">
        <tr>
            <td><a href="<c:url value='/project/${project.name}/tag/${tag.tagName}' />">${tag.tagName}</a></td>
            <td><a href="#" class="button edit">Edit</a></td>
            <td><a href="#" class="button delete">Remove</a></td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<div id="tagDialog" dojoType="dijit.Dialog" title="Tag Details">
    <form id="editTagForm" method="post" action="<c:url value='/project/addTag.do' />?projectId=${project.id}">
        <div id="test" style="background-color:white;">

          <label for="newTagName">Name:</label> <div type="text" dojoType="dijit.form.TextBox" name="tagName" id="newTag" value="" ></div>
          <br/>
          <input dojotype="dijit.form.Button" type="submit" name="changeTag" value="Save"/>
        </div>
    </form>
</div>


<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
