<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gb" %>
<script type="text/javascript">
      dojo.require("dijit.TitlePane");
      dojo.require("dijit.form.FilteringSelect");
      dojo.require("dijit.form.ComboBox");
      dojo.require("dojo.data.ItemFileReadStore");
      dojo.require("dijit.form.TextBox");
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


        var left =  ((playerStatus.time / ${media.mediaLength}) * 500) + 163;;
//        console.log("time: " + playerStatus.time );
        dojo.style("playhead", "left", left );
        dojo.style("playhead", "height", taglist_height + 20);
    }

    function showExistingTag() {
        console.log("show existing tag");
        dojo.style("existingTagDiv", "display", "block" );
        dojo.fadeIn({node: "existingTagDiv"}).play();
        dojo.fadeOut({node: "newTagDiv"}).play();
        dojo.style("newTagDiv", "display", "none" );
    }

    function showNewTag() {
        console.log("show new tag");
        dijit.byId("tagName").setValue(null);
        dojo.style("newTagDiv", "display", "block" );
        dojo.fadeIn({node: "newTagDiv"}).play();
        dojo.fadeOut({node: "existingTagDiv"}).play();
        dojo.style("existingTagDiv", "display", "none" );
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
        dojo.removeClass(id, "primary-2");
        dojo.addClass(id, "tagdiv-over");
        dojo.addClass(id, "primary-1");

        
    }
    function outTag(id) {
        dojo.removeClass(id, "tagdiv-over");
        dojo.removeClass(id, "primary-1");
        dojo.addClass(id, "tagdiv");
        dojo.addClass(id, "primary-2");
    }

    function deleteTag(mediaTagId) {
        if(confirm("Are you sure you want to delete this tag?")) {
            dojo.xhrGet( {
                url: "<c:url value='/conversation/tag/delete.do' />?mediaTagId=" + mediaTagId,
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


    function showTagDelete(mediaTagId) {
        //tag-${mediaTag.id}-row1tag-${mediaTag.id}-row1
        var tag = dojo.byId("tag-" + mediaTagId + "-row2");
        //console.dir(tag);
        dojo.fadeOut({node: "tag-" + mediaTagId + "-row2"}).play();
        dojo.fadeOut({
            node: "tag-" + mediaTagId + "-row1",
            onEnd: function() {
                var nodeIDs = ["#tag-" + mediaTagId + "-row2", "#tag-" + mediaTagId + "-row1"];
                dojo.forEach(nodeIDs, "dojo.query(item).orphan();");
            }

        }).play();


    }

    function tagChanged() {
        console.log(dijit.byId("tagName").getValue() + " for tag");
    }

    function updateTagDialog(data) {
         // TODO - how to set dojo name/value
        dijit.byId("tagName").setValue( data.tagId);
        dijit.byId("tagName").setDisplayedValue(data.tagName);
        dijit.byId("sliderMin").setValue(data.startTime);
        dijit.byId("sliderMax").setValue(data.endTime);
        dojo.byId("shortDescription").value = data.shortDescription;
    }

    function addTag() {
        var playerStatus = $f("fms").getStatus();
        //dijit.byId("tagName").setValue("");
        dijit.byId("sliderMin").setValue(playerStatus.time);
        dijit.byId("sliderMax").setValue(playerStatus.time + 10);
        dojo.byId("shortDescription").value = "";
        dojo.byId("editTagForm").action = "<c:url value='/conversation/tag/new.do' />?conversation=${conversation.id}";
        var playhead_left =  ((playerStatus.time / ${media.mediaLength}) * 400);
        dojo.style("editPlayhead", "left", playhead_left );
        dijit.byId("tagDialog").show();

    }

    function showTagDialog(mediaTagId) {
        dojo.xhrGet( {
                url: "<c:url value='/conversation/tag/view-json.do' />?conversation=${conversation.id}&mediaTagId=" + mediaTagId,
                handleAs: "json",
                load: function(data) {
                    updateTagDialog(data);
                },
                error: function(error) {
                    alert("Problem getting tag details");
                }
            });

        dojo.byId("editTagForm").action = "<c:url value='/conversation/tag/edit.do' />?conversation=${conversation.id}&mediaTagId=" + mediaTagId;
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
	
	function finishLoadingAudio() {
		dojo.style("loading", "display", "none" );
	}

    function showControls(id) {
        console.log("shoing: " + id);
        dojo.style(id, "display", "block" );
        //dojo.fadeIn({node: id}).play();
    }

</script>
    <style>
    </style>


    <c:if test="${not empty conversation}">
	<h2 class="font-3" style="margin-top: 0px; margin-bottom: 7px;" ><img src="<c:url value='/images/balloon-left.png' />"/> ${conversation.name}</h2>
        <div id="conversation_date" class="conversation-attribute">
            <fmt:formatDate value="${conversation.startTime}" pattern="MMM d/yyyy HH:mm a"/>
        </div>
        <div id="conversation_description" class="conversation-attribute">
            ${conversation.description}
        </div>

        <div id="fms" style=" height: 57px; width: 537px; margin-left: 150px;">
        </div>
		
		<div id="loading" style="position: absolute; z-index: 30; top: 45px;">
			<img src="<c:url value='/images/ajax-loader.gif' />" /> <span style="">Loading Audio...</span>
		</div>

        <div id="download" style="position: absolute; z-index: 30; top: 45px; left: 650px;">
			<a href="<c:url value='/mp3/${media.url}' />">mp3</a>
		</div>


        <div id="conversation-menu">
                 &nbsp;
                 <span id="tag-menu-item" class="tag-menu-selected" onclick="selectTags()" onmouseover="overTab(this)" onmouseout="outTab(this)">Tags</span>
                 <c:if test="${not empty conversation.freemindUrl}">
                     <span id="map-menu-item" class="tag-menu" onclick="selectMap()" onmouseover="overTab(this)" onmouseout="outTab(this)">Map</span>
                 </c:if>
        </div>
        <div id="tag-content" style="width: 660px; ">
            <button onclick="addTag()">Add Tag</button>
            <!--<div id="playhead" style="width:1px; height:200px; background-color:#BBBBBB; position:absolute; left: 168px; z-index:1"></div>-->
            <div id="playhead" class="playhead"></div>
            <table id="tag-table" cellpadding="0" cellspacing="0">
              <c:forEach var="mediaTag" items="${mediaTags}" varStatus="tagId" >
                 <tr  class="tagrow" id="tag-${mediaTag.id}-row1">
                    <td width="120" class="taglabel">
                         <a href="<c:url value='/conversation/${conversation.id}/time/${mediaTag.startTime}' />">${mediaTag.tag.tagName}</a>

                         <div id="tag-${mediaTag.id}-controls" style="position:absolute; display:none;" >
                             <button onclick="showTagDialog('${mediaTag.id}')">Edit</button> <button onclick="deleteTag('${mediaTag.id}')">Delete</button>

                         </div>
                    </td>
                    <td width="40">
                          <a href="<c:url value='/tag/${mediaTag.tag.tagName}' />"><img src="<c:url value='/images/light/tag-light.png' />"   title="Show ${mediaTag.tag.tagName} tags from all conversations." alt="Show ${mediaTag.tag.tagName} tags from all conversations."      onmouseover="this.src='<c:url value='/images/light/tag.png' />'"          onmouseout="this.src='<c:url value='/images/light/tag-light.png' />'"/></a>
                          <a href="#" onclick="showTagDialog('${mediaTag.id}'); return false;" style="text-decoration: none;" ><img src="<c:url value='/images/light/field_input-light.png' />" title="Edit this tag..." alt="Edit this tag..." onmouseover="this.src='<c:url value='/images/light/field_input.png' />'"  onmouseout="this.src='<c:url value='/images/light/field_input-light.png' />'" /></a>
                          <a href="#" onclick="deleteTag('${mediaTag.id}');     return false;" style="text-decoration: none;" ><img src="<c:url value='/images/light/cross-light.png' />"  title="Remove this tag..." alt="Remove this tag..."     onmouseover="this.src='<c:url value='/images/light/cross.png' />'"        onmouseout="this.src='<c:url value='/images/light/cross-light.png' />'"/></a>
                    </td>
                    <td width="500" class="tagtd">
                        <div class="tagdiv primary-2"  style="<gb:tag-duration maxwidth="500" mediaTag="${mediaTag}" />" onmouseover="overTag(this)" onmouseout="outTag(this)" onclick="play(${mediaTag.startTime});"></div>
                    </td>    
                </tr>
                <tr id="tag-${mediaTag.id}-row2" >
                    <td colspan="3" >
                        <div style="margin-bottom: 5px;">
                            <gb:tag-icons mediaTag="${mediaTag}"/>
                            <div class="tagdescription">${mediaTag.shortDescription}</div>
                            <gb:tag-extrainfo mediaTag="${mediaTag}" />
                        </div>
                    </td>
                </tr>
              </c:forEach>
            </table>
        </div>
        <c:if test="${not empty conversation.freemindUrl}">
        <div id="freemind" style="display: none;">
            <button onclick="inspectApplet()">Play Selected</button> <span>This is the original upload. It does not reflect the changes made after upload.</span>
            <applet name="freemindApplet" code="freemind.main.FreeMindApplet.class" archive="<c:url value='/scripts/freemind/freemindbrowser.jar' />" width="100%" height="100%" >
                <param name="type" value="application/x-java-applet;version=1.4"/>
                <param name="scriptable" value="true"/>
                <param name="modes" value="freemind.modes.browsemode.BrowseMode"/>
                <param name="browsemode_initial_map" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}<c:url value='/freemind/${conversation.freemindUrl}' />"/>
                <param name="initial_mode" value="Browse"/>
                <param name="selection_method" value="selection_method_direct"/>
            </applet>
        </div>
        </c:if>

        <div dojoType="dojo.data.ItemFileReadStore" jsId="TagStore" url="<c:url value='/conversation/tag/json.do' />"></div>
        <div dojoType="dojo.data.ItemFileReadStore" jsId="ProjectStore" url="<c:url value='/project/list/json.do' />"></div>

        <div id="tagDialog" dojoType="dijit.Dialog" title="Tag Details">
            <form id="editTagForm" method="post" action="<c:url value='/conversation/tag/edit.do' />?conversation=2&id=1">
                    <div id="test" style="background-color:white;">
                    
                    <label for="tagType">Tag:</label>

                    <input type="radio" name="tagType" value="existing" onclick="showExistingTag()" /> Existing
                    <input type="radio" name="tagType" value="new" onclick="showNewTag()" /> New
                    <div id="existingTagDiv" style="margin-left: 100px;">
                        <div dojoType="dijit.form.ComboBox"   store="TagStore" searchAttr="value" id="tagName" name="tagName"  autocomplete="true" onChange="tagChanged()" ></div>
                    </div>
                    <div id="newTagDiv" style="margin-left: 100px; display: none;">
                        <label for="newTagName">Name:</label> <div type="text" dojoType="dijit.form.TextBox" name="newTagName" id="newTag" value="" ></div><br/>
                        <label for="newTagProject">Project:</label> <div dojoType="dijit.form.FilteringSelect"  store="ProjectStore" searchAttr="value" id="tagProject" name="projectId"  autocomplete="true"  ></div><br/>
                    </div>

                        <label for="sliderMin">Tag:</label><div id="sliderMin" style="width:445px;" name="startTime" dojoType="dijit.form.HorizontalSlider" minimum="0" maximum="${media.mediaLength}" discreteValues="${media.mediaLength}" value="19" onChange="moveEditSliders()" ></div>


                        <label for="sliderMax">Tag:</label><div id="sliderMax" style="width:445px;"  name="endTime" dojoType="dijit.form.HorizontalSlider" minimum="0" maximum="${media.mediaLength}" discreteValues="${media.mediaLength}" value="19" onChange="moveEditSliders()" ></div>


                          <div style="height:14px; margin-left:125px; margin-right:22px;">
                            <div style="float:left; position:relative; width: 400px; height: 14px; border:1px solid green;">
                                <div id="editSpan" class="tagdiv primary-2"  style="margin-left: 100px; width:60px; height:13px;" >
                                    <div  id="newStartTime" style="position:relative; margin-right: 3px; left:-30px;">0:12</div>
                                    <span id="newEndTime" style="position:relative; top:-15px; margin-left: 3px; left: 70px;">0:22</span>
                                </div>
                                <div id="editPlayhead" style="width:1px; height:20px; top:-20px; background-color:#222222; position:relative; left: 157px; z-index:1" ></div>
                            </div>
                        </div>
                        <br/>
                        <!-- -->
                        <label for="_editSlider">Description:</label>
                        <textarea id="shortDescription" name="shortDescription"  style="width:425px;"></textarea>
                        <br/>
                        <br/>

                        <input  type="submit" name="changeTag" value="Save"/>
                    </div>
                </form>
        </div>


        
		<!-- this will install flowplayer inside previous A- tag. -->
		<script>
			$f("fms", {src: "<c:url value='/scripts/flowplayer/flowplayer-3.1.1.swf' />", wmode: 'transparent'}, {
				clip: {
					provider: 'influxis',
					onStart: function(clip) {finishLoadingAudio();},
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
