let autocompleteInputFrom;
let autocompleteInputTo;

let searchFrom;
let searchTo;

const options = {componentRestrictions: {country: 'ar'}, types: ['(regions)']};

$(document).ready(function () {
    //From input
    let autoInputFrom = new google.maps.places.Autocomplete(document.getElementById('fromTrip'), options);
    google.maps.event.addListener(autoInputFrom, 'place_changed', function () {
            autocompleteInputFrom = autoInputFrom.getPlace();
            searchFrom = {
                latLng: new google.maps.LatLng(autocompleteInputFrom.geometry.location.lat(),
                    autocompleteInputFrom.geometry.location.lng()),
            }
            console.log(autocompleteInputFrom);
            console.log(searchFrom);
        }
    )
    //To input
    let autoInputTo = new google.maps.places.Autocomplete(document.getElementById('toTrip'), options);
    google.maps.event.addListener(autoInputTo, 'place_changed', function () {
            autocompleteInputTo = autoInputTo.getPlace();
            searchTo = {
                latLng: new google.maps.LatLng(autocompleteInputTo.geometry.location.lat(),
                    autocompleteInputTo.geometry.location.lng()),
            }
            console.log(autocompleteInputTo);
            console.log(searchTo);
        }
    )
})

function searchTrip() {
    const maxDistance = 10000;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let trips = JSON.parse(this.response);
            let tripList = [];
            let homeTrip = document.getElementById("tripsHome");

            if (!$.isEmptyObject(searchFrom) || !$.isEmptyObject(searchTo)) { //Is empty
                if (!$.isEmptyObject(searchFrom)) {
                    //Tiene From Trip nomas
                    for (const trip of trips) {
                        fromDeleteTrips(trip, tripList, maxDistance)
                    }
                } else if (!$.isEmptyObject(searchTo)) {
                    //Tiene To Trip nomas
                    for (const trip of trips) {
                        toDeleteTrips(trip, tripList, maxDistance)
                    }
                } else {
                    //Tiene ambos (from y to Trip)
                    for (const trip of trips) {
                        fromDeleteTrips(trip, tripList, maxDistance)
                        toDeleteTrips(trip, tripList, maxDistance)
                    }
                }
                printTripsHome(tripList, homeTrip)
            } else {
                printTripsHome(trips, homeTrip)
            }
        }
    };
    xhttp.open("GET", "/homeTrip", true);
    xhttp.send();
}

function fromDeleteTrips(trip, tripList, maxDistance) {
    const distance = google.maps.geometry.spherical.computeDistanceBetween(
        searchFrom.latLng,
        new google.maps.LatLng(trip.fromLocation.lat, trip.fromLocation.lng)
    );
    console.log(trip.fromLocation.toString() + distance)
    if (distance < maxDistance) {
        tripList.push(trip);
    }
}

function toDeleteTrips(trip, tripList, maxDistance) {
    const distance = google.maps.geometry.spherical.computeDistanceBetween(
        searchTo.latLng,
        new google.maps.LatLng(trip.toLocation.lat, trip.toLocation.lng)
    );
    console.log(trip.toLocation.toString() + distance)
    if (distance < maxDistance) {
        tripList.push(trip);
    }
}