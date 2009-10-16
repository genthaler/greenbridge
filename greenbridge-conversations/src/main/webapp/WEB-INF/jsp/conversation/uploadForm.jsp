<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");
</script>
<script>

</script>

<form action="add.do" method="post" enctype="multipart/form-data">
<h2 style="margin-top: 2px;"><img src="<c:url value='/images/balloon--arrow.png' />"/> Upload Conversation</h2>
    <div id="roo_conversation">
        <label for="_name">Name:</label>
        <input type="text" style="width:350px" id="_name" maxlength="50" name="name" path="name" size="0"/>
        <br/>

    </div>
    <br/>
    <div id="roo_date">
        <label for="_name">Date:</label>
        <input type="text" cssStyle="width:250px" id="_date" maxlength="30" name="date" path="date" size="0"/>
        <br/>

        <script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_date", widgetType : "dijit.form.DateTextBox", widgetAttrs : { promptMessage: "Enter the date the conversation started.", invalidMessage: "", required : true}})); </script>
    </div>
    <br/>
    <div id="roo_time">
        <label for="_name">Time:</label>
        <input type="text" cssStyle="width:250px" id="_time" maxlength="30" name="time" path="time" size="0"/>
        <br/>

        <script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_time", widgetType : "dijit.form.TimeTextBox", widgetAttrs : {promptMessage: "Enter the time conversation started.", invalidMessage: "", required : true}})); </script>
    </div>
    <br/>
    <br/>
    <div id="conversation_file">
        <label for="_name">Conversation (mp3):</label>
        <div  id="_file"><input type="file" name="conversation" style="width: 350px;" size="55"/></div>
    </div>
    <br/>
    <div id="conversation_tags" style="display: none;">
        <label for="_name">Tags (xml):</label>
        <div  id="_tags"><input type="file" name="tags"/></div>
    </div>
    <br/>
    <input type="submit" value="Upload">
</form> 

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
