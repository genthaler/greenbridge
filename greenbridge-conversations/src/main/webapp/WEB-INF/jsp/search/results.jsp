<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<style>
    .result th {
        color: black;
    }
</style>

<script type="text/javascript">
    dojo.require("dijit.TitlePane");
    dojo.require("dojox.form.RangeSlider");
</script>
<script type="text/javascript" src="${pageContext.servletContext.servletContextName}/scripts/flowplayer/flowplayer-3.1.1.min.js"></script>
<script></script>

    Results ${pagination.resultsPageStartRange} - ${pagination.resultsPageEndRange} of ${pagination.totalResults}.
    <table class="result" style="width: 100%;">
        <tr style="color: black;">
            <th style="color: black;" >When</th>
            <th>Conversation</th>
            <th>Tag</th>
            <th>Time </th>
            <th>Play</th>
        </tr>
    <c:forEach items="${results.mediaTags}" var="mediaTagSummary">
       <tr>

          <td><fmt:formatDate value="${mediaTagSummary.mediaTagCalculatedTimestamp}" pattern="HH:mm:ss"/><br/>
              <fmt:formatDate value="${mediaTagSummary.mediaTagCalculatedTimestamp}" pattern="dd/MM/yyyy"/>
          </td>
          <td><a href="${pageContext.servletContext.servletContextName}/conversation/${mediaTagSummary.conversationId}">${mediaTagSummary.conversationName}</a></td>
          <td><a href="${pageContext.servletContext.servletContextName}/conversation/${mediaTagSummary.conversationId}/time/${mediaTagSummary.tagStartTime}">${mediaTagSummary.mediaTagName}</a></td>
          <td>${mediaTagSummary.tagStartTime} - ${mediaTagSummary.tagEndTime}</td>
          <td><div id="mediatag-${mediaTagSummary.mediaTagId}" style="display:block;width:130px;height:30px;"></div></td>

        <script>
            $f("mediatag-${mediaTagSummary.mediaTagId}", "${pageContext.servletContext.servletContextName}/scripts/flowplayer/flowplayer-3.1.1.swf", {
            clip: {
                autoPlay: false,
                provider: 'influxis',
                streams: [
                    { url: '${mediaTagSummary.mediaUrl}', start: ${mediaTagSummary.tagStartTime}, duration: ${mediaTagSummary.tagEndTime} }
                ]
            },
            // streaming plugins are configured under the plugins node
            plugins: {
                 controls: {
                   backgroundColor: '#FFFFFF',
                   backgroundGradient: 'low',

                   height: 30,
                   scrubber: false,
                   volume: false,
                   mute: false,
                   fullscreen: false
                 },
                influxis: {
                    url: '${pageContext.servletContext.servletContextName}/scripts/flowplayer/flowplayer.rtmp-3.1.0.swf',
                    netConnectionUrl: 'rtmp://localhost:1935/oflaDemo'
                },
                 audio: {
                    url: '${pageContext.servletContext.servletContextName}/scripts/flowplayer/flowplayer.audio-3.1.0.swf'
                }
            }
        });
        </script>
        </tr>
    </c:forEach>
    </table>


<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>