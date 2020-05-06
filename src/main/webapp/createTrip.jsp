<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create a Trip</title>

    <!-- bootstrap -->
    <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Font awesome icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<%


%>

<form class="container center" action="${pageContext.request.contextPath}/createTrip.do" method="post">
    <div class="jumbotron">
        <h1> Create a trip</h1>
        <div class="form-group">
            <label for="from">From</label>
            <input type="text" class="form-control" id="from" placeholder="Type where you´re going from"
                   name="fromTrip" required>
        </div>
        <div class="form-group">
            <label for="to">To</label>
            <input type="text" class="form-control" id="to" placeholder="Type where you´re going to" name="toTrip"
                   required>
        </div>
        <div class="form-group">
            <label for="day">Day</label>
            <input type="date" class="form-control" id="day" placeholder="Please type the departing day" name="dayTrip"
                   value="2020-05-06" min="2020-05-06" max="2021-12-31" required>
        </div>
        <div class="form-group">
            <label for="time">Time</label>
            <input type="time" class="form-control" id="time" placeholder="Please type the hour you´re departing"
                   name="timeTrip" required>
        </div>
        <div class="form-group">
            <label for="com">Commentary</label>
            <input type="text" class="form-control" id="com" placeholder="Do you want to clarify something?"
                   name="commentTrip">
        </div>
        <div class="form-group">
            <label for="inputState">Available Seats</label>
            <select id="inputState" class="form-control" style="max-width: 60px" name="seatsTrip">
                <option selected value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
            </select>
        </div>
    </div>
    <span class="ml-2">
        <button type="submit" class="btn btn-primary">Submit</button>
    </span>
</form>

</body>
</html>