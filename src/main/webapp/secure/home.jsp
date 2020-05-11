<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
</head>
<style>
    body {
        background-color: #EEEEEE;
    }
</style>
<body>
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="../bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="../bootstrap/js/bootstrap.js"></script>
<!---------------------------------------------->

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1

    Optional<User> user = Users.findByEmail(request.getUserPrincipal().getName());
    user.ifPresent(value -> request.getSession().setAttribute("isAdmin", value.getAdministrator()));

%>

<!-- codigo para gente no admin-->

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">  <!-- NavBar -->

    <a class="navbar-brand" id="home" href="">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <a class="nav-item btn text-white ml-auto" href="${pageContext.request.contextPath}/secure/home.do">Trips</a>

        <div class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Actions
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">My trips</a>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/secure/profile.do">Profile</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout.do">Logout</a>
            </div>

        </div>

        <a class="nav-link btn btn-danger btn-outline-light ml-2 col-auto"
           href="${pageContext.request.contextPath}/createTrip.jsp">Create Trip </a>

    </div>
</nav>

<ul class="container">
    <div class="mt-5">
        <form class="form-inline justify-content-center" action="${pageContext.request.contextPath}/filterHome.do"
              method="get">
            <input class="form-control mr-3" type="search" placeholder="Origin" id="fromTrip"
                   name="fromTrip" aria-label="Search">

            <input class="form-control mr-3" type="search" placeholder="Destination" id="toTrip"
                   name="toTrip" aria-label="Search">

            <button class="btn btn-primary" type="submit">SEARCH
                <i class="fa fa-search"></i></button>
        </form>
    </div>
</ul>


<!-- searching for a trip -->

<div class="container-fluid mt-4">
    <div class="row justify-content-center">
        <c:forEach var="trip" items="${trip}">
            <div class="col-auto mb-3">
                <div class="card" style="width: 18rem;">
                    <div>
                        <img src="${trip.driver.avatarPath}" class="rounded-circle" alt="Your Avatar"
                             width="90"
                             height="90">
                        <h4>${trip.driver.firstName} ${trip.driver.lastName}</h4>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">From: ${trip.fromTrip}</h5>
                        <h5 class="card-title">To: ${trip.toTrip}</h5>
                        <p class="card-text">Day: ${trip.date.toString()}</p>
                        <p class="card-text">Hour: ${trip.time.toString()}</p>
                        <p class="card-text">Available seats: ${trip.availableSeats}</p>
                        <a href="#" class="card-link">Join Trip</a>
                        <a href="#" class="card-link">Another link</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
