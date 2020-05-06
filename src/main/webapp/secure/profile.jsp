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
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<style>
    body{
        background-color:lightblue;
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
    Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        request.setAttribute("firstNameUser", user.getFirstName());
        request.setAttribute("lastNameUser", user.getLastName());
        request.setAttribute("emailUser", user.getEmail());
        if(user.getCar()!=null)
        request.setAttribute("carUser", user.getCar().getCarModel().getName());

        request.setAttribute("hasPath", user.getAvatarPath() != null);
        request.setAttribute("avatarPath", user.getAvatarPath());
    }

    final List<CarModel> carModel = CarModels.listAll();
    request.setAttribute("carModels", carModel);

%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">  <!-- NavBar -->

    <a class="navbar-brand" href="">CarPool</a>
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

            <div class="nat-item col-8">
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-1 col-5" type="search" placeholder="Search for Destination"
                           aria-label="Search">

                    <button class="btn btn-outline-success my-2 my-sm-0 col-1 ml-2" type="submit"><i
                            class="fa fa-search"></i></button>
                </form>
            </div>
        </ul>

        <c:if test="${isAdmin}">
            <a class="nav-link btn btn-light mr-1" href="../createCarBrand.html"> <i class="fa fa-car"></i> </a>
        </c:if>
        <a class="nav-link btn btn-danger col-1" href="${pageContext.request.contextPath}/logout.do">Logout</a>


    </div>

</nav>


    ${avatarPath}
        <c:if test="${hasPath}">
            <img src="../images/${avatarPath}" class="rounded-circle" alt="Your Avtar" width="150" height="150">
        </c:if>

<div class="container emp-profile mt-2">
    <form method="post">
        <div class="row">
            <div class="col-md-4">
                <div class="profile-img">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS52y5aInsxSm31CvHOFHWujqUx_wWTS9iM6s7BAm21oEN_RiGoog" alt=""/>
                </div>
            </div>
            <div class="col-md-6">
                <div class="profile-head">
                    <h3>
                        Your Profile
                    </h3>
                </div>
            </div>
            <div class="col-md-2">
                <div action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" id="img" accept="image/*"/>
                    <input type="submit" class="btn btn-primary"/>
                </div>
            </div>
        </div>

            <div class="col-md-8 mt-auto">
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
                            <span><button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#createCar">Edit your car</button></span>
                        </div>
                    </div>
                </div>
            </div>
    </form>
        </div>
    </form>
</div>
</div>
</div>

<form class="container collapse mt-3" id="createCar" action="${pageContext.request.contextPath}/newCar.do" method="post">
    <div class="form-row align-items-center jumbotron">
        <div class="col-auto my-1">
            <h1 class="display-4">Your car</h1>
            <label class="mr-sm-2 sr-only" for="inlineFormCustomSelect">Preference</label>
            <div class="form-inline">
                <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="carModelId">
                    <option selected>Choose a car</option>
                    <c:forEach var="carModel" items="${carModels}">
                        <option value="${carModel.id}">${carModel.name}</option>
                    </c:forEach>
                </select>
                <span class="form-group">
                    <input style="max-width: 300px" type="text" class="form-control" id="color" name="car_color"
                           placeholder="Type your car color" required/>
                    </span>
                <span class="form-group ml-2">
                        <input style="max-width: 300px" type="text" class="form-control" id="patent" name="car_patent"
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