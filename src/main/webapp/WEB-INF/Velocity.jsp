<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <head>
      <title>Velocity</title>
   </head>

   <body>
      <h2>Velocity</h2>
      <h2>Result: ${isSuccessful}</h2>
     
      <p>Velocity:</p>
		<table border="1px" cellpadding="0" cellspacing="0" >
		<thead>
			<tr>
				<th width="30%">start_point</th><th width="30%">end_point</th><th width="30%">velocity</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="sm_linkArea" items="${sm_linkAreas}">
		<tr>
			<td>${sm_linkArea.start_point}</td>
			<td>${sm_linkArea.end_point}</td>
			<td>
				<table>
				<thead>
				<tr>
					<th width="50%">id</th><th width="50%">velocity</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="velocity" items="${sm_linkArea.velocity}">
				<tr>
					<td>${velocity.x}</td>
					<td>${velocity.y}</td>
				</tr>
				</c:forEach>
			    </tbody>
				</table>
			</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		
   </body>  
   <p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>   
</html>