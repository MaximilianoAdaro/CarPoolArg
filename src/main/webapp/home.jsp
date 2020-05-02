<%--
  Created by IntelliJ IDEA.
  User: Numa Leone
  Date: 29-Apr-20
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type ="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href= <meta charset="UTF-8">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <title>Title</title>
</head>
<body>

<!-- codigo para gente no admin-->

<nav class="navbar navbar-expand-lg navbar-light bg-light">  <!-- NavBar -->

    <a class="navbar-brand" id="home" href="home.html">CarPool</a>
    <button class="navbar-toggler" type="button"  data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto container-fluid">

            <li>
                <a class ="nav-item btn btn-light ml-2" href="profile.jsp"><i class="fa fa-user" aria-hidden="true"></i></a>

            </li>

            <div class = "nat-item col-8">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-1 col-5" type="search" placeholder="Search for Destination" aria-label="Search">

                    <button class="btn btn-outline-success my-2 my-sm-0 col-1 ml-2" type="submit"><i class="fa fa-search"></i></button>
                </form>
            </div>

            <li class="nav-item">

                <a class="nav-link btn btn-light mr-1" href="createCarBrand.html"> <i class="fa fa-car"></i> </a>

            </li>

        </ul>

        <a class="nav-link btn btn-light mr-2" href="createTrip.html"> <i class="fa fa-plus"></i> </a>
        <a class="nav-link btn btn-danger col-1" href="/logout.do">Logout</a>

    </div>

</nav>

<p>
    Nuestra pagina web se enfoca en hacer mas faciles los traslados de personas de un lado a otro sin tener que pagar demasiado ni bala cinascowC
</p>


</body>
</html>
