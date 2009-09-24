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
          dojo.require("dijit.form.FilteringSelect");
          dojo.require("dojo.data.ItemFileReadStore");

          function quickTagSelected() {
              window.location = '<c:url value='/tag/' />' + dijit.byId("quickTag").getDisplayedValue();
              
          }

  </script>
  <title>Greenbridge Conversations</title>
 
</head>

<body class="soria spring">
  <div dojoType="dojo.data.ItemFileReadStore" jsId="TagStore" url="<c:url value='/conversation/tag/json.do' />"/>
  <div id="quickTagDiv" style="position:absolute; right: 200px;">
     Quick Tag: <input dojoType="dijit.form.FilteringSelect"  store="TagStore" searchAttr="value" id="quickTag" name="tagId"  autocomplete="true" onChange="quickTagSelected()" />
  </div>
  <div id="banner">
      <img height="30" src="<c:url value='/images/greenbridge-logo.png' />" alt="Greenbridge Conversations" /> Greenbridge
  </div>
  <div id="wrap" >
  
  	<div id="menu">
    	<%@ include file="/WEB-INF/jsp/menu.jsp" %>
    </div>
    <div id="main">