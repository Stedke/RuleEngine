<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<html>
   <head>
      <title>Add sm_link_properties</title>
   </head>
   <body>
      <h2>Add sm_link_properties</h2>
      <form:form method = "POST" action = "/RuleEngine/addSm_link_properties">

	<table>
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
		<td colspan = "2">
			<input type = "submit" value = "Submit"/>
		</td>
	</tr>
	</table>
      </form:form>
      

<p>New sm_link_properties id</p>
<p>${sm_link_properties_id}</p>

   </body>   
</html>