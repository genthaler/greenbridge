<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.form.Slider");

    function addPerson() {
        dijit.byId("personDialog").show();
    }

</script>


    <button onclick="addPerson()">Add</button>

    <c:forEach items="${people}" var="person">
        <div>
            <a href="<c:url value='/person/${person.slug}' />">${person.name} [${person.email}] </a>
        </div>
    </c:forEach>



<div id="personDialog" dojoType="dijit.Dialog" title="Person Details">
    <form id="editPersonForm" method="post" action="<c:url value='/person/add.do' />">
        <div id="test" style="background-color:white;">

          <label for="name">Name:</label> <div type="text" dojoType="dijit.form.TextBox" name="name" id="name" value="" ></div><br/>
          <label for="email">Email:</label> <div type="text" dojoType="dijit.form.TextBox" name="email" id="email" value="" ></div><br/>
          <br/>
          <input dojotype="dijit.form.Button" type="submit" value="Save"/>
        </div>
    </form>
</div>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
