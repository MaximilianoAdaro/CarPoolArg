<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <title>Create a Trip</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<style>

    body {
        font-family: Roboto, Muli, sans-serif !important;
    }

</style>

<body style="background-image: url(/images/createTripImage.jpg)">
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="${pageContext.request.contextPath}/bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<!---------------------------------------------->
<script src="${pageContext.request.contextPath}/bootstrap/js/createTrip.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&key=AIzaSyB8S7tVTT7SU0K7aCgQ34g1RieAdx6vIdo"></script>

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1

    Optional<User> user = Users.findByEmail(request.getUserPrincipal().getName());
    if (user.isPresent()) {
        request.setAttribute("isAdmin", user.get().getAdministrator());
        request.setAttribute("userName", user.get().getFirstName() + " " + user.get().getLastName());
        request.setAttribute("avatarPath", user.get().getAvatarPath());
        request.setAttribute("hasCar", user.get().getCar() != null);
    }

    LocalDate localDate = LocalDate.now();
    request.setAttribute("localDate", localDate.toString());
    System.out.println();
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">  <!-- NavBar -->

    <a class="navbar-brand" id="home" href="${pageContext.request.contextPath}/secure/home.do">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <c:if test="${isAdmin}">
            <a class="nav-link btn btn-light mr-1" href="createCarBrand.jsp"> <i class="fa fa-car"></i> </a>
        </c:if>
        <a class="nav-item btn text-white ml-auto" href="${pageContext.request.contextPath}/secure/home.do">Trips</a>
        <a class="nav-item btn text-white ml-2" href="${pageContext.request.contextPath}/notification.do">
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

</nav>
<%--navbar--%>

<br>
<form class="container center" action="${pageContext.request.contextPath}/secure/home.jsp" method="post">
<div class="container center">
    <div class="jumbotron">
        <h1> Create a trip</h1>
        <div class="form-group">
            <label for="from">From</label>
            <input type="text" class="form-control" id="from" placeholder="Type where you´re going from"
                   name="fromTrip" required>
        </div>
        <div class="form-group">
            <label for="to">To</label>
            <input type="text" class="form-control" id="to" placeholder="Type where you´re going to" name="toTrip"
                   required>
        </div>
        <div class="form-group">
            <label for="day">Day</label>
            <input type="date" class="form-control" id="day" placeholder="Please type the departing day" name="dayTrip"
                   value="${localDate}" min="${localDate}" max="2021-12-31" required>
        </div>
        <div class="form-group">
            <label for="time">Time</label>
            <input type="time" class="form-control" id="time" placeholder="Please type the hour you´re departing"
                   value="12:00" name="timeTrip" required>
        </div>
        <div class="form-group">
            <label for="com">Commentary</label>
            <input type="text" class="form-control" id="com" placeholder="Do you want to clarify something?"
                   name="commentTrip" maxlength="200" required>
        </div>
        <div class="form-group">
            <label for="seatsTrip">Available Seats</label>
            <select id="seatsTrip" class="form-control" style="max-width: 60px" name="seatsTrip">
                <option selected value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" onclick="createTrip()">Submit</button>
    </div>
</div>
</form>

<div id="footer">
</div>

<script src="${pageContext.request.contextPath}/bootstrap/js/footer.js" type="text/javascript"></script>
</body>
</html>