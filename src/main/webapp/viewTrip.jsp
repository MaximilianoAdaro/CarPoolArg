<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="austral.ing.lab1.entity.Ratings" %>
<%@ page import="austral.ing.lab1.entity.Trips" %>
<%@ page import="austral.ing.lab1.entity.TripsPassengers" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.Trip" %>
<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body style="background-color: #EEEEEE; font-family: Roboto, Muli, sans-serif !important;">
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="bootstrap/js/bootstrap.js"></script>
<!---------------------------------------------->
<script src="https://maps.googleapis.com/maps/api/js?v=3&sensor=false&libraries=geometry"></script>

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1

    Long idTrip = Long.parseLong(request.getParameter("trip"));
    Optional<Trip> optionalTrip = Trips.findById(idTrip);

    Ratings.setRating();

    long userID = -1;
    long driverID = -2;
    boolean isNotOwner;
    boolean isNotPassengerYetAfter = true;
    boolean availableSeats = false;
    boolean isNotPassengerYetBefore = true;
    boolean isOldTrip = false;
    List<User> passengers = new ArrayList<>();
    if (optionalTrip.isPresent()) {
        Trip trip = optionalTrip.get();
        availableSeats = trip.getAvailableSeats() > 0;
        request.setAttribute("trip", trip);
        request.getSession().setAttribute("tripId", trip.getTripId());
        User driver = trip.getDriver();
        driverID = driver.getUserId();
        request.setAttribute("driver", driver);
        request.setAttribute("driverID", driver.getUserId());
        request.setAttribute("driverEmail", driver.getEmail());
        request.setAttribute("driverCar", driver.getCar());
        request.setAttribute("driverName", driver.getFirstName() + " " + driver.getLastName());
        request.setAttribute("ratingDriver", Ratings.rateUser(driver));
        request.setAttribute("ratingSize", Ratings.getSizeRate(driver));

        Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userID = user.getUserId();
            request.setAttribute("userID", user.getUserId());
            request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
            request.setAttribute("avatarPath", user.getAvatarPath());
            request.setAttribute("hasCar", user.getCar() != null);

            isOldTrip = Trips.listBeforeTrips().contains(trip);
            List<Trip> tripsBefore = Trips.listPassengerTrips(userID, false);
            List<Trip> tripsAfter = Trips.listPassengerTrips(userID, true);

            if (tripsAfter.contains(trip)) {
                isNotPassengerYetAfter = false;
            }
            if (tripsBefore.contains(trip))
                isNotPassengerYetBefore = false;

            passengers = TripsPassengers.listPassengers(trip);
        }
    }

    isNotOwner = (userID != driverID);
    request.setAttribute("passengers", passengers);
    request.setAttribute("passengersIsEmpty", passengers.isEmpty());
    request.setAttribute("isNotOwner", isNotOwner);
    request.setAttribute("isOldTrip", isOldTrip);

    boolean toReturn = isNotPassengerYetAfter && isNotOwner && availableSeats && isNotPassengerYetBefore;
    request.setAttribute("appearJoinTrip", toReturn & !isOldTrip);

    boolean toReject = !isNotPassengerYetAfter && isNotPassengerYetBefore;
    request.setAttribute("appearGoDownTrip", toReject & !isOldTrip);

    boolean payDriver = isNotOwner & isOldTrip;
    request.setAttribute("payDriver", payDriver);
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

<div class="container mt-5 border rounded">
    <div class="row">
        <%--        Seccion izquierda--%>
        <div class="col-3 bg-primary text-white border-right rounded-left viewTripAvatar">
            <img src="${driver.avatarPath}" class="rounded-circle" alt="Avatar of the driver" height="100" width="100">
            ${driverName}
            <c:if test="${ratingSize > 0}">
                <p> ${ratingDriver} <i class="fa fa-star" style="color: yellow;"></i> (${ratingSize} ratings)</p>
            </c:if>
            <c:if test="${ratingSize < 1}">
                <p> (${ratingSize} ratings)</p>
            </c:if>
            <p> Email: ${driverEmail}</p>

            <div class="col-12" style="max-lines: 7">
                <i class="fa fa-quote-left"></i>
                <span>${trip.comment}</span>
                <i class="fa fa-quote-right"></i>
            </div>
        </div>
        <%--    Seccion medio--%>
        <div class="col-6" style="background-color: white">
            <div class="col-8 ml-4">
                <div>
                    <h5 class="card-title text-center" style="color: orange;font-size: 2em;">
                        <i class="fa fa-map-marker"></i>
                        ${trip.fromTrip.name}</h5>
                    <h5 class="card-title text-center" style="color: #1c7430;font-size: 2em;">
                        <i class="fa fa-map-marker"></i>
                        ${trip.toTrip.name}</h5>
                </div>
                <br>
                <div>
                    <p class="card-text"><span>${trip.date.toString()} </span> <span
                            style="color: orange;"> ${trip.time.toString()}</span></p>
                </div>
                <br>
                <p style="font-size: 1.4em"><span class="seatsViewTrip"> ${trip.availableSeats} </span> Available seats
                </p>

                <c:if test="${appearJoinTrip}">
                    <a class="nav-link btn btn-primary ml-2 col-auto requestButton"
                       href="${pageContext.request.contextPath}/newPassenger.do?tripId=${trip.tripId}&state=join">
                        Join trip</a>
                </c:if>
                <c:if test="${appearGoDownTrip}">
                    <a class="nav-link btn btn-primary ml-2 col-auto"
                       href="${pageContext.request.contextPath}/newPassenger.do?tripId=${trip.tripId}&state=goDown">
                        Go down</a>
                </c:if>

                <%--Pay driver--%>
                <c:if test="${payDriver}">
                    <button class="btn btn-warning mt-3 container d-flex" data-toggle="modal" data-target="#buyModal">
                        Pay driver with Mercado Pago
                    </button>
                </c:if>
            </div>
        </div>
        <%--    Seccion derecha--%>
        <div class="col-3 border-left" style="background-color: #ced4da">
            <div class="row ml-2">
                <div class="col-12 carDetailViewTrip">Car details</div>
                <div class="col-12"> Car model: ${driverCar.carModel.name} </div>
                <div class="col-12"> Color: ${driverCar.color} </div>
                <div class="col-12"> Patent: ${driverCar.patent} </div>
            </div>
            <div class="row ml-2">
                <c:if test="${!isNotOwner}">
                    <br>
                    <c:if test="${passengersIsEmpty}">
                        <div class="col-12" style="font-size: 1.3em; font-weight: bold;">
                            There is no passenger in your trip
                        </div>
                    </c:if>
                    <c:if test="${!passengersIsEmpty}">
                        <ul style="list-style-type:none;" class="col-12 mt-1">
                            <li class="passengerViewTrip">Passengers:</li>
                            <c:forEach var="passenger" items="${passengers}">
                                <li style="color: black">
                                    <img src="${passenger.avatarPath}" class="rounded-circle" alt="Your Avatar"
                                         width="30"
                                         height="30"> ${passenger.firstName} ${passenger.lastName}
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </c:if>
            </div>
            <br>
