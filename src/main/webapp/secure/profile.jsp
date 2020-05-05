<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="../bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="../bootstrap/js/bootstrap.js"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/secure/home.jsp">Home <span
                        class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Dropdown
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav> <!--Navbar-->

<div class="col-md-8 container">
    <div class="tab-content profile-tab align-items-center jumbotron" id="myTabContent">
        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
            <div class="row">
                <div class="col-md-6">
                    <label>First Name</label>
                </div>
                <div class="col-md-6">
                    <p>your name</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label>Last Name</label>
                </div>
                <div class="col-md-6">
                    <p>holanda</p>
                </div>

            </div>
            <div class="row">
                <div class="col-md-6">
                    <label>Email</label>
                </div>
                <div class="col-md-6">
                    <p>que hacelga</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label>Car</label>
                </div>
                <div class="col-md-6">
                    <p>your car</p>
                </div>
                <span><button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#createCar">Edit your car</button></span>
            </div>
        </div>

    </div>
</div>

<form class="container collapse" id="createCar">
    <div class="form-row align-items-center jumbotron">
        <div class="col-auto my-1">
            <h1 class="display-4">Your car</h1>
            <label class="mr-sm-2 sr-only" for="inlineFormCustomSelect">Preference</label>
                <div class="form-inline">
                    <select class="custom-select mr-sm-2" id="inlineFormCustomSelect">
                        <option selected>Choose a car please</option>
                        <option value="1">auto uno</option>
                        <option value="2">holanda</option>
                        <option value="3">bro</option>
                     </select>

                    <span class="form-group">
                        <input style="max-width: 300px" type="text" class="form-control" id="color" name="car_color" placeholder="Type your car color please"/>
                    </span>

                    <span class="form-group ml-2">
                        <input style="max-width: 300px" type="text" class="form-control" id="patent" name="car_patent" placeholder="Type your car patent please"/>
                    </span>

                    <span class="ml-2">
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </span>

                </div>


        </div>

    </div>
</form>



<br><br>

<form class="form-inline text my-lg-0" action="${pageContext.request.contextPath}/newCarModel.do" method="post">
    <input class="form-control mr-sm-2" type="text" name="car_name" placeholder="Model of the car" aria-label="Search">
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Submit</button>
</form>
<br>
<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit"/>
</form>
<br>
<div class="crop-img">
    <c:if test="${hasPath}">
        <img src="..\\images\\ ${avatarPath}" width="200" height="200" alt="">
    </c:if>
</div>

</body>
</html>