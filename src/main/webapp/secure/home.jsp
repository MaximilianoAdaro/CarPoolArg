<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CarPoolArg</title>

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/style.css">
</head>
<style>
    body {
        background-color: #EEEEEE;
        font-family: Roboto, Muli, sans-serif !important;
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

    Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        request.setAttribute("isAdmin", user.getAdministrator());
        request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
        request.setAttribute("avatarPath", user.getAvatarPath());
        request.setAttribute("hasCar", user.getCar() != null);
    }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

    <a class="navbar-brand" id="home" href="${pageContext.request.contextPath}/secure/home.do">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <a class="nav-item btn text-white ml-auto" href="${pageContext.request.contextPath}/secure/home.do">Trips</a>
        <a class="nav-item btn text-white ml-2" href="${pageContext.request.contextPath}/notification.jsp">
            <i class="fa fa-bell"></i></a>

        <div class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${userName}
                <img src="${avatarPath}" class="rounded-circle" alt="Your Avatar" width="30" height="30">
            </a>
            <div class="dropdown-menu mt-2 ml-5" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/myTrips.do">My trips</a>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/secure/profile.do">Profile</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout.do">Logout</a>
            </div>
        </div>

        <c:if test="${hasCar}">
            <a class="nav-link btn btn-danger btn-outline-light ml-2 col-auto"
               href="${pageContext.request.contextPath}/createTrip.jsp">Create Trip </a>
        </c:if>
        <c:if test="${!hasCar}">
            <!-- Button to Open the Modal -->
            <a class="nav-link btn btn-danger btn-outline-light ml-2 col-auto" data-toggle="modal"
               data-target="#myModal" href="">
                Create Trip
            </a>
            <!-- The Modal -->
            <div class="modal fade" id="myModal">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">

                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h4 class="modal-title">You cannot create a trip</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>

                        <!-- Modal body -->
                        <div class="modal-body">
                            You do not have the requirements, you must have a car added in your profile
                        </div>

                        <!-- Modal footer -->
                        <div class="modal-footer">
                            <a class="nav-link btn btn-primary ml-2 col-auto"
                               href="${pageContext.request.contextPath}/secure/profile.do">Go to profile</a>
                            <a type="button" class="btn btn-secondary" data-dismiss="modal">Close</a>
                        </div>

                    </div>
                </div>
            </div>
        </c:if>

    </div>
</nav> <!-- NavBar -->

<%--Search of a trip--%>

<div class="container">
    <div class="mt-5">
        <h1 class="text-center blueTag">Find who to share your next trip with!</h1>
        <h3 class="text-center lightBlueTag">Choose origin or destination and find it!</h3>
        <form class="form-inline justify-content-center mt-4" action="${pageContext.request.contextPath}/filterHome.do"
              method="get">
            <input class="form-control mr-3 shadow p-3 bg-white rounded"
                   style="border-color: orange" type="search" placeholder="Origin"
                   id="fromTrip"
                   name="fromTrip" aria-label="Search">

            <input class="form-control mr-3 shadow p-3 bg-white rounded"
                   style="border-color: #1c7430" type="search" placeholder="Destination"
                   id="toTrip"
                   name="toTrip" aria-label="Search">

            <button class="btn btn-primary shadow bg-primary rounded" type="submit">SEARCH</button>
        </form>
    </div>
</div>

<!-- searching for a trip -->

<div class="container-fluid mt-4">
    <div class="row justify-content-center">
        <c:forEach var="trip" items="${trip}">
            <div class="col-auto mb-3">
                <div class="card shadow p-3 mb-5 bg-white rounded" style="width: 18rem;">
                    <div class="row p-2 mb-5">
                        <div class="col-5 align-content-center imgDriver">
                            <img src="${trip.driver.avatarPath}" class="rounded-circle"
                                 alt="Your Avatar" width="70"
                                 height="70"></div>
                        <div class="col-7 align-content-center nameDriver mt-4">
                                ${trip.driver.firstName} ${trip.driver.lastName}
                        </div>
                    </div>
                    <div class="card-body">
                        <div>

                            <h5 class="card-title" style="color: orange">
                                <i class="fa fa-map-marker"></i>
                                    ${trip.fromTrip}</h5>
                            <h5 class="card-title" style="color: #1c7430">
                                <i class="fa fa-map-marker"></i>
                                    ${trip.toTrip}</h5>
                        </div>
                        <div>
                            <p class="card-text text-center"> ${trip.date.toString()}</p>
                            <p class="card-text text-center"> ${trip.time.toString()}</p>
                        </div>
                        <div class="row p-2">
                            <div class="col-8">
                                <div class="row">
                                    <span class="col-3 numberSeats">${trip.availableSeats}</span>
                                    <span class="col-9 availableSeats">
                                        Available seats</span>
                                </div>
                            </div>
                            <a href="${pageContext.request.contextPath}/viewTrip.jsp?trip=${trip.tripId}"
                               class="viewButton col-4 btn btn-default" role="button"> View
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>