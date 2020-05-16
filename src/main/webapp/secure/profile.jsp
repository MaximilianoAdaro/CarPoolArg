<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.model.CarModel" %>
<%@ page import="java.util.List" %>
<%@ page import="austral.ing.lab1.entity.CarModels" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>CarPoolArg</title>
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <!-- jQuery (Bootstrap plugins depend on it) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <style>
        body {
            background-color: #EEEEEE;
        }
    </style>
</head>
<body>

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1

    Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        request.setAttribute("firstNameUser", user.getFirstName());
        request.setAttribute("lastNameUser", user.getLastName());
        request.setAttribute("emailUser", user.getEmail());
        if (user.getCar() != null)
            request.setAttribute("carUser", user.getCar().getCarModel().getName());
        request.setAttribute("avatarPath", user.getAvatarPath());
    }

    final List<CarModel> carModel = CarModels.listAll();
    request.setAttribute("carModels", carModel);

%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">  <!-- NavBar -->

    <a class="navbar-brand" href="${pageContext.request.contextPath}/secure/home.do">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <c:if test="${isAdmin}">
            <a class="nav-link btn btn-light mr-1" href="../createCarBrand.html"> <i class="fa fa-car"></i> </a>
        </c:if>

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

<div class="container emp-profile mt-2">
    <div class="row">
        <div class="col-md-4">
            <div class="profile-img">
                <img src="${avatarPath}" class="rounded-circle" alt="Your Avatar" width="150" height="150">
            </div>
        </div>
        <div class="col-md-6">
            <div class="profile-head">
                <h3>
                    Your Profile
                </h3>
            </div>
        </div>
        <div class="col-md-6 my-3">
            <form method="post" action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data">
                <input class="ml-3 mb-3" style="width:220px" type="file" name="file" id="img" accept="image/*"/>
                <button type="submit" class="btn btn-primary"> Submit</button>
            </form>
        </div>
    </div>

    <div class="col-md-8 mt-auto mt-5">
        <div class="tab-content profile-tab" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="row">
                    <div class="col-md-6">
                        <label>First Name</label>
                    </div>
                    <div class="col-md-6">
                        <p>${firstNameUser}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label>Last Name</label>
                    </div>
                    <div class="col-md-6">
                        <p>${lastNameUser}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label>Email</label>
                    </div>
                    <div class="col-md-6">
                        <p>${emailUser}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <label>Car</label>
                    </div>
                    <div class="col-md-6">
                        <p>${carUser}</p>
                    </div>
                    <div class="mr-5">
                        <form action="${pageContext.request.contextPath}/carABM.do" method="get">
                            <span class="ml-2">
                                <button type="submit" class="btn btn-primary">Delete car</button>
                            </span>
                        </form>
                    </div>
                    <span><button type="button" class="btn btn-primary" data-toggle="collapse"
                                  data-target="#createCar">Edit your car</button></span>
                </div>
            </div>
        </div>
    </div>
</div>

<form class="container collapse mt-3" id="createCar" action="${pageContext.request.contextPath}/carABM.do"
      method="post">
    <div class="form-row align-items-center jumbotron">
        <div class="col-auto my-1">
            <h1 class="display-4">Your car</h1>
            <label class="mr-sm-2 sr-only" for="inlineFormCustomSelect">Preference</label>
            <div class="form-inline">
                <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="carModelId">
                    <option selected>Choose a car</option>
                    <c:forEach var="carModel" items="${carModels}">
                        <option value="${carModel.carModelId}">${carModel.name}</option>
                    </c:forEach>
                </select>
                <span class="form-group">
                    <label for="color"></label><input style="max-width: 300px" type="text" class="form-control"
                                                      id="color" name="car_color"
                                                      placeholder="Type your car color" required/>
                    </span>
                <span class="form-group ml-2">
                        <label for="patent"></label><input style="max-width: 300px" type="text" class="form-control"
                                                           id="patent" name="car_patent"
                                                           placeholder="Type your car patent" required/>
                    </span>
                <span class="ml-2">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </span>
            </div>
        </div>
    </div>
</form>

</body>
</html>