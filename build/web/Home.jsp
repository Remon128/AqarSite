<%-- 
    Document   : Home
    Created on : Dec 13, 2017, 9:44:15 AM
    Author     : MrHacker
--%>

<%@page import="Entities.Useraccount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <%

            session = request.getSession();

            boolean userNotExist = session.getAttribute("userId") == null;
            if (userNotExist) {
                response.sendRedirect("index.jsp");
            } else {
                String userId = session.getAttribute("userId").toString();
                Useraccount user = (Useraccount) session.getAttribute("userInfo");
                out.print(userId);
                out.print("<br>" + user.getEmail());

            }

        %>

        <form action="Logout">
            <input type="submit" value="Logout"/>
        </form>
    </body>
</html>
