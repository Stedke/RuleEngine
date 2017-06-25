<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
   <head>
      <title>Add sm_dictionary</title>
   </head>

   <body>
      <h2>Add sm_dictionary</h2>
      <form:form method = "POST" action = "/RuleEngine/addSm_nodes">

	<table>
	<tr>
		<td><form:label path = "geom">geom</form:label></td>
		<td><form:input path = "geom" /></td>
	</tr>
    <tr>
		<td><form:label path = "osm_node">osm_node</form:label></td>
		<td><form:input path = "osm_node" /></td>
	</tr>
	<tr>
		<td colspan = "1">
			<input type = "submit" value = "Submit"/>
		</td>
	</tr>
	</table>
      </form:form>
      

<p>New sm_nodes id</p>
<p>${sm_nodes_id}</p>

   </body>   
</html>