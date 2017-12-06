<%-- 
    Document   : Register
    Created on : Dec 5, 2017, 8:41:26 PM
    Author     : MrHacker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
               <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css"  >
        <link rel="stylesheet" href="css/bootstrap-theme.min.css"  >
        <!-- Optional theme -->
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <!-- Latest compiled and minified JavaScript -->
        <script src="js/bootstrap.min.js" ></script>
        <script src="js/jquery-3.2.1.min.js"></script>
    </head>
    <body>
        <h1>Register Page</h1>

        <div>

            <form method="post">
                <span>Email</span>
                <input type="email" name="email"/><br>
                <span>Username</span>
                <input type="text" name="username"/><br>
                <span>Password</span>
                <input type="password" name="password"/><br>
                <input class="btn" type="submit" value="Sign Up"/><br>
            </form>
        </div>

    </body>
</html>
