<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<html>
   <head>
      <title>Add sm_node_properties</title>
   </head>
   <body>
      <h2>Add sm_node_properties</h2>
      <form:form method = "POST" action = "/RuleEngine/addSm_node_properties">

	<table>
	<tr>
		<td><form:label path = "node_id">node_id</form:label></td>
		<td><form:input path = "node_id" /></td>
	</tr>
    <tr>
		<td><form:label path = "tags">tags</form:label></td>
		<td><form:input path = "tags" /></td>
	</tr>
	<tr>
		<td><form:label path = "description">description</form:label></td>
		<td><form:input path = "description" /></td>
	</tr>
	<tr>
		<td><form:label path = "dictionary_id">dictionary_id</form:label></td>
		<td><form:input path = "dictionary_id" /></td>
	</tr>
	<tr>
		<td colspan = "3">
			<input type = "submit" value = "Submit"/>
		</td>
	</tr>
	</table>
      </form:form>
      

<p>New sm_node_properties id</p>
<p>${sm_node_properties_id}</p>

   </body>   
</html>