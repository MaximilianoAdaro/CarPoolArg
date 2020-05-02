<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Home jsp page</title>
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">

</head>
<body>
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="../bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="../bootstrap/js/bootstrap.js"></script>

<div class="navbar navbar-dark bg-dark">
    <nav>
        <a href="/secure/user-list">Listar Usuarios</a>
        <a href="/secure/users.html">Listar Usuarios - AJAX</a>
        <a href="/logout.do">Logout</a>
    </nav>
</div>

<h1 class="text-center">Home</h1>
<a href="profile.html">Go to your profile</a>
<br>

<c:if test="${isAdmin}">
    <form class="form-inline text my-lg-0" action="${pageContext.request.contextPath}/newCarModel.do" method="post">
        <input class="form-control mr-sm-2" type="text" name="car_name" placeholder="Model of the car"
               aria-label="Search">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Submit</button>
    </form>
</c:if>

</body>
</html>