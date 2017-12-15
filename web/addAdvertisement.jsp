<%-- 
    Document   : add
    Created on : Dec 12, 2017, 6:17:15 PM
    Author     : lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add advertisement</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <%
            session = request.getSession(false);
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("Register.jsp");
            }
        %>
        <div class="container">
            <h2>Add advertisement</h2>
            <form class="form-horizontal" action="AddAdvertisement" method="GET" onsubmit="return validate(this);">
                <div class="form-group">
                    <label class="control-label col-sm-2">Advertisement type:</label>
                    <div class="col-sm-10">
                        <select class="form-control" placeholder="Enter email" name="adType"><option value="0">Sale</option><option value="1">Rent</option></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Title:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="title">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Description:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="description"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Size:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="number" min="70" value="70" name="size"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Floor:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="number" min="1" value="1" name="floor"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Property Status:</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="propStatus"><option value="finished">Finished</option><option value="half">Half-finished</option><option value="unfinished">Un-finished</option></select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Property Type:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="propType"/>
                    </div>
                </div>
                <div class="form-group">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" value="Add" class="btn btn-default"/>
                    </div>
                </div>
            </form>
        </div>
        <script type="text/javascript">
            function validate(form) {
                if (form.title.value.length < 1) {
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
