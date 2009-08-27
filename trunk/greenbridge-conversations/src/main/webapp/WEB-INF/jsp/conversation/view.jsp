<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gb" %>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
</script>
<script type="text/javascript" src="<c:url value='/scripts/flowplayer/flowplayer-3.1.1.min.js' />"></script>
<script>
    function play(time) {
        var state = $f("fms").getState();
        // if paused or ended
        if (state == 4 || state == 5) {
            $f("fms").play();
        }
        $f("fms").seek(time);
    }

    setInterval("updatePlayhead()", 450);

    function updatePlayhead() {
        var playerStatus = $f("fms").getStatus();
        //var playhead = dojo.byId("playhead");
        //var taglist_width  = dojo.style("taglist", "width") ;
        var taglist_height = dojo.style("tag-table", "height");


        var left =  ((playerStatus.time / ${media.mediaLength}) * 500) + 155;;
//        console.log("time: " + playerStatus.time );
        dojo.style("playhead", "left", left );
        dojo.style("playhead", "height", taglist_height + 20);
    }

    function selectMap() {
        dojo.style("tag-content", "display", "none" );
        dojo.style("freemind", "display", "block" );

        dojo.removeClass("tag-menu-item", "tag-menu-selected");
        dojo.addClass("tag-menu-item", "tag-menu");
        dojo.removeClass("map-menu-item", "tag-menu-hover");
        dojo.removeClass("map-menu-item", "tag-menu");
        dojo.addClass("map-menu-item", "tag-menu-selected");
    }
    function selectTags() {
        dojo.style("tag-content", "display", "block" );
        dojo.style("freemind", "display", "none" );

        dojo.removeClass("map-menu-item", "tag-menu-selected");
        dojo.addClass("map-menu-item", "tag-menu");
        dojo.removeClass("tag-menu-item", "tag-menu-hover");
        dojo.removeClass("tag-menu-item", "tag-menu");
        dojo.addClass("tag-menu-item", "tag-menu-selected");
    }

    function overTab(id) {
         if (dojo.hasClass(id, "tag-menu")) {
            dojo.addClass(id, "tag-menu-hover");
         }
    }

    function outTab(id) {
        if (dojo.hasClass(id, "tag-menu-hover")) {
            dojo.removeClass(id, "tag-menu-hover");
        }
    }

    function overTag(id) {
        dojo.removeClass(id, "tagdiv");
        dojo.addClass(id, "tagdiv-over");

        
    }
    function outTag(id) {
        dojo.removeClass(id, "tagdiv-over");
        dojo.addClass(id, "tagdiv");
    }

    function deleteTag(tagId) {
        if(confirm("Are you sure you want to delete this tag?")) {
            console.log("Deleting " + tagId);
            showTagDelete(tagId);
            console.log("done");
        }
    }


    function showTagDelete(tagId) {
        //tag-${mediaTag.id}-row1tag-${mediaTag.id}-row1
        var tag = dojo.byId("tag-" + tagId + "-row2");
        //console.dir(tag);
        dojo.fadeOut({node: "tag-" + tagId + "-row2"}).play();
        dojo.fadeOut({
            node: "tag-" + tagId + "-row1",
            onEnd: function() {
                var nodeIDs = ["#tag-" + tagId + "-row2", "#tag-" + tagId + "-row1"];
                dojo.forEach(nodeIDs, "dojo.query(item).orphan();");
            }

        }).play();


    }


