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

    h1,h5 {
        color: #4178b3;
        font-family: Roboto, Muli, sans-serif !important;
    }

    .ratingSize {
        font-size: 15px;
    }

    p {
        margin: 0;
    }

</style>

<body>

<h1 class="text-center my-3">Notifications</h1>


<!-- Requests -->
<h5 class="ml-3">Requests</h5>
<!-- esto arranca a repetir aca-->
<div id="requestCards">
    <div class=" row col-8 mt-2 mb-2">
        <!-- requests -->
        <div class="col-5">
            <div class="card" style="height: 8rem ; width: 25rem">
                <div class="container">
                    <div class = "row">
                        <div class="col-3">
                            <p> avatar</p>
                        </div>
                        <div class = "col-9">
                            <p class="card-text jointrip col-11"><span class="font-weight-bold text-dark">Nombre y apellido</span> want toy join your trip to <span class="font-weight-bold text-dark"> lugar </span> in the day <span class="font-weight-light">17/7</span></p>
                        </div>
                        <div class = "col-3">
                        </div>
                        <button type="submit" class="btn btn-success ml-4">Accept</button>
                        <button type="submit" class="btn btn-danger ml-2 ">Deny</button>

                    </div>

                </div>
            </div>
        </div>

    </div>
</div>
<!-- termina de repetir -->
</div>

<!-- end of requests -->

<h5 class="ml-3">Ratings</h5>
<!-- arranca los ratings -->

<div id="ratingCards">
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

<!-- end of ratings -->

<div class="container">
    <ul class="list-group">

        <%--Tipo 1--%>
        <li class="list-group-item">
            <div class="container">
                You have accepted
                <span class="font-weight-bold text-dark"> Name Lastname </span>
                to your trip
                <span class="font-weight-bold text-dark"> FROM </span>
                to
                <span class="font-weight-bold text-dark">TO</span>.

                <span class="font-weight-light font-italic "> date </span>
            </div>
        </li>

        <%--Tipo 2--%>
        <li class="list-group-item">
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <p> avatar </p>
                    </div>
                    <div class="col-11">
                        <div class="col-12">
                            <span class="font-weight-bold text-dark"> Nombre y apellido </span>
                            has accepted your request.
                            <span class="font-weight-light font-italic"> 17/07 </span>
                        </div>
                    </div>
                </div>
            </div>
        </li>

        <%--Tipo 3--%>
        <li class="list-group-item">
            <div class="container">
                <div class="row">
                    <div class="col-1">
                        <p> avatar </p>
                    </div>
                    <div class="col-11">
                        <div class="col-12">
                <span class="font-weight-bold text-dark">
                    Name Lastname </span>
                            has rejected you request.
                            <span class="font-weight-light font-italic "> date </span>
                        </div>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</div>


</body>

</html>
