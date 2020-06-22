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

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/style.css">

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<style>
    body {
        background-color: #EEEEEE;
        font-family: Roboto, Muli, sans-serif !important;
    }

    .notif{
        background-color: white;
    }

    .ratingsize{
        font-size: 15px;
    }


</style>

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
        <a class="nav-item btn text-white ml-2" href="${pageContext.request.contextPath}/notification.jsp"><i class="fa fa-bell"></i></a>

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

<div class="notif container mt-5 border border-secondary rounded">
    <a class="font-weight-bold text-dark" href="${pageContext.request.contextPath}/myTrips.do">
        Name lastname has requested to join to one of your trips.
        <span class="font-weight-light font-italic "> date</span> </a>
</div>

<div class="collapse multi-collapse" id="join-card">

<!-- cuando alguien se quiere subir -->
<div class=" row col-8 mt-2">

    <!-- esto arranca a repetir aca-->
    <div class="col-sm-6">
        <div class="card">
            <div class="card-body">
                <p class="card-text jointrip col-11"><span class="font-weight-bold text-dark">Nombre y apellido</span> want toy join your trip to <span class="font-weight-bold text-dark"> lugar </span> in the day <span class="font-weight-light">17/7</span></p>
                <a href="#" class="btn btn-success col-3 jointrip">Accept</a>
                <a href="#" class="btn btn-danger col-3 jointrip">Deny</a>
            </div>
        </div>
    </div>

    <!-- termina de repetir aca -->

</div>

<!-- y aca termina-->
</div>

<div class="notif container mt-0 border border-secondary rounded">
    <a class="font-weight-bold text-dark" href="${pageContext.request.contextPath}/myTrips.do">
        You still have a trip to classify.
        <span class="font-weight-light font-italic "> date</span> </a>
</div>
<div class="collapse multi-collapse" id="ratingCards">
    <div class=" row col-8 mt-2 mb-2">
        <!-- rate -->
        <!-- esto arranca a repetir aca-->
        <div class="col-5">
            <div class="card" style="height: 8rem ; width: 25rem">
                <div class="container">
                    <div class = "row">
                        <div class="col-3">
                            <p> avatar</p>
                        </div>
                        <div class = "col-9">
                            <p class="card-text ratingsize "> You havent rated <span class="font-weight-bold text-dark">Nombre y apellido</span> from your trip to <span class="font-weight-bold text-dark"> lugar </span> in the day <span class="font-weight-light">17/7</span></p>
                        </div>
                        <div class = "col-3">
                        </div>
                        <div class ="col-9 mt-0">
                            <div class="form-check-inline mt-1">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" value="1">1
                                </label>
                            </div>
                            <div class="form-check-inline">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" value="2">2
                                </label>
                            </div>
                            <div class="form-check-inline">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" value="3">3
                                </label>
                            </div>
                            <div class="form-check-inline">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" value="4">4
                                </label>
                            </div>
                            <div class="form-check-inline">
                                <label class="form-check-label">
                                    <input type="checkbox" class="form-check-input" value="5">5
                                </label>
                            </div>
                            <button type="submit" class="btn btn-success">Rate</button>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
    <!-- termina de repetir -->
</div>
</div>

<!-- y aca termina-->

<div class="notif container mt-0 border border-secondary rounded">
    <a class="font-weight-bold text-dark" href="${pageContext.request.contextPath}/myTrips.do">
        Your trip to "lugar" has been finished.
        <span class="font-weight-light font-italic "> date</span> </a>
</div>

</body>
</html>
