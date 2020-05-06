<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns:https="http://java.sun.com/JSP/Page" xmlns:c="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <title>Create a Trip</title>

    <!-- bootstrap -->
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body style="background-image: url(https://gottakeepmovin.com/wp-content/uploads/2015/04/2015-04-28-09.03.59-3.jpg)">

<%
    Optional<User> user = Users.findByEmail(request.getUserPrincipal().getName());
    user.ifPresent(value -> request.getSession().setAttribute("isAdmin", value.getAdministrator()));

%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">  <!-- NavBar -->

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
        <a class="nav-link btn btn-danger col-1" href="${pageContext.request.contextPath}/logout.do">Logout</a>


    </div>

</nav>


<form class="container center">
    <div class="jumbotron">
        <h1> Create a trip</h1>
        <div class="form-group">
            <label for="from">From</label>
            <input type="text" class="form-control" id="from" placeholder="Type where youre going from">
        </div>
        <div class="form-group">
            <label for="to">To</label>
            <input type="text" class="form-control" id="to" placeholder="Type where youre going to">
        </div>
        <div class="form-group">
            <label for="day">Day</label>
            <input type="text" class="form-control" id="day" placeholder="Please type the departing day">
        </div>
        <div class="form-group">
            <label for="hour">Hour</label>
            <input type="text" class="form-control" id="hour" placeholder="Please type the hour youre departing">
        </div>
        <div class="form-group">
            <label for="com">Commentary</label>
            <input type="text" class="form-control" id="com" placeholder="Do you want to clarify something?">
        </div>
        <div class="form-group">
            <label for="inputState">Available Seats</label>
            <select id="inputState" class="form-control" style="max-width: 60px">
                <option selected>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>4+</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>

</body>


</html>
