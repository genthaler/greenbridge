<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
</script>
<script type="text/javascript" src="<c:url value='/scripts/flowplayer/flowplayer-3.1.1.min.js' />"></script>
<script>
    function play(sliderId) {
        var min = dijit.byId(sliderId).getValue()[0];
        console.log("minimum: " + min);
        var state = $f("fms").getState();
        // if paused or ended
        if (state == 4 || state == 5) {
            $f("fms").play();
        }
        $f("fms").seek(min);
    }

    setInterval("updatePlayhead()", 500);

    function updatePlayhead() {
        var playerStatus = $f("fms").getStatus();
        var playhead = dojo.byId("playhead");
        var taglist_width  = dojo.style("taglist", "width") ;
        var taglist_height = dojo.style("taglist", "height");


        var left =  (playerStatus.time / ${media.mediaLength}) * taglist_width;

        dojo.style("playhead", "left", left );
        dojo.style("playhead", "height", taglist_height + 20);
    }


</script>
    <style>
    </style>


    <c:if test="${not empty conversation}">
	<h2 style="margin-top: 2px;"><img src="<c:url value='/images/balloon-left.png' />"/> ${conversation.name}</h2>
        <br/>
        <div id="conversation_description">
            <label for="_name">Description:</label>
            <div class="box" id="_description">${conversation.description}</div>
        </div>
        <br/>
           <div id="fms" style="border: 1px; height: 60px; width: 575px; left: -10px; position:relative">
            </div>
        <div id="taglist" >
           <div id="playhead" style="width:1px; height:1px; background-color:#BBBBBB; position:absolute; left: 10px; z-index:-1"></div>
           <c:forEach var="mediaTag" items="${mediaTags}" varStatus="tagId" >
               <div style="">
                ${mediaTag.tag.tagName}
                    <input type="image" src="<c:url value='/images/control.png' />" onclick="play('${mediaTag.id}-slider');" />
                     <img src="<c:url value='/images/tag--pencil.png' />" alt="Edit this tag" />
                    <img src="<c:url value='/images/tag--minus.png' />" alt="Remove this tag" />

                    <a href="<c:url value='/conversation/${conversation.id}/time/${mediaTag.startTime}' />" title="permalink">
                        <img src="<c:url value='/images/chain.png' />" alt="permalink"/>
                    </a>
                    <div >

                        



                        <span id="${mediaTag.id}-slider" 
                                 discreteValues="${mediaTag.media.mediaLength}" showButtons="false"
                                 maximum="${mediaTag.media.mediaLength}" value="${mediaTag.startTime},${mediaTag.endTime}">
                        </span>
                    </div>
               </div>
           </c:forEach>
        </div>
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
			$f("fms", "<c:url value='/scripts/flowplayer/flowplayer-3.1.1.swf' />", {
				clip: {
					provider: 'influxis',
					streams: [
						{ url: '${media.url}', start: ${startTime} }
					]
				},
				// streaming plugins are configured under the plugins node
				plugins: {
                     controls: {
                       fullscreen: false,
                       backgroundColor: '#FFFFFF',
                       backgroundGradient: 'low',
                       all: false,
                       scrubber: true,
                       mute: false,
                       height: 30,
                       borderRadius: '0px'
                     },
                     time: {
                         url: '<c:url value='/scripts/flowplayer/flowplayer.controls-3.1.1.swf' />',
                         backgroundColor: '#FFFFFF',
                         backgroundGradient: 'low',
                         top: 0,
                         all: false,
                         play: true,
                         time: true,
                         volume: true,
                         mute: true,
                         height: 30
                     },
					// here is our rtpm plugin configuration
					influxis: {
						url: '<c:url value='/scripts/flowplayer/flowplayer.rtmp-3.1.0.swf' />',

						// netConnectionUrl defines where the streams are found
						netConnectionUrl: 'rtmp://localhost:1935/oflaDemo'
					}
				}
			});
		</script>


    </c:if>
    <c:if test="${empty conversation}">No conversation found with this id.</c:if>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
