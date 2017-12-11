<%-- 
    Document   : Advertisement
    Created on : Dec 8, 2017, 5:35:39 PM
    Author     : lenovo
--%>

<%@page import="java.util.Collection"%>
<%@page import="Entities.Rating"%>
<%@page import="Entities.Advertisement"%>
<%@page import="Controllers.AdvertisementJpaController"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AqarTestPU");
    AdvertisementJpaController adController = new AdvertisementJpaController(emf);
    Advertisement ad = adController.findAdvertisement(Integer.parseInt(request.getParameter("id")));
    if (ad == null) {
        response.sendRedirect("Advertisements.jsp");
    }
    Collection<Rating> ratings = ad.getRatingCollection();
    int rating = 0;
    for (Rating rate : ratings) {
        rating += rate.getRateValue();
    }
    if (rating > 0) {
        rating /= ratings.size();
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=ad.getTitle()%></title>
    </head>
    <body>
        <h1><%=ad.getTitle()%></h1>
        <%-- check if user owns this ad so add edit ad button--%>
        <h1>Photos: will be implemented soon</h1>
        <h1>Status: <%=ad.getPropStatus()%></h1>
        <h1>Type: <%=ad.getPropType()%></h1>
        <h1>Floor: <%=ad.getFloor()%></h1>
        <h1>Size: <%=ad.getSize()%></h1>
        <h1>Description:</h1>
        <h1><%=ad.getDescription()%></h1>
        <h1>Rating:<%=rating%></h1>

    </body>
</html>
