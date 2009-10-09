<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

  <style type="text/css" media="screen">   
  	@import url("<c:url value="/resources/dojo/resources/dojo.css"/>");
  	@import url("<c:url value="/resources/dijit/themes/soria/soria.css"/>");
    @import url("<c:url value="/styles/roo.css"/>");
  </style>     
  
  <script djconfig="parseOnLoad: true" src="<c:url value='/resources/dojo/dojo.js'/>" type="text/javascript"></script>
  <script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"> </script>
  <script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"> </script>	 
	<script type="text/javascript">
          dojo.require("dojo.parser");
          dojo.require("dijit.form.ComboBox");
          dojo.require("dojo.data.ItemFileReadStore");

          function quickTagSelected() {
              var tagAndProject = dijit.byId("quickTag").getDisplayedValue();
              var tag = getTag(tagAndProject);
              var project = getProject(tagAndProject);
              if (project == null) {
                window.location = '<c:url value='/tag/' />' + tag;
              } else {
                window.location = '<c:url value='/project/' />' + project + '/tag/' + tag;
              }
          }

          function getTag(d) {
              if (d.indexOf('[') < 0) {
                  return d;
              }
              return d.substring(0, d.indexOf('['));
          }

          function getProject(d) {
              if (d.indexOf('[') < 0) {
                  return null;
              }
              var startIndex = d.indexOf('[') + 1;
              return d.substring(startIndex, d.indexOf(']'));
          }


  </script>
  <title>Greenbridge Conversations</title>
 
</head>

<body class="soria spring">
  <div dojoType="dojo.data.ItemFileReadStore" jsId="TagStore" url="<c:url value='/conversation/tag/json.do' />"/>
  <div id="quickTagDiv">
     Quick Tag: <input dojoType="dijit.form.ComboBox"  store="TagStore" searchAttr="value" id="quickTag" name="tagId"  autocomplete="true" onChange="quickTagSelected()" />
  </div>
  <div id="banner" class="primary-4">
      
  </div>
  <div id="wrap" >
  
  	<div id="menu" class="primary-4">
    	<%@ include file="/WEB-INF/jsp/menu.jsp" %>
    </div>
    <div id="main">