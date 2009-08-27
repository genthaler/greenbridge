<%-- 
    Document   : tag-duration
    Created on : Aug 27, 2009, 1:02:13 PM
    Author     : ryan
--%>

<%@tag description="Create the tag duration width" pageEncoding="UTF-8"%>
<%@attribute name="maxwidth" required="true"%>
<%@attribute name="mediaTag" type="com.googlecode.greenbridge.conversations.domain.MediaTag" required="true"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="startPixel" value="${(mediaTag.startTime / mediaTag.media.mediaLength) * maxwidth  }" />
<c:set var="endPixel" value="${(mediaTag.endTime / mediaTag.media.mediaLength) * maxwidth  }" />
<c:if test="${endPixel > maxwidth}">
        <c:set var="endPixel" value="${maxwidth}" />
</c:if>
<c:set var="tagWidth" value="${ endPixel- startPixel  }" />


width: ${tagWidth}px; margin-left: ${startPixel}px;