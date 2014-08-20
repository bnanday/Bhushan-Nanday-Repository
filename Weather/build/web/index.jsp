<%-- 
    Document   : index
    Created on : Mar 11, 2014, 1:35:17 PM
    Author     : Bhushan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
        h1 { text-align: center}
        h1{color: blue}
        p{text-align: center}
        </style>
    </head>
    <body>
        
        <h1> THE WEATHER MASTER</h1>
        <%
            // Extracting the location and temperture sent by the servlet.
            String  s1  = (String) request.getAttribute("temp");
            String  s2  = (String) request.getAttribute("location");
            
        %>
        
        <p> The current temperature at <%=s2%> is <%=s1%> F. </p>
       <br>
       <br>
       <br>
       <p>Thank you for using THE WEATHER MASTER.</p>
       
      
    </body>
</html>
