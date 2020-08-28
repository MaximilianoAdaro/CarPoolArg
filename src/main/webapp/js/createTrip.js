let autocompleteInputFrom;
let autocompleteInputTo;

let autoPropsFrom;
let autoPropsTo;

const options = {componentRestrictions: {country: 'ar'}, types: ['(regions)']};

$(document).ready(function () {
    //From input
    let autoInputFrom = new google.maps.places.Autocomplete(document.getElementById('from'), options);
    google.maps.event.addListener(autoInputFrom, 'place_changed', function () {
            autocompleteInputFrom = autoInputFrom.getPlace();
            autoPropsFrom = {
                name: autocompleteInputFrom.name,
                lat: autocompleteInputFrom.geometry.location.lat(),
                lng: autocompleteInputFrom.geometry.location.lng(),
                address: autocompleteInputFrom.formatted_address
            }
            console.log(autocompleteInputFrom);
            console.log(autoPropsFrom);
        }
    )
    //To input
    let autoInputTo = new google.maps.places.Autocomplete(document.getElementById('to'), options);
    google.maps.event.addListener(autoInputTo, 'place_changed', function () {
            autocompleteInputTo = autoInputTo.getPlace();
            autoPropsTo = {
                name: autocompleteInputTo.name,
                lat: autocompleteInputTo.geometry.location.lat(),
                lng: autocompleteInputTo.geometry.location.lng(),
                address: autocompleteInputTo.formatted_address
            }
            console.log(autocompleteInputTo);
            console.log(autoPropsTo);
        }
    )

})

function createTrip() {

    let fromTrip = JSON.stringify(autoPropsFrom);
    let toTrip = JSON.stringify(autoPropsTo);
    let day = document.getElementById('day').value;
    let time = document.getElementById('time').value;
    let comment = document.getElementById('com').value;
    let seatsTrip = document.getElementById('seatsTrip').value;

    if (!$.isEmptyObject(fromTrip) && !$.isEmptyObject(toTrip) && !$.isEmptyObject(comment)) {
        $.post("/createTrip.do",
            {
                fromTrip: fromTrip,
                toTrip: toTrip,
                dayTrip: day,
                timeTrip: time,
                commentTrip: comment,
                seatsTrip: seatsTrip
            });
    }
    // var xhttp = new XMLHttpRequest();
    // xhttp.open("POST", "/createTrip.do", true);
    // xhttp.send(null);

}