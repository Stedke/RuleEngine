<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
      <title>links Information</title>
   </head>
   <body>
   
	<table border="1px" cellpadding="0" cellspacing="0" >
	<thead>
	<tr>
		<th width="15%">id</th><th width="30%">geom</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="sm_link" items="${sm_links}">
	<tr>
		<td>${sm_link.id}</td>
		<td>${sm_link.geom}</td>
		<td>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
   
      <h2>links Information</h2>
      <form:form method = "POST" action = "/RuleEngine/chooseLink/chooseSm_Link">
         <table>
            <tr>
               <td><form:label path = "chosenSm_LinkId">chosenSm_LinkId</form:label></td>
               <td><form:input path = "chosenSm_LinkId" /></td>
            </tr>
            <tr>
               <td colspan = "0">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
</html>