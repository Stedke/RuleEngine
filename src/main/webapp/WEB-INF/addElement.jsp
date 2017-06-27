<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>addElement</title>
</head>
<body>
<h1>addElement</h1>

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

<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_dictionary.html">sm_dictionary</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_nodes.html">sm_nodes</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_node_properties.html">sm_node_properties</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_segments.html">sm_segments</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_segment_properties.html">sm_segment_properties</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/sm_link_properties.html">sm_link_properties</a><br/>
</p>
<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/index.html">Home page</a><br/>
</p>
</body>
</html>