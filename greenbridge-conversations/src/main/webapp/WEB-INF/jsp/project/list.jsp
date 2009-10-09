<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
</script>

    <c:forEach items="${projects}" var="project">
        <div>
            <a href="<c:url value='/project/${project.name}' />">${project.name}</a>
        </div>
    </c:forEach>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
