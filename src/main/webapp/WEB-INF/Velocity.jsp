<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Velocity</title>
   </head>

   <body>
      <h2>Velocity</h2>
      <h2>Result: ${isSuccessful}</h2>
      <h2>Velocity: ${velocity}</h2>
   </body>
   
   <p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>
   
</html>