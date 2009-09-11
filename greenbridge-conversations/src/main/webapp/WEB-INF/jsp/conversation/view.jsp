<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gb" %>
<script type="text/javascript">
      dojo.require("dijit.TitlePane");
      dojo.require("dijit.form.ComboBox");
      dojo.require("dojo.data.ItemFileReadStore");
      dojo.require("dijit.form.Slider");
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
            dojo.xhrGet( {
                url: "<c:url value='/conversation/tag/delete.do' />?id=" + tagId,
                handleAs: "text",
                load: function(tagId) {
                    showTagDelete(tagId);
                },
                error: function(error) {
                    alert("Problem deleting");
                }
            });
            
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

    function updateTagDialog(data) {
        dijit.byId("tagName").setValue(data.name);
        dijit.byId("sliderMin").setValue(data.startTime);
        dijit.byId("sliderMax").setValue(data.endTime);
        dojo.byId("shortDescription").value = data.shortDescription;
    }

    function addTag() {
        var playerStatus = $f("fms").getStatus();
        dijit.byId("tagName").setValue(null);
        dijit.byId("sliderMin").setValue(playerStatus.time);
        dijit.byId("sliderMax").setValue(playerStatus.time + 10);
        dojo.byId("shortDescription").value = "";
        dojo.byId("editTagForm").action = "<c:url value='/conversation/tag/new.do' />?conversation=${conversation.id}";
        var playhead_left =  ((playerStatus.time / ${media.mediaLength}) * 400);
        dojo.style("editPlayhead", "left", playhead_left );
        dijit.byId("tagDialog").show();

    }

    function showTagDialog(tagId) {
        dojo.xhrGet( {
                url: "<c:url value='/conversation/tag/view-json.do' />?conversation=${conversation.id}&id=" + tagId,
                handleAs: "json",
                load: function(data) {
                    updateTagDialog(data);
                },
                error: function(error) {
                    alert("Problem getting tag details");
                }
            });

        dojo.byId("editTagForm").action = "<c:url value='/conversation/tag/edit.do' />?conversation=${conversation.id}&id=" + tagId;
        $f("fms").pause();
        var playerStatus = $f("fms").getStatus();
        var playhead_left =  ((playerStatus.time / ${media.mediaLength}) * 400);
        dojo.style("editPlayhead", "left", playhead_left );
        dijit.byId("tagDialog").show();
    }

    function moveEditSliders() {
        var time    = dijit.byId("sliderMin").value;
        var endTime = dijit.byId("sliderMax").value;
        var left    =  ((time / ${media.mediaLength}) * 400) ;

        var length  = (((endTime / ${media.mediaLength}) * 400)) - left;
        console.log("left: " + left);
        dojo.style("editSpan", "marginLeft", left );
        dojo.style("editSpan", "width", length );
        dojo.style("newEndTime", "left", length + 10)
        console.log("left: " + left + " length: " + length);
        dojo.byId("newStartTime").innerHTML = Math.round(time);
        dojo.byId("newEndTime").innerHTML = Math.round(endTime);
    }


    function inspectApplet() {
        var tag_ms = document.freemindApplet.getView().getSelected().getModel().getHistoryInformation().getCreatedAt().getTime();
        console.log("TagMS: " + tag_ms);
        var meetingStart = ${conversation.startTime.time};
        console.log("MeetingMS: " + meetingStart);
        var offset = (tag_ms - meetingStart) / 1000;
        console.log("Offset: " + offset);
        play(offset);
       //Thu Aug 13 11:30:59 NZST 2009
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
            <button onclick="addTag()">Add Tag</button>
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
                        <div class="tagdiv"  style="<gb:tag-duration maxwidth="500" mediaTag="${mediaTag}" />" onmouseover="overTag(this)" onmouseout="outTag(this)" onclick="play(${mediaTag.startTime});"></div>
                    </td>    
                </tr>
                <tr id="tag-${mediaTag.id}-row2">
                    <td colspan="3" >
                        <gb:tag-shortDescriptiion mediaTag="${mediaTag}" />


                        <div class="tagactions">
                             <button onclick="showTagDialog(${mediaTag.id})">Edit</button> <button onclick="deleteTag(${mediaTag.id})">Delete</button>
                             [<a href="<c:url value='/conversation/${conversation.id}/time/${mediaTag.startTime}' />" title="permalink">link</a>]
                             [<a href="<c:url value='/tag/${mediaTag.tag.tagName}' />">all</a>]<br/><br/>
                        </div>
                    </td>
                </tr>
              </c:forEach>
            </table>
        </div>
        <div id="freemind" style="display: none;">
            <button onclick="inspectApplet()">Play Selected</button>
            <applet name="freemindApplet" code="freemind.main.FreeMindApplet.class" archive="<c:url value='/scripts/freemind/freemindbrowser.jar' />" width="100%" height="100%" >
                <param name="type" value="application/x-java-applet;version=1.4"/>
                <param name="scriptable" value="true"/>
                <param name="modes" value="freemind.modes.browsemode.BrowseMode"/>
                <param name="browsemode_initial_map" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<c:url value='/freemind/${conversation.freemindUrl}' />"/>
                <param name="initial_mode" value="Browse"/>
                <param name="selection_method" value="selection_method_direct"/>
            </applet>
        </div>

        <div dojoType="dojo.data.ItemFileReadStore" jsId="TagStore" url="<c:url value='/scripts/tags.txt' />"/>
        <div id="tagDialog" dojoType="dijit.Dialog" title="Tag Details">
            <form id="editTagForm" method="post" action="<c:url value='/conversation/tag/edit.do' />?conversation=${conversation.id}&id=1">
                <div id="test" style="background-color:white;">
                    <label for="_name">Tag:</label>
                     <input dojoType="dijit.form.ComboBox"  store="TagStore" searchAttr="name" id="tagName" name="name"  autocomplete="true"  />
                    <br/>
                    <br/>



                    <label for="_editSlider">Start:</label>
                    <span id="sliderMin" name="startTime" dojoType="dijit.form.HorizontalSlider" minimum="0" maximum="${media.mediaLength}" discreteValues="${media.mediaLength}" value="9" onChange="moveEditSliders()"></span>
                      <br/>
                    <label for="_editSlider">End:</label>
                    <span id="sliderMax" name="endTime" dojoType="dijit.form.HorizontalSlider" minimum="0" maximum="${media.mediaLength}" discreteValues="${media.mediaLength}" value="19" onChange="moveEditSliders()"></span>
                      <br/>
                      <div style="height:14px; margin-left:125px; margin-right:22px;">
                        <div style="float:left; position:relative; width: 400px; height: 14px; border:1px solid green;">
                            <div id="editSpan" class="tagdiv"  style="margin-left: 100px; width:60px; height:13px;" >
                                <div  id="newStartTime" style="position:relative; margin-right: 3px; left:-30px;">0:12</div>
                                <span id="newEndTime" style="position:relative; top:-15px; margin-left: 3px; left: 70px;">0:22</span>
                            </div>
                            <div id="editPlayhead" style="width:1px; height:20px; top:-20px; background-color:#222222; position:relative; left: 157px; z-index:1" ></div>
                        </div>
                    </div>
                    <br/>
                    <label for="_editSlider">Description:</label>
                    <textarea id="shortDescription" name="shortDescription" dojoType="dijit.form.Textarea" style="width:425px;"></textarea>
                    <br/>
                    <br/>

                    <input dojotype="dijit.form.Button" type="submit" name="changeTag" value="Save"/>
                </div>
            </form>
        </div>
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
			$f("fms", {src: "<c:url value='/scripts/flowplayer/flowplayer-3.1.1.swf' />", wmode: 'transparent'}, {
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
