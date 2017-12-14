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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Advertisements</title>
    </head>
    <body>
        <%-- 
            print user details upper
        --%>
        <div class="container">
            <div class="row">
                <h1 class="col-lg-10">Advertisements</h1>
                <h1 class="col-lg-2"><a class="btn btn-primary" href="addAdvertisement.jsp">Add advertisement</a></h1>
            </div>
            <div class="list-group">
            <%
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("AqarTestPU");
                AdvertisementJpaController adController = new AdvertisementJpaController(emf);
                List<Advertisement> ads = adController.findAdvertisementEntities();
                for (Advertisement ad : ads) {
            %>
            <a href="Advertisement.jsp?id=<%=ad.getId()%>" class="list-group-item"><%=ad.getTitle()%></a>
            <%
                }
            %>
            </div>
        </div>
    </body>
</html>
