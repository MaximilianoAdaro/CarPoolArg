<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Ratings" %>
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

    h1, h3 {
        color: #4178b3;
        font-family: Roboto, Muli, sans-serif !important;
    }

    p {
        margin: 0;
    }
    .ratingpassenger, .ratingdriver,.acceptpassenger, .rejectpassenger {
        backdrop-filter: blur();
    }
    .bs-example{
        position:fixed;
        top: 15px; right: 10px;
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

    Ratings.setRating();
    Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        request.setAttribute("userId", user.getUserId());
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
</nav> <!-- NavBar -->

<div class="container">
    <!-- Requests -->
    <c:if test="${tripPassNotEmpty}">
        <h3 class="ml-3">Pending requests</h3>
        <div class="list-group">
        <c:forEach var="tripsPassenger" items="${tripsPassengers}">
            <div class="list-group-item list-group-item-action">
                <div class="container">
                    <div class="row">
                        <div class="col-1">
                            <img src="${tripsPassenger.passenger.avatarPath}"
                                 class="rounded-circle" alt="Your Avatar" width="50" height="50">
                        </div>
                        <div class="col-11">
                            <div class="col-12">
                                <p class="card-text col-11"><span class="font-weight-bold text-dark">
                                        ${tripsPassenger.passenger.firstName} ${tripsPassenger.passenger.lastName} </span>want
                                    to join your trip from <span
                                            class="font-weight-bold text-dark"> ${tripsPassenger.trip.fromTrip} </span>
                                    to
                                    <span class="font-weight-bold text-dark"> ${tripsPassenger.trip.toTrip.name} </span>
                                    in the day <span
                                            class="font-weight-light"> ${tripsPassenger.trip.date} </span>
                                </p>
                            </div>
                            <div>
                                <a href="${pageContext.request.contextPath}/passenger.do?user=${tripsPassenger.passenger.userId}&tripId=${tripsPassenger.trip.tripId}&case=accepted"
                                   role="button" onClick="window.location.reload();" type="submit"
                                   class="btn btn-success ml-4 acceptbutton"> Accept
                                </a>
                                <a href="${pageContext.request.contextPath}/passenger.do?user=${tripsPassenger.passenger.userId}&tripId=${tripsPassenger.trip.tripId}&case=rejected"
                                   role="button" onClick="window.location.reload();" type="submit"
                                   class="btn btn-danger ml-2 "> Deny
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>

        </c:forEach>
    </c:if>
    <!-- end of requests -->

    <!-- Starts ratings -->
    <c:if test="${ratingAsDriver}">
        <br>
        <h3 class="ml-3">Pending ratings as driver</h3>
        <%--        Rating as driver--%>
        <div class="list-group">
            <!-- esto arranca a repetir aca-->
            <c:forEach var="rateAsDriver" items="${ratingsUserAsDriver}">

                <form class="list-group-item list-group-item-action" data-toggle="modal" data-target=".ratemodal"
                      action="${pageContext.request.contextPath}/newRating.do?idDriver=${userId}&idPassenger=${rateAsDriver.idPassenger.userId}&idTrip=${rateAsDriver.idTrip.tripId}&isDriver=true"
                      method="post">
                    <div class="container">
                        <div class="row">
                            <div class="col-1">
                                <img src="${rateAsDriver.idPassenger.avatarPath}"
                                     class="rounded-circle" alt="Your Avatar" width="50" height="50">
                            </div>
                            <div class="col-11">
                                <div class="col-12">
                                    <p class="card-text"> Rate <span class="font-weight-bold text-dark">
                                        ${rateAsDriver.idPassenger.firstName} ${rateAsDriver.idPassenger.lastName}</span>
                                        in your trip from
                                        <span class="font-weight-bold text-dark"> ${rateAsDriver.idTrip.fromTrip.name} </span>
                                        to
                                        <span class="font-weight-bold text-dark"> ${rateAsDriver.idTrip.toTrip.name} </span>
                                        the day <span class="font-weight-light"> ${rateAsDriver.idTrip.date} </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>

        <!--RatingModal -->
        <div class="modal fade ratemodal">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Rate this passenger</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <!-- Modal body -->
                    <div class="modal-body">
                        Please rate this passenger from 1 to 5. 1 being a bad passenger and 5 being an excelent
                        passenger.
                        <br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                   id="inlineRadio1"
                                   value="1"> <label class="form-check-label"
                                                     for="inlineRadio1">1</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                   id="inlineRadio2"
                                   value="2"> <label class="form-check-label"
                                                     for="inlineRadio2">2</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                   id="inlineRadio3"
                                   value="3"> <label class="form-check-label"
                                                     for="inlineRadio3">3</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                   id="inlineRadio4"
                                   value="4"> <label class="form-check-label"
                                                     for="inlineRadio4">4</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                   id="inlineRadio5"
                                   value="5"> <label class="form-check-label"
                                                     for="inlineRadio5">5</label>
                        </div>
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success ratingButtonpassenger" data-dismiss="modal"> Rate</button>
                        <a type="button" class="btn btn-secondary" data-dismiss="modal">Close</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <!-- termina de repetir -->

    <c:if test="${ratingAsPassenger}">
        <br>
        <%--        Rating as passenger--%>
        <h3 class="ml-3">Rating as passenger</h3>
        <div class="list-group">
            <!-- esto arranca a repetir aca-->
            <c:forEach var="rateAsPassenger" items="${ratingsUserAsPassenger}">
                <form class="list-group-item list-group-item-action" data-toggle="modal" data-target=".ratemodaldriver"
                      action="${pageContext.request.contextPath}/newRating.do?idDriver=${rateAsPassenger.idDriver.userId}&idPassenger=${userId}&idTrip=${rateAsPassenger.idTrip.tripId}&isDriver=false"
                      method="post">
                    <div class="container">
                        <div class="row">
                            <div class="col-1">
                                <img src="${rateAsPassenger.idDriver.avatarPath}"
                                     class="rounded-circle" alt="Your Avatar" width="50" height="50">
                            </div>
                            <div class="col-11">
                                <div class="col-12">
                                    <p class="card-text"> Rate <span class="font-weight-bold text-dark">
                                        ${rateAsPassenger.idDriver.firstName} ${rateAsPassenger.idDriver.lastName}</span>
                                        in his/her trip from
                                        <span class="font-weight-bold text-dark"> ${rateAsPassenger.idTrip.fromTrip.name} </span>
                                        to
                                        <span class="font-weight-bold text-dark"> ${rateAsPassenger.idTrip.toTrip.name} </span>
                                        the day <span
                                                class="font-weight-light"> ${rateAsPassenger.idTrip.date} </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <!--RatingModal -->
                <div class="modal fade ratemodaldriver">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title">Rate this driver</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <!-- Modal body -->
                            <div class="modal-body">
                                Please rate this driver from 1 to 5. 1 being a bad driver and 5 being an excelent one.
                                <br>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                           id="inlineRadio1Driver"
                                           value="1"> <label class="form-check-label"
                                                             for="inlineRadio1Driver">1</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                           id="inlineRadio2Driver"
                                           value="2"> <label class="form-check-label"
                                                             for="inlineRadio2Driver">2</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                           id="inlineRadio3Driver"
                                           value="3"> <label class="form-check-label"
                                                             for="inlineRadio3Driver">3</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                           id="inlineRadio4Driver"
                                           value="4"> <label class="form-check-label"
                                                             for="inlineRadio4Driver">4</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions"
                                           id="inlineRadio5Driver"
                                           value="5"> <label class="form-check-label"
                                                             for="inlineRadio5Driver">5</label>
                                </div>
                            </div>
                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success ratingButtondriver" data-dismiss="modal"> Rate</button>
                                <a type="button" class="btn btn-secondary" data-dismiss="modal">Close</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- termina de repetir -->
        </div>
    </c:if>
    <!-- end of ratings -->
</div>

<h1 class="text-center my-3"> Notifications </h1>
<%--Notificaciones--%>
<div class="container">
    <ul class="list-group">
        <c:forEach var="notifList" items="${notifList}">
            <%--Tipo 1--%>
            <c:if test="${notifList.type == 1}">
                <li class="list-group-item">
                    <div class="container">
                        <div class="row">
                            <div class="col-1">
                                <img src="${notifList.notification.idUser.avatarPath}"
                                     class="rounded-circle" alt="Your Avatar" width="50" height="50">
                            </div>
                            <div class="col-11">
                                <div class="col-12">
                                    You have accepted
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idUser.firstName}
                                            ${notifList.notification.idUser.lastName}</span>
                                    to your trip from
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.fromTrip.name} </span>
                                    to
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.toTrip.name} </span>.

                                    <span class="font-weight-light font-italic "> ${notifList.notification.date} </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </c:if>

            <%--Tipo 2--%>
            <c:if test="${notifList.type == 2}">
                <li class="list-group-item">
                    <div class="container">
                        <div class="row">
                            <div class="col-1">
                                <img src="${notifList.notification.idTrip.driver.avatarPath}"
                                     class="rounded-circle" alt="Your Avatar" width="50" height="50">
                            </div>
                            <div class="col-11">
                                <div class="col-12">
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.driver.firstName}
                                            ${notifList.notification.idTrip.driver.lastName} </span>
                                    has accepted your request in the trip from
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.fromTrip.name} </span>
                                    to
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.toTrip.name} </span>.
                                    <span class="font-weight-light font-italic"> ${notifList.notification.date} </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </c:if>

            <%--Tipo 3--%>
            <c:if test="${notifList.type == 3}">
                <li class="list-group-item">
                    <div class="container">
                        <div class="row">
                            <div class="col-1">
                                <img src="${notifList.notification.idTrip.driver.avatarPath}"
                                     class="rounded-circle" alt="Your Avatar" width="50" height="50">
                            </div>
                            <div class="col-11">
                                <div class="col-12">
                                    <span class="font-weight-bold text-dark">
                                            ${notifList.notification.idTrip.driver.firstName}
                                            ${notifList.notification.idTrip.driver.lastName}</span>
                                    has rejected your request in the trip from
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.fromTrip.name} </span>
                                    to
                                    <span class="font-weight-bold text-dark"> ${notifList.notification.idTrip.toTrip.name} </span>.
                                    <span class="font-weight-light font-italic "> ${notifList.notification.date} </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </c:if>
        </c:forEach>
    </ul>
    <br>
    <br>
