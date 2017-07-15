<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>List of loaded items</title>
</head>
<body>
<h1>List of loaded items</h1>
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

<p>List of sm_link_properties</p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="15%">id</th><th width="30%">dictionary_id</th><th width="30%">tags</th>
</tr>
</thead>
<tbody>
<c:forEach var="sm_link_properties" items="${sm_link_properties}">
<tr>
	<td>${sm_link_properties.id}</td>
	<td>${sm_link_properties.dictionary_id.id}</td>
	<td>
	<c:forEach var="sm_link_properties_tags" items="${sm_link_properties.tags}">
	    ' ' <c:out value = "${sm_link_properties_tags}"/>
	</c:forEach>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p>List of sm_segment_properties</p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="15%">id</th><th width="30%">dictionary_id</th><th width="30%">tags</th>
</tr>
</thead>
<tbody>
<c:forEach var="sm_segment_properties" items="${sm_segment_properties}">
<tr>
	<td>${sm_segment_properties.id}</td>
	<td>${sm_segment_properties.dictionary_id.id}</td>
	<td>
	<c:forEach var="sm_segment_properties_tags" items="${sm_segment_properties.tags}">
	    ' ' <c:out value = "${sm_segment_properties_tags}"/>
	</c:forEach>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p>List of sm_node_properties</p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="15%">id</th><th width="30%">dictionary_id</th><th width="30%">tags</th>
</tr>
</thead>
<tbody>
<c:forEach var="sm_node_properties" items="${sm_node_properties}">
<tr>
	<td>${sm_node_properties.id}</td>
	<td>${sm_node_properties.dictionary_id.id}</td>
	<td>
	<c:forEach var="sm_node_properties_tags" items="${sm_node_properties.tags}">
	    ' ' <c:out value = "${sm_node_properties_tags}"/>
	</c:forEach>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p>List of sm_segments</p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="15%">id</th><th width="30%">geom</th><th width="30%">inLineWithLink</th>
</tr>
</thead>
<tbody>
<c:forEach var="sm_segments" items="${sm_segments}">
<tr>
	<td>${sm_segments.id}</td>
	<td>${sm_segments.geom}</td>
	<td>${sm_segments.inLineWithLink}</td>
</tr>
</c:forEach>
</tbody>
</table>

<p>List of sm_nodes</p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="15%">id</th><th width="30%">geom</th>
</tr>
</thead>
<tbody>
<c:forEach var="sm_nodes" items="${sm_nodes}">
<tr>
	<td>${sm_nodes.id}</td>
	<td>${sm_nodes.geom}</td>
</tr>
</c:forEach>
</tbody>
</table>

<p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>

</body>
</html>