</script>
    <style>
    </style>


    <c:if test="${not empty conversation}">
	<h2 style="margin-top: 0px; margin-bottom: 7px;" ><img src="<c:url value='/images/balloon-left.png' />"/> ${conversation.name}</h2>
        <div id="conversation_date" class="conversation-attribute">
            <fmt:formatDate value="${conversation.startTime}" pattern="MMM d/yyyy HH:mm a"/>
        </div>
        <div id="conversation_description" class="conversation-attribute">
            ${conversation.description}
        </div>

        <div id="fms" style=" height: 57px; width: 537px; margin-left: 143px;">
        </div>
        <div id="conversation-menu">
                 &nbsp;
                 <span id="tag-menu-item" class="tag-menu-selected" onclick="selectTags()" onmouseover="overTab(this)" onmouseout="outTab(this)">Tags</span>
                 <span id="map-menu-item" class="tag-menu" onclick="selectMap()" onmouseover="overTab(this)" onmouseout="outTab(this)">Map</span>
        </div>
        <div id="tag-content" style="width: 660px; ">
            <!--<div id="playhead" style="width:1px; height:200px; background-color:#BBBBBB; position:absolute; left: 168px; z-index:1"></div>-->
            <div id="playhead" style="width:1px; height:1px; background-color:#BBBBBB; position:absolute; left: 157px; z-index:1"></div>
            <table id="tag-table" cellpadding="0" cellspacing="0">
              <c:forEach var="mediaTag" items="${mediaTags}" varStatus="tagId" >
                 <tr  class="tagrow" id="tag-${mediaTag.id}-row1">
                    <td width="100" class="taglabel">
                         ${mediaTag.tag.tagName}
                    </td>
                    <td width="60">
                        <gb:tag-icons mediaTag="${mediaTag}"/>
                    </td>
                    <td width="500" class="tagtd">
                        <div class="tagdiv" class="tagdiv" style="<gb:tag-duration maxwidth="500" mediaTag="${mediaTag}" />" onmouseover="overTag(this)" onmouseout="outTag(this)" onclick="play(${mediaTag.startTime});"></div>
                    </td>    
                </tr>
                <tr id="tag-${mediaTag.id}-row2">
                    <td colspan="3" >
                        <div class="tagactions">
                             <button onclick="editTag(${mediaTag.id})">Edit</button> <button onclick="deleteTag(${mediaTag.id})">Delete</button>
                             [<a href="<c:url value='/conversation/${conversation.id}/time/${mediaTag.startTime}' />" title="permalink">link</a>]
                             [<a href="<c:url value='/tag/${mediaTag.tag.tagName}' />">all</a>]<br/><br/>
                        </div>
                    </td>
                </tr>
              </c:forEach>
                <tr  class="tagrow">
                    <td width="100" class="taglabel">
                        Follow-up
                    </td>
                    <td width="60">
                        <img src="<c:url value='/images/fm/icons/flag.png'/>">
                        <img src="<c:url value='/images/fm/icons/clock.png'/>">
                    </td>
                    <td width="500" class="tagtd">
                        <div class="tagdiv" class="tagdiv" style="width: 100px; margin-left: 0px;" onmouseover="overTag(this)" onmouseout="outTag(this)" onclick="play(0);"></div>
                    </td>
                    
                </tr>
                <tr>
                    <td colspan="3" >
                        <div class="tagdescription">
                            Attendee: Ryan
                        </div>
                        <div class="tagdescription">
                            Here is the description for this tag. It can be really long winded. There is nothing we can do about it.
                        </div>
                        <div class="tagactions">
                             <button>Edit</button> <button>Delete</button> [<a href="conversation/2/time/30">link</a>] [<a href="conversation/tag/Follow-up">all</a>]<br/><br/>
                        </div>
                    </td>
                </tr>
                <!-- Next row -->
                <tr  class="tagrow">
                    <td width="100" class="taglabel">
                        Parking-lot
                    </td>
                    <td width="60">
                        <img src="<c:url value='/images/fm/icons/pencil.png'/>">
                    </td>
                    <td width="500" class="tagtd">
                        <div id="tag-1" class="tagdiv" class="tagdiv" style="width: 100px; margin-left: 400px;" onmouseover="overTag(this)" onmouseout="outTag(this)" onclick="play(49);"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                         <div class="tagactions">
                                <button>Edit</button> <button>Delete</button> [<a href="conversation/2/time/30">link</a>] [<a href="conversation/tag/Follow-up">all</a>]
                         </div>
                    </td>
                </tr>
            </table>
        </div>
        <div id="freemind" style="display: none;">
            <applet code="freemind.main.FreeMindApplet.class" archive="/greenbridge/scripts/freemind/freemindbrowser.jar" width="100%" height="100%">
                <param name="type" value="application/x-java-applet;version=1.4"/>
                <param name="scriptable" value="true"/>
                <param name="modes" value="freemind.modes.browsemode.BrowseMode"/>
                <param name="browsemode_initial_map" value="http://localhost:8080/greenbridge/scripts/freemind/map.mm"/>
                <param name="initial_mode" value="Browse"/>
                <param name="selection_method" value="selection_method_direct"/>
            </applet>
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
