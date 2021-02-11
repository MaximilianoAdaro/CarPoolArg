<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="austral.ing.lab1.entity.Ratings" %>
<%@ page import="austral.ing.lab1.entity.Users" %>
<%@ page import="austral.ing.lab1.model.User" %>
<%@ page import="java.util.Optional" %>
<%@ page import="austral.ing.lab1.entity.Trips" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CarPoolArg Chat</title>
    <script src="${pageContext.request.contextPath}/bootstrap/js/app.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/chat-ws.css">
    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
</head>
<body onload="init()">
<!-- jQuery (Bootstrap plugins depend on it) -->
<script src="../bootstrap/js/jquery-v3.5.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="../bootstrap/js/bootstrap.js"></script>
<!-------------------------------------------------------------------------------------->

<%
    response.setHeader("Cache-Control", "no-store"); //HTTP 1.1

    Ratings.setRating();
    Optional<User> optionalUser = Users.findByEmail(request.getUserPrincipal().getName());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        request.setAttribute("isAdmin", user.getAdministrator());
        request.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
        request.setAttribute("avatarPath", user.getAvatarPath());
        request.setAttribute("hasCar", user.getCar() != null);
        request.setAttribute("trip", Trips.listCurrentTrips(user.getUserId()));
    }

%>

<!-- NavBar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">

    <a class="navbar-brand" id="home" href="${pageContext.request.contextPath}/secure/home.do">CarPool</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <a class="nav-item btn text-white ml-auto" href="${pageContext.request.contextPath}/chat.do">
            <i class="fa fa-comments-o" aria-hidden="true"></i>
        </a>
        <a class="nav-item btn text-white ml-2" href="${pageContext.request.contextPath}/secure/home.do">Trips</a>
        <a class="nav-item btn text-white ml-2" href="${pageContext.request.contextPath}/notification.do">
            <i class="fa fa-bell"></i>
        </a>

        <div class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${userName}
                <img src="${avatarPath}" class="rounded-circle" alt="" width="30" height="30">
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
</nav>