<%--            <div class="row ml-2 col-12">--%>
<%--                Distance to travel: <i id="distanceKM"> </i>--%>
<%--            </div>--%>
        </div>
        <%--mapa--%>
        <div class="col-8 mb-5" id="map" style="height: 400px"></div>
        <%--Indicaciones del mapa--%>
        <div class="col-4" id="indicators"></div>
        <br>
        <br>
    </div>

</div>

<div id="footer">
</div>

<%--Pay driver modal--%>
<div class="modal fade" id="buyModal" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalCenterTitle">Pay driver</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/payment-procedure?tripID=${tripId}" method="post"
                      id="form-buytickets" class="d-flex justify-content-end">
                    <h4>Are you sure you want to pay?</h4>
                    <div class="selectAmount" id="selectAmount">
                        <h3>Amount of money to pay: </h3>
                        <input type="number" min="1" name="amountToPay" id="amountToPay" value="1" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <button class="btn btn-primary" onclick="makePayment()" type="button" id="submit-button">Confirm
                </button>
            </div>
        </div>
    </div>
</div>

<script>
    const makePayment = () => $('#form-buytickets').submit()
</script>

<!-- El request to join toast -->
<div class="toast requestToJoin" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
    <div class="toast-header">
        <strong class="mr-5">CarPoolArg</strong>
        <small class="mr-5">a moment ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        You have just requested to join a trip.
    </div>
</div>
<!-- hasta aca -->
<script>
    $(document).ready(function () {
        $(".requestButton").click(function () {
            $(".requestToJoin").toast('show');
        });
    });
</script>

<script>
    function initMap() {
        let map = new google.maps.Map(document.getElementById("map"));

        let origin = new google.maps.LatLng(${trip.fromTrip.lat}, ${trip.fromTrip.lng});
        // let markerFrom = new google.maps.Marker({
        //     position: origin,
        //     map: map
        // })
        let destination = new google.maps.LatLng(${trip.toTrip.lat}, ${trip.toTrip.lng});
        // let markerTo = new google.maps.Marker({
        //     position: destination,
        //     map: map,
        //     icon: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
        // })

        let bounds = new google.maps.LatLngBounds();
        bounds.extend(origin);
        bounds.extend(destination);
        map.fitBounds(bounds);

        drawDirections(origin, destination, map)
    }

    function drawDirections(origin, destination, map) {
        const directionsService = new google.maps.DirectionsService();
        const directionsDisplay = new google.maps.DirectionsRenderer();

        directionsDisplay.setMap(map)
        directionsDisplay.setPanel(document.getElementById('indicators'));
        directionsService.route({
            origin: origin,
            destination: destination,
            travelMode: google.maps.TravelMode.DRIVING,
        }, (response, status) => {
            if (status === google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            } else {
                alert('Could not display directions due to: ' + status);
            }
        });
    }

    const doc = document.getElementById("distanceKM");
    const distance = google.maps.geometry.spherical.computeDistanceBetween(
        new google.maps.LatLng(${trip.fromTrip.lat}, ${trip.fromTrip.lng}),
        new google.maps.LatLng(${trip.toTrip.lat}, ${trip.toTrip.lng})
    );
    // doc.innerText += parseInt(distance / 1000) + " km";
</script>

<script src="${pageContext.request.contextPath}/bootstrap/js/footer.js" type="text/javascript"></script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB8S7tVTT7SU0K7aCgQ34g1RieAdx6vIdo&callback=initMap&libraries=places&v=weekly">
</script>
</body>
</html>
