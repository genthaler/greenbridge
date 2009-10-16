<%-- 
    Document   : tag-icons
    Created on : Aug 27, 2009, 8:03:13 AM
    Author     : ryan
--%>
<%@tag description="Writes out the icons for a tag" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="mediaTag" type="com.googlecode.greenbridge.conversations.domain.MediaTag" required="true"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>

<c:forEach var="extraInfo" items="${mediaTag.mediaTagExtraInfos}" varStatus="i" >
    <c:if test="${extraInfo.prop == 'shortDescription'}" >
        <div class="tagdescription">${extraInfo.entry}</div>
    </c:if>
    <c:if test="${extraInfo.prop == 'personId'}" >
        <div class="tagdescription">Person: <a href="<c:url value='/person/${extraInfo.person.slug}' />">${extraInfo.person.name}</a></div>
    </c:if>
    <c:if test="${extraInfo.prop != 'personId' && extraInfo.prop != 'shortDescription' && extraInfo.prop != 'icon'}" >
        <div class="tagdescription">${extraInfo.prop}: ${extraInfo.entry}</div>
    </c:if>
</c:forEach>
