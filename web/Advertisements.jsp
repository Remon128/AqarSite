<%-- 
    Document   : Advertisements
    Created on : Nov 30, 2017, 11:10:58 PM
    Author     : lenovo
--%>

<%@page import="java.util.List"%>
<%@page import="Entities.Advertisement"%>
<%@page import="Controllers.AdvertisementJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- 
            print user details upper
        --%>
        <header> user data</header>
        <div id="UserData">or user header will be added here</div>
        <h1> Advertisements <input type="button" value="new Advertisement" onclick="CreateAddRedirect()"/></h1> 
        <%
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AqarSitePU");
            AdvertisementJpaController adController = new AdvertisementJpaController(emf);
            List<Advertisement> ads = adController.findAdvertisementEntities();
            for (Advertisement ad : ads) {
                out.print(ad);
            }
        %>
    </body>
</html>
