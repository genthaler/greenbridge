<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<style>
    .header {
        background-color: #EEEEEE;
        color: black;
        font-weight: bold;
        padding-top: 3px;
        padding-bottom: 3px;
        padding-left: 7px;
        border-bottom: 1px solid #BBBBBB;
        
    }
    .taggroup {
        border: 1px solid #BBBBBB;
        margin-bottom: 10px;
    }
</style>

<script type="text/javascript">
    dojo.require("dijit.TitlePane");
</script>
<script type="text/javascript" src="${pageContext.servletContext.servletContextName}/scripts/flowplayer/flowplayer-3.1.1.min.js"></script>
<script></script>

<div>
    <div class="taggroup">
        <div class="header">
            Global Tags
        </div>
        <div class="tagList">
            <ul>
                <c:forEach items="${globalTags}" var="tag">
                    <li><a href="<c:url value='/tag/${tag.tagName}' />">${tag.tagName}</a>

                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <b>Project Tags</b> -
        <a href="<c:url value='/project/form' />">Add A Project</a>
    </div>
    <div style="margin-left: 15px;">
        <c:forEach items="${projects}" var="project">
            <div class="taggroup">
                <div class="header">
                    ${project.name}
                </div>
                <div class="tagList">
                    <ul>
                        <c:forEach items="${project.tags}" var="tag">
                            <li>${tag.tagName}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </div>




</div>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>