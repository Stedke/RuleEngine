<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
      <title>Validate</title>
   </head>
   <body>
   
    <p>List of sm_dictionary</p>
	<table border="1px" cellpadding="0" cellspacing="0" >
	<thead>
	<tr>
	<th width="15%">id</th><th width="30%">name</th><th width="30%">keys</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="sm_dictionary" items="${sm_dictionary}">
	<tr>
		<td>${sm_dictionary.id}</td>
		<td>${sm_dictionary.name}</td>
		<td>
			<c:forEach var="sm_dictionary_key" items="${sm_dictionary.key}">
	    		' ' <c:out value = "${sm_dictionary_key}"/>
			</c:forEach>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>
	</body>
</html>