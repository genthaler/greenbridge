<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
        <script type="text/javascript" src="scripts/jquery.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $('#addRow').bind('click', function() {
                    $('#row0').show();
                });
            });
        </script>
    </head>
    <body>

        <form:form modelAttribute="bean" method="post">

            TestId <input type="text" name="testId"/>

            <table style="border: 1px solid gray">
                <tr>
                    <th>String Binding</th>
                    <th></th>
                </tr>
                <tr>
                    <td>input text</td>
                    <td><form:input path="inputText"></form:input></td>
                </tr>
                <tr>
                    <td>Checkbox</td>
                    <td>
                      <form:checkbox path="checkbox1" value="a"/> a<br/>
                      <form:checkbox path="checkbox1" value="b"/> b<br/>
                      <form:checkbox path="checkbox1" value="c"/> c<br/>
                    </td>
                </tr>
                <tr>
                    <td>Radio</td>
                    <td>
                      <form:radiobutton path="radio1" value="a"/> a<br/>
                      <form:radiobutton path="radio1" value="b"/> b<br/>
                      <form:radiobutton path="radio1" value="c"/> c<br/>
                    </td>
                </tr>
                <tr>
                    <td>Select</td>
                    <td>
                        <form:select path="select1" items="${select1_values}" />
                    </td>
                </tr>
            </table>
            <table style="border: 1px solid gray">
                <tr>
                    <th>Boolean Binding</th>
                    <th></th>
                </tr>
                <tr>
                    <td>input text</td>
                    <td><form:input path="inputText2"></form:input></td>
                </tr>
                <tr>
                    <td>Radio</td>
                    <td>
                      <form:radiobutton path="radio2" value="Y"/> Yes<br/>
                      <form:radiobutton path="radio2" value="N"/> No<br/>
                    </td>
                </tr>
                <tr>
                    <td>Select</td>
                    <td>
                        <form:select path="select2" items="${select_YN_values}" />
                    </td>
                </tr>
            </table>
            <table style="border: 1px solid gray">
                <tr>
                    <th>Integer Binding</th>
                    <th></th>
                </tr>
                <tr>
                    <td>input text</td>
                    <td><form:input path="inputText3"></form:input></td>
                </tr>
                <tr>
                    <td>Radio</td>
                    <td>
                      <form:radiobutton path="radio3" value="1"/> 1<br/>
                      <form:radiobutton path="radio3" value="2"/> 2<br/>
                    </td>
                </tr>
                <tr>
                    <td>Select</td>
                    <td>
                        <form:select path="select3" items="${select_INT_values}" />
                    </td>
                </tr>
            </table>
            <table style="border: 1px solid gray">
                <tr>
                    <th>Date Binding</th>
                    <th></th>
                </tr>
                <tr>
                    <td>input text</td>
                    <td><form:input path="inputText4"></form:input></td>
                </tr>
                <tr>
                    <td>Radio</td>
                    <td>
                      <form:radiobutton path="radio4" value="30/12/2009"/> 12/30/2009<br/>
                      <form:radiobutton path="radio4" value="31/12/2009"/> 12/31/2009<br/>
                    </td>
                </tr>
                <tr>
                    <td>Select</td>
                    <td>
                        <form:select path="select4" items="${select_date_values}" />
                    </td>
                </tr>
            </table>
            <table style="border: 1px solid gray">
                <tr>
                    <th>Sub-bean</th>
                    <th></th>
                </tr>
                <tr>
                    <td>input text</td>
                    <td><form:input path="subbean.prop1"></form:input></td>
                </tr>
                <tr>
                    <td>idate</td>
                    <td><form:input path="subbean.date1"></form:input></td>
                </tr>
            </table>
            <table style="border: 1px solid gray">
                <tr>
                    <th>prop1</th>
                    <th>date</th>
                </tr>
                <tr id="row0" style="display: none;">
                    <td><form:input path="list[0].prop1"></form:input></td>
                    <td><form:input path="list[0].date1"></form:input></td>
                </tr>
            </table>
                <input type="button" id="addRow"  value="Add Row"><br/><br/>

            <input id="next" type="submit" />
        </form:form>



    </body>
</html>
