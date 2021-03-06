<%-- 
    Document   : Register
    Created on : Dec 5, 2017, 8:41:26 PM
    Author     : MrHacker
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css"  >
        <link rel="stylesheet" href="css/bootstrap-theme.min.css"  >
        <link rel="stylesheet" href="css/styles.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <!-- Latest compiled and minified JavaScript -->
        <script src="js/bootstrap.min.js" ></script>
        <script src="js/jquery-3.2.1.min.js"></script>

        <script>
            $(function () {

                $('#login-form-link').click(function (e) {
                    $('#login-form').delay(100).fadeIn(100);
                    $('#register-form').fadeOut(100);
                    $('#register-form-link').removeClass('active');
                    $(this).addClass('active');
                    e.preventDefault();
                });

                $('#register-form-link').click(function (e) {
                    $("#register-form").delay(100).fadeIn(100);
                    $("#login-form").fadeOut(100);
                    $('#login-form-link').removeClass('active');
                    $(this).addClass('active');
                    e.preventDefault();
                });

            });
        </script>

    </head>
    <body>

        <%
            Cookie myCurrentSession = null;

            boolean sessionsManagerNotExist = application.getAttribute("sessionsManager") == null;
            String sessionId = "";
            int numOfSessions = 0;

            Cookie[] cookies = request.getCookies();

            for (Cookie c : cookies) {
                if (c.getName().equals("MyCurrentSession")) {
                    myCurrentSession = c;
                }
            }

            boolean MyCurrentSessionExist = myCurrentSession != null;
            //User user = new User("", "", "", "");
            if (MyCurrentSessionExist) {
                sessionId = myCurrentSession.getValue();
            }

            String username = sessionId;
            //String email = user.getEmail();
            
            if (MyCurrentSessionExist) {//MyCurrentSession exist
                if (!sessionsManagerNotExist) {

                    Map<String, HttpSession> sessionsManager = (HashMap<String, HttpSession>) application
                            .getAttribute("sessionsManager");

                    if (sessionsManager.containsKey(sessionId)) {
                        HttpSession newSession = (HttpSession) sessionsManager.get(sessionId);

                        out.print("<h1>sessId = " + sessionId + "</h1><br>");

            //            Database.retriveFromDB(user, sessionId);

               //         username = user.getUsername();
              //          email = user.getEmail();
                        numOfSessions = sessionsManager.size();

                    } else {
                        application.removeAttribute("MyCurrentSession");
                    }
                } else {
                    application.removeAttribute("MyCurrentSession");
                }
            } else {//MyCurrentSession doesn't exist
                if (sessionsManagerNotExist) {
                    Map<String, HttpSession> sessionsManager = new HashMap<String, HttpSession>();
                    application.setAttribute("sessionsManager", sessionsManager);
                }

                //out.print(Forms.inputForm());
            }
        %>

        <div class="container">

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">
                                    <a href="#" class="active" id="login-form-link">Login</a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" id="register-form-link">Register</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="login-form" action="Login" method="post" role="form" style="display: block;">
                                        <div class="form-group">
                                            <input type="email" name="email" tabindex="1" class="form-control" placeholder="email" value="" required />
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" tabindex="2" class="form-control" placeholder="Password" required />
                                        </div>
                                        <div class="form-group text-center">
                                            <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                            <label for="remember"> Remember Me</label>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-success" value="Log In">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="text-center">
                                                        <a href="#" tabindex="5" class="forgot-password">Forgot Password?</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="register-form" action="Signup" method="post" role="form" style="display: none;">
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="" required />
                                        </div>
                                        <div class="form-group">
                                            <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="" required />
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" required />
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" required />
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-success" value="Register Now">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
