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
               <td><form:label path = "leftUpper_latitude">leftUpper_latitude</form:label></td>
               <td><form:input path = "leftUpper_latitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "leftUpper_longitude">leftUpper_longitude</form:label></td>
               <td><form:input path = "leftUpper_longitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "leftLower_latitude">leftLower_latitude</form:label></td>
               <td><form:input path = "leftLower_latitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "leftLower_longitude">leftLower_longitude</form:label></td>
               <td><form:input path = "leftLower_longitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "rightUpper_latitude">rightUpper_latitude</form:label></td>
               <td><form:input path = "rightUpper_latitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "rightUpper_longitude">rightUpper_longitude</form:label></td>
               <td><form:input path = "rightUpper_longitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "rightLower_latitude">rightLower_latitude</form:label></td>
               <td><form:input path = "rightLower_latitude" /></td>
            </tr>
            <tr>
               <td><form:label path = "rightLower_longitude">rightLower_longitude</form:label></td>
               <td><form:input path = "rightLower_longitude" /></td>
            </tr>          
            <tr>
               <td colspan = "7">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
   
</html>