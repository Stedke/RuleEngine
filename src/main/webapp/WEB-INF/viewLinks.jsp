<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>links Information</title>
   </head>
   <body>
      <h2>links Information</h2>
      <form:form method = "POST" action = "/RuleEngine/chooseLink/chooseSm_Link">
         <table>
            <tr>
               <td><form:label path = "index">index</form:label></td>
               <td><form:input path = "index" /></td>
            </tr>
            <tr>
               <td colspan = "1">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
</html>