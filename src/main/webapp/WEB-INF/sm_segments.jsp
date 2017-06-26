<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
   <head>
      <title>Add sm_segments</title>
   </head>

   <body>
      <h2>Add sm_segments</h2>
      <form:form method = "POST" action = "/RuleEngine/addSm_segments">

	<table>
	<tr>
		<td><form:label path = "geom">geom</form:label></td>
		<td><form:input path = "geom" /></td>
	</tr>
    <tr>
		<td><form:label path = "start_node">start_node</form:label></td>
		<td><form:input path = "start_node" /></td>
	</tr>
	<tr>
		<td><form:label path = "end_node">end_node</form:label></td>
		<td><form:input path = "end_node" /></td>
	</tr>
	<tr>
		<td colspan = "2">
			<input type = "submit" value = "Submit"/>
		</td>
	</tr>
	</table>
      </form:form>
      

<p>New sm_segments id</p>
<p>${sm_segments_id}</p>

   </body>   
</html>