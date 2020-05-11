<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.Trip" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="austral.ing.lab1.entity.Trips" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="austral.ing.lab1.service.HibernateProxyTypeAdapter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
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

    .portfolio {
        margin-top: 80px;
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

<header class="navbar navbar-expand-lg fixed-top navbar-light bg-light">

    <a class="navbar-brand" id="home" href="">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto container-fluid">

            <li>
                <a class="nav-item btn btn-light ml-auto" href="${pageContext.request.contextPath}/secure/home.do">
                    <i class="fa fa-home"></i></a>

                <a class="nav-item btn btn-light" href="${pageContext.request.contextPath}/secure/profile.do"><i
                        class="fa fa-user" aria-hidden="true"></i></a>

            </li>

        </ul>

        <c:if test="${isAdmin}">
            <a class="nav-link btn btn-light mr-1" href="../createCarBrand.html"> <i class="fa fa-car"></i> </a>
        </c:if>
        <a class="nav-link btn btn-light mr-2" href="${pageContext.request.contextPath}/createTrip.jsp"> <i
                class="fa fa-plus"></i> </a>
        <a class="nav-link btn btn-danger col-1" href="${pageContext.request.contextPath}/logout.do">Logout</a>

    </div>

</header> <!-- NavBar -->

<div class="container text-center portfolio">
    <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/filterHome.do" method="get">
        <input class="form-control mr-sm-1 col-4 mr-4" type="text" placeholder="From" id="fromTrip"
               name="fromTrip" aria-label="Search">
        <input class="form-control mr-sm-1 col-4" type="text" placeholder="To" id="toTrip"
               name="toTrip" aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0 col-1 ml-2" type="submit" value="search" id="buttonTrip">
            <i class="fa fa-search"></i>
        </button>
    </form>
</div>

<div class="container-fluid mt-5">
    <div class="row justify-content-center" id="result">
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



<%--<script>--%>
<%--    const fromTrip = document.querySelector('#fromTrip');--%>
<%--    const toTrip = document.querySelector('#toTrip');--%>
<%--    const button = document.querySelector('#buttonTrip');--%>
<%--    const result = document.querySelector('#result');--%>
<%--    const trips = JSON.parse(${trip});--%>

<%--    console.log(trips);--%>

<%--    const filter = () => {--%>
<%--        result.innerHTML = '';--%>

<%--        const fromText = fromTrip.value.toLowerCase();--%>
<%--        const toText = fromTrip.value.toLowerCase();--%>

<%--        for (let trip of trips){--%>
<%--            let fromName = trip.fromTrip.toLowerCase();--%>
<%--            let toName = trip.toTrip.toLowerCase();--%>



<%--        }--%>

<%--    }--%>

<%--    button.addEventListener('click',filter);--%>

<%--</script>--%>
</body>
</html>
