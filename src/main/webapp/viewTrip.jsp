<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="austral.ing.lab1.entity.Trips" %>
<%@ page import="austral.ing.lab1.model.Trip" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="jdk.nashorn.internal.ir.RuntimeNode" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>CarPoolArg</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<!---------------------------------------------->

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
    Long idTrip = Long.parseLong(request.getParameter("trip"));
    Optional<Trip> optionalTrip = Trips.findById(idTrip);

    long userID = -1;
    long driverID = -2;
    boolean isNotOwner;
    boolean isNotPassengerYet = true;
    boolean availableSeats = false;
    if (optionalTrip.isPresent()) {
        Trip trip = optionalTrip.get();
        availableSeats = trip.getAvailableSeats() > 0;
        request.setAttribute("trip", trip);
        request.getSession().setAttribute("tripId", trip.getTripId());
        User driver = trip.getDriver();
        driverID = driver.getUserId();
        request.setAttribute("driver", driver);
        request.setAttribute("driverID", driver.getUserId());
        request.setAttribute("driverCar", driver.getCar());
        request.setAttribute("driverName", driver.getFirstName() + " " + driver.getLastName());

        Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userID = user.getUserId();
            request.setAttribute("userID", user.getUserId());
            request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
            request.setAttribute("avatarPath", user.getAvatarPath());
            request.setAttribute("hasCar", user.getCar() != null);

            List<Trip> trips = Trips.listPassengerTrips(userID, true);
            if (trips.contains(trip))
                isNotPassengerYet = false;
        }
    }

    isNotOwner = (userID != driverID);
    boolean toReturn =  isNotPassengerYet && isNotOwner && availableSeats;
    request.setAttribute("appearJoinTrip", toReturn);
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" id="home" href="${pageContext.request.contextPath}/secure/home.do">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <a class="nav-item btn text-white ml-auto" href="${pageContext.request.contextPath}/secure/home.do">Trips</a>

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

<div class="container mt-5">
    <div class="row">
        <div class="col-4" style="background-color: #93b1d0">
            <img src="${driver.avatarPath}" class="rounded-circle" alt="Avatar of the driver" height="100" width="100">
            ${driverName}
            <div class="col-12" style="font-size: 1.3em; font-weight: bold;">Car details</div>
            <div class="col-12"> Car model: ${driverCar.carModel.name} </div>
            <div class="col-12"> Color: ${driverCar.color} </div>
            <div class="col-12"> Patent: ${driverCar.patent} </div>
            <div></div>
            <div class="col-12" style="max-lines: 7">
                <i class="fa fa-quote-left"></i>
                <span>${trip.comment}</span>
                <i class="fa fa-quote-right"></i>
            </div>
        </div>
        <div class="col-8" style="background-color: #89e3a0">
            Details of the trip
            <%--            <% request.getSession().setAttribute("tripId", idTrip); %>--%>
            <c:if test="${appearJoinTrip}">
                <a class="nav-link btn btn-primary ml-2 col-auto"
                   href="${pageContext.request.contextPath}/newPassenger.do?tripId=${trip.tripId}">Join trip</a>
            </c:if>

        </div>
        <div class="col-12" style="background-color: #d78f8f">
            This is the map on Google
        </div>
    </div>

</div>

</body>
</html>
