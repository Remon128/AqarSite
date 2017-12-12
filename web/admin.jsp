<%-- 
    Document   : admin
    Created on : Dec 8, 2017, 2:28:48 PM
    Author     : leonardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome Admin </h1>
        <h2>This is the Whole system Advertisements:</h2>
    </body>

    <div id="showResponse" > ShowResponse field </div>

    <style>

        .vertical-menu {
            width: 800px;
            margin-left: 220px;
            margin-bottom: 10px;
        }

        .vertical-menu a {
            background-color: #eee;
            color: black;
            display: block;
            padding: 12px;
            text-decoration: none;
            margin: 15px;
            margin-bottom: 20px;
        }

        .vertical-menu aSub1:hover {
            background-color: #ff6f4b;
        }

        .vertical-menu a.active {
            background-color: #4CAF50;
            color: white;
        }

        .vertical-menu aSub1{
            background-color: #eee;
            color: black;
            display: ruby;
            padding: 10px;
            text-decoration: none;
            margin-left: 16px;
        }

        .color {
            background-color: yellow;
        }


    </style>

    <script src="js/jquery-3.2.1.min.js" >

    </script>


    <script type="text/javascript">
        function deleteAdd_Ajax(id) {
            console.log("log");
            var AddID = id;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("GET", "AdminManger?id=" + AddID, true);
            xmlhttp.send();

            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    document.getElementById("showResponse").innerHTML = xmlhttp.responseText;
                }
            };
        }
        
        
          function delete_Add(id)
        {
            // document.getElementById(id).innerHTML = "";
            $("#" + id).remove();

        }
         function suspend_Add(id)
        {
            // document.getElementById(id).innerHTML = "";
            //$("#"+id+" a").addClass("color");
            $("#" + id).find("#" + id).addClass("color");

        }
    </script>


    <script>
        $(document).ready(function () {
            $("button").click(function () {
                $("#div1").remove();
            });
        });
    </script>

    <div id='div2' class="vertical-menu">
        <a href="#" class="active">Advertisements</a>
    </div>

    <%
        for (int i = 0; i < 10; i++) {
    %>
    <div id="<%=i%>"   class="vertical-menu">

        <a  id="<%=i%>" > -  <%=i%>   Link 1</a>
        <aSub1  href="#" onclick="deleteAdd_Ajax('<%=i%>') , delete_Add('<%=i%>');"> Remove </aSub1>
        <aSub1  href="#" onclick="suspend_Add('<%=i%>');" >Suspend</aSub1>
    </div>
    <%
        }
    %>

</html>
