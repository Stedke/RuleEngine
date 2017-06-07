<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>linksArea Getter</title>
   </head>

   <body>
      <h2>linksArea Information</h2>
      <form:form method = "POST" action = "/RuleEngine/sm_links/addlinksArea">
         <table>
            <tr>
               <td><form:label path = "latitude">Latitude</form:label></td>
               <td><form:input path = "latitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "longitude">Longitude</form:label></td>
               <td><form:input path = "longitude" /></td>
            </tr>
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
   
</html>