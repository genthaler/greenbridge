<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.TimeTextBox");

    function showRemoteDiv() {
        console.log("remote tags on");
        dojo.("remote_div").play();
    }

    function showRemoteTypeDiv() {

    }


</script>


<form action="save.do" method="post" enctype="multipart/form-data">
<h2 style="margin-top: 2px;"><img src="<c:url value='/images/tables.png' />"/> Project</h2>
    <div id="roo_conversation">
        <label for="_name">Name:</label>
        <input type="text" cssStyle="width:250px" id="_name" maxlength="30" name="name" path="name" size="0"/>
        <br/>

        <script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_name", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {regExp: "[a-z0-9-]+", promptMessage: "Enter Project Name", invalidMessage: "Please only use letters, numbers and no spaces", required : true}})); </script>
    </div>
    <br/>
    <br/>
    <div id="conversation_description">
        <label for="_name">Description:</label>
        <div class="box" id="_description">
            <textarea cols="60" rows="4" name="description"></textarea>
        </div>
    </div>
    <br/>

    <h4>Tags</h4>

    <div>
        <label for="_remote">Remote Tags:</label>
        <input type="checkbox" name="remote" onselect="showRemoteDiv();"> Include tags synchronized from a remote location.
    </div>
    <div id="remote_div" >
        <label for="_remote_url">Remote URL:</label>
         <input type="text" name="remote_url" size="60">
    </div>
    <div id="remote_type" >
        <label for="_remote_type">Remote Type:</label>
         <input type="radio" name="remote_type" size="60" value="jira"> JIRA
         <input type="radio" name="remote_type" size="60" value="confluence"> Confluence
         <input type="radio" name="remote_type" size="60" value="groovy"> Groovy Script
    </div>
    <div id="remote_type_jira" style="display: none;">
        JIRA
    </div>
    <div id="remote_type_confluence" style="display:none">
        Confluence
    </div>
    <div id="remote_type_groovy" style="display:none">
        Groovy
    </div>

    <input type="submit" value="Save">
</form> 

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