</div>

<div id="footer">
</div>

<div class = "bs-example">
<!-- El rating passenger toast -->
<div class="toast ratingpassenger" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
    <div class="toast-header">
        <strong class="mr-5">CarPoolArg</strong>
        <small class="mr-5">a moment ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        You have just rated a passenger.
    </div>
</div>
<!-- hasta aca -->


<!-- El rating diver toast -->
<div class="toast ratingdriver" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
    <div class="toast-header">
        <strong class="mr-5">CarPoolArg</strong>
        <small class="mr-5">a moment ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        You have just rated a driver.
    </div>
</div>
<!-- hasta aca -->

<!-- El accept passenger toast -->
<div class="toast acceptpassenger" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
    <div class="toast-header">
        <strong class="mr-5">CarPoolArg</strong>
        <small class="mr-5">a moment ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        You have just rated a passenger.
    </div>
</div>
<!-- hasta aca -->

<!-- El deny passenger toast -->
<div class="toast rejectpassenger" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
    <div class="toast-header">
        <strong class="mr-5">CarPoolArg</strong>
        <small class="mr-5">a moment ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        You have just rejected a passenger.
    </div>
</div>
<!-- hasta aca -->
</div>

<script>
    $(document).ready(function () {
        $(".ratingButtonpassenger").click(function () {
            $(".ratingpassenger").toast('show');
        });
    });

    $(document).ready(function () {
        $(".ratingButtondriver").click(function () {
            $(".ratingdriver").toast('show');
        });
    });

    $(document).ready(function () {
        $(".acceptbutton").click(function () {
            $(".acceptpassenger").toast('show');
        });
    });

    $(document).ready(function () {
        $(".rejectbutton").click(function () {
            $(".rejectpassenger").toast('show');
        });
    });
</script>

<div id="footer">
</div>

<script src="${pageContext.request.contextPath}/bootstrap/js/footer.js" type="text/javascript"></script>

</body>
</html>