<div class="container-fluid mainChat" id="main">
    <div class="ks-messenger row">
        <!---------------------------------------LEFT---------------------------->
        <div class="ks-discussions col-3 px-0">
            <div class="ks-search">
                <div class="input-icon icon-right icon icon-lg icon-color-primary">
                    <input id="input-group-icon-text" type="text" class="form-control" placeholder="Search">
                    <span class="icon-addon">
                <span class="la la-search"></span>
                        </span>
                </div>
            </div>
            <div class="ks-body" style="overflow: auto; padding: 0;">
                <div class="jspContainer">
                    <div class="jspPane" style="padding: 0; top: 0;">
                        <ul class="ks-items">
                            <li class="ks-item ks-active">
                                <a href="#">
                                    <span class="ks-group-amount">3</span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Group Chat
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png"
                                                 width="18" height="18" class="rounded-circle"> The weird future
                                            of movie theater food
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="ks-item ks-unread">
                                <a href="#">
                                    <span class="ks-group-amount">5</span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Eric George
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar2.png"
                                                 width="18" height="18" class="rounded-circle"> Why didn't he
                                            come and talk to me...
                                        </div>
                                    </div>
                                </a>
                            </li>
                            <li class="ks-item">
                                <a href="#">
                                        <span class="ks-avatar">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar3.png" width="36"
                                                 height="36">
                                        </span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Eric George
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">Why didn't he come and talk to me himse...</div>
                                    </div>
                                </a>
                            </li>
                            <li class="ks-item">
                                <a href="#">
                                        <span class="ks-avatar">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar4.png" width="36"
                                                 height="36">
                                            <span class="badge badge-pill badge-danger ks-badge ks-notify">7</span>
                                        </span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Eric George
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">Why didn't he come and talk to me himse...</div>
                                    </div>
                                </a>
                            </li>
                            <li class="ks-item">
                                <a href="#">
                                            <span class="ks-group-amount">3 <span
                                                    class="badge badge-pill badge-danger ks-badge ks-notify">7</span></span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Eric George
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">Why didn't he come and talk to me himse...</div>
                                    </div>
                                </a>
                            </li>
                            <li class="ks-item ks-closed">
                                <a href="#">
                                        <span class="ks-avatar">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar6.png" width="36"
                                                 height="36">
                                        </span>
                                    <div class="ks-body">
                                        <div class="ks-name">
                                            Brian Diaz
                                            <span class="ks-datetime">just now</span>
                                        </div>
                                        <div class="ks-message">The weird future of movie theater food</div>
                                    </div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!---------------------------------------CENTER---------------------------->

        <div class="ks-messages col-6 px-0">
            <div class="ks-header">
                <div class="ks-description">
                    <div class="ks-name">Chat name</div>
                    <div class="ks-amount">2 members</div>
                </div>
            </div>
            <div class="ks-body" style="overflow: auto; padding: 0;">
                <div class="jspContainer">
                    <div class="jspPane" style="padding: 0; top: 0;">
                        <!--------------------------------------CHAT COMIENZA----------------------------->
                        <ul class="ks-items">
                            <li class="ks-item ks-self">
                                    <span class="ks-avatar ks-offline">
                                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="36"
                                             height="36" class="rounded-circle">
                                    </span>
                                <div class="ks-body">
                                    <div class="ks-header">
                                        <span class="ks-name">Brian Diaz</span>
                                        <span class="ks-datetime">6:46 PM</span>
                                    </div>
                                    <div class="ks-message">The weird future of movie theater food</div>
                                </div>
                            </li>
                            <li class="ks-item ks-from">
                                    <span class="ks-avatar ks-online">
                                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" width="36"
                                             height="36" class="rounded-circle">
                                    </span>
                                <div class="ks-body">
                                    <div class="ks-header">
                                        <span class="ks-name">Brian Diaz</span>
                                        <span class="ks-datetime">6:46 PM</span>
                                    </div>
                                    <div class="ks-message">The weird future of movie theater food</div>
                                </div>
                            </li>
                            <li class="ks-item ks-self">
                                    <span class="ks-avatar ks-offline">
                                        <img src="https://bootdey.com/img/Content/avatar/avatar4.png" width="36"
                                             height="36" class="rounded-circle">
                                    </span>
                                <div class="ks-body">
                                    <div class="ks-header">
                                        <span class="ks-name">Brian Diaz</span>
                                        <span class="ks-datetime">6:46 PM</span>
                                    </div>
                                    <div class="ks-message">The weird future of movie theater food</div>
                                </div>
                            </li>
                            <li class="ks-item ks-from ks-unread">
                                    <span class="ks-avatar ks-online">
                                        <img src="https://bootdey.com/img/Content/avatar/avatar5.png" width="36"
                                             height="36" class="rounded-circle">
                                    </span>
                                <div class="ks-body">
                                    <div class="ks-header">
                                        <span class="ks-name">Brian Diaz</span>
                                        <span class="ks-datetime">1 minute ago</span>
                                    </div>
                                    <div class="ks-message">
                                        The weird future of movie theater food

                                        <ul class="ks-files">
                                            <li class="ks-file">
                                                <a href="#">
                                                        <span class="ks-thumb">
                                                            <span class="ks-icon la la-file-word-o text-info"></span>
                                                        </span>
                                                    <span class="ks-info">
                                                            <span class="ks-name">Project...</span>
                                                        <span class="ks-size">15 kb</span>
                                                        </span>
                                                </a>
                                            </li>
                                            <li class="ks-file">
                                                <a href="#">
                                                        <span class="ks-thumb">
                                                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png"
                                                                 width="36" height="36">
                                                        </span>
                                                    <span class="ks-info">
                                                            <span class="ks-name">photo.jpg</span>
                                                        <span class="ks-size">312 kb</span>
                                                        </span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                            <li class="ks-separator">Today</li>
                            <li class="ks-item ks-self">
                                    <span class="ks-avatar ks-offline">
                                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" width="36"
                                             height="36" class="rounded-circle">
                                    </span>
                                <div class="ks-body">
                                    <div class="ks-header">
                                        <span class="ks-name">Brian Diaz</span>
                                        <span class="ks-datetime">6:46 PM</span>
                                    </div>
                                    <div class="ks-message">
                                        Lorem Ipsum is simply dummy text of the printing and typesetting
                                        industry. Lorem Ipsum has been the industry's standard dummy text ever
                                        since the 1500s, when an unknown printer took a galley of type and
                                        scrambled it to make a type specimen book. It has survived not only five
                                        centuries, but also the leap into electronic typesetting, remaining
                                        essentially unchanged. It was popularised in the 1960s with the release
                                        of Letraset sheets containing Lorem Ipsum passages, and more recently
                                        with desktop publishing software like Aldus PageMaker including versions
                                        of Lorem Ipsum
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <!--------------------------------------CHAT TERMINA----------------------------->
                    </div>
                </div>
            </div>
            <div class="ks-footer">
                <textarea class="form-control" placeholder="Type something..."></textarea>
                <div class="ks-controls">
                    <button class="btn btn-primary">Send</button>
                    <a href="#" class="la la-paperclip ks-attachment"></a>
                </div>
            </div>
        </div>

        <!---------------------------------------RIGHT---------------------------->

        <div class="ks-info col-3 px-0">
            <div class="ks-header">
                User Info
            </div>
            <div class="ks-body">
                <div class="ks-item ks-user">
                        <span class="ks-avatar ks-online">
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="36" height="36"
                                 class="rounded-circle">
                        </span>
                    <span class="ks-name">
                            Lauren Sandoval
                        </span>
                </div>
                <div class="ks-item">
                    <div class="ks-name">Username</div>
                    <div class="ks-text">
                        @lauren.sandoval
                    </div>
                </div>
                <div class="ks-item">
                    <div class="ks-name">Email</div>
                    <div class="ks-text">
                        lauren.sandoval@example.com
                    </div>
                </div>
                <div class="ks-item">
                    <div class="ks-name">Phone Number</div>
                    <div class="ks-text">
                        +1(555) 555-555
                    </div>
                </div>
            </div>
            <div class="ks-footer">
                <div class="ks-item">
                    <div class="ks-name">Created</div>
                    <div class="ks-text">
                        Febriary 17, 2016 at 11:38 PM
                    </div>
                </div>
                <div class="ks-item">
                    <div class="ks-name">Last Activity</div>
                    <div class="ks-text">
                        1 minute ago
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>