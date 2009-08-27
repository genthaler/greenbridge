<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
</script>
<script type="text/javascript" src="<c:url value='/scripts/flowplayer/flowplayer-3.1.1.min.js' />"></script>
<script></script>
<div>

    Results ${pagination.resultsPageStartRange} - ${pagination.resultsPageEndRange} of ${pagination.totalResults}.

    <c:forEach items="${results.conversations}" var="conversation">
        <div>
            <a href="<c:url value='/conversation/${conversation.id}' />">${conversation.name}</a>
        </div>
    </c:forEach>

</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>