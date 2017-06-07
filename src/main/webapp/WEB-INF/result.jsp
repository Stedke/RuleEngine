<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>linksArea Getter result</title>
   </head>

   <body>
      <h2>linksArea Information result</h2>
      <table>
         <tr>
            <td>Latitude</td>
            <td>${latitude}</td>
         </tr>
         <tr>
            <td>Longitude</td>
            <td>${longitude}</td>
         </tr>
      </table>  
   </body>
   
   <p><a href="${pageContext.request.contextPath}/index.html">Home page</a></p>
   
</html>