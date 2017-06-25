<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
   <head>
      <title>Add sm_dictionary</title>
   </head>

   <body>
      <h2>Add sm_dictionary</h2>
      <form:form method = "POST" action = "/RuleEngine/addSm_dictionary">

	<table>
    <tr>
		<td><form:label path = "name">name</form:label></td>
		<td><form:input path = "name" /></td>
	</tr>
    <tr>
		<td><form:label path = "description">description</form:label></td>
		<td><form:input path = "description" /></td>
	</tr>
	<tr>
		<td><form:label path = "key">key</form:label></td>
		<td><form:input path = "key" /></td>
	</tr>
	<tr>
		<td><form:label path = "datatypes">datatypes</form:label></td>
		<td><form:input path = "datatypes" /></td>
	</tr>
	<tr>
		<td><form:label path = "required">required</form:label></td>
		<td><form:input path = "required" /></td>
	</tr>
	<tr>
		<td colspan = "3">
			<input type = "submit" value = "Submit"/>
		</td>
	</tr>
	</table>
      </form:form>
      
      <p>Missing sm_dictionary</p>
	<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
	<th width="15%">id</th><th width="30%">name</th><th width="30%">keys</th>
</tr>
</thead>
<tbody>
<c:forEach var="missing_dictionary" items="${missing_dictionary}">
<tr>
	<td>${missing_dictionary.id}</td>
	<td>${missing_dictionary.name}</td>
	<td>
		<c:forEach var="missing_dictionary_key" items="${missing_dictionary.key}">
   		' ' <c:out value = "${missing_dictionary_key}"/>
		</c:forEach>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p>New dictionary id</p>
<p>${sm_dictionary_id}</p>

   </body>   
</html>