let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function () {
    if (this.readyState === 4 && this.status === 200) {
        let trips = JSON.parse(this.response);
        let homeTrip = document.getElementById("tripsHome");
        printTripsHome(trips, homeTrip)
    }
}
xhttp.open("GET", "/homeTrip", true);
xhttp.send();

function printTripsHome(tripList, docTrip) {
    docTrip.innerHTML = '';
    if (tripList.length === 0) {
        docTrip.innerHTML = `
        <p class="alert alert-warning my-4" style="width: 400px"> You do not have trips available</p>
        `;
    }
    for (const trip of tripList) {
        docTrip.innerHTML +=
            `
            <div class="col-auto mb-3">
                <div class="card shadow p-3 mb-5 bg-white rounded" style="width: 18rem;">
                    <div class="row p-2 mb-5">
                        <div class="col-5 align-content-center imgDriver">
                            <img src="${trip.driverPath}" class="rounded-circle"
                                 alt="Your Avatar" width="70"
                                 height="70"></div>
                        <div class="col-7 align-content-center nameDriver mt-4">
                                ${trip.driverFirstN} ${trip.driverLastN}
                        </div>
                    </div>
                    <div class="card-body">
                        <div>
                            <h5 class="card-title" style="color: orange">
                                <i class="fa fa-map-marker"> </i>
                                    ${trip.fromTrip}</h5>
                            <h5 class="card-title" style="color: #1c7430">
                                <i class="fa fa-map-marker"> </i>
                                    ${trip.toTrip}</h5>
                        </div>
                        <div>
                            <p class="card-text text-center"> ${trip.date}</p>
                            <p class="card-text text-center"> ${trip.time}</p>
                        </div>
                        <div class="row p-2">
                            <div class="col-8">
                                <div class="row">
                                    <span class="col-3 numberSeats">${trip.availableSeats}</span>
                                    <span class="col-9 availableSeats">
                                        Available seats</span>
                                        <br>    
                                        <!--<span>Owner's Rating: ${trip.driverRate}</span> todo: HACER ESTO-->
                                </div>
                            </div>
                            <a href="/viewTrip.jsp?trip=${trip.tripId}"
                               class="viewButton col-4 btn btn-default" role="button"> View
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            `
    }
}