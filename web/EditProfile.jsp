<%-- 
    Document   : EditProfile
    Created on : Dec 10, 2017, 1:26:20 PM
    Author     : MrHacker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Profile</title>
        <link rel="stylesheet" href="css/bootstrap.min.css"  >
        <link rel="stylesheet" href="css/bootstrap-theme.min.css"  >
        <link rel="stylesheet" href="css/styles.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <!-- Latest compiled and minified JavaScript -->
        <script src="js/bootstrap.min.js" ></script>
        <script src="js/jquery-3.2.1.min.js"></script>
    </head>
    <body>

        <div class="container">

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div>
                                    <h1 class="text-success">Update Profile</h1>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form>
                                        <div class="form-group" >
                                            <div  style="align-content: stretch">
                                                <img  class="img-circle"  name="profileImage" src="assets/default.png" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <input name="profileImageSrc" type="file" value="Upload" />
                                        </div>
                                        <div class="form-group">
                                            <div class="text-success">Full Name</div>  <input type="text" class="form-control" placeholder="full name" name="fullName" class="input-smu"/>
                                        </div>

                                        <div class="form-group">
                                            <div class="text-success">Old Password</div> <input type="text" class="form-control" placeholder="Old Password" name="oldPassword" class="input-smu"/>
                                        </div>
                                        <div class="form-group">
                                            <div class="text-success">New Password</div> <input type="text" class="form-control" placeholder="New Password" name="newPassword" class="input-smu"/>
                                        </div>

                                        <div class="form-group">
                                            <div class="text-success">Repeat Password</div> <input type="text" class="form-control" placeholder="Repeat Password" name="repeatPassword" class="input-smu"/>
                                        </div>


                                        <div class="form-group">
                                            <input type="submit"  value="Save" class="form-control btn btn-success"/>
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
