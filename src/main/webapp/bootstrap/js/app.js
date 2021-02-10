let userName = null;
let websocket = null;

function loadUserName() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            userName = JSON.parse(this.response);
        }
    }
    xhttp.open("GET", "/username", false);
    xhttp.send();
}

function init() {
    loadUserName();
    if ("WebSocket" in window) {
        let chatId = 1;
        websocket = new WebSocket('ws://localhost:8080/chat/' + userName + "/" + chatId);
        websocket.onopen = function (_) {
            document.getElementById("main").style.display = "block";
        };

        websocket.onmessage = function (data) {
            setMessage(JSON.parse(data.data));
        };

        websocket.onerror = function (_) {
            // alert('An error occurred, closing application');
            cleanUp();
        };

        websocket.onclose = function (data) {
            cleanUp();
            // let reason = (data.reason) ? data.reason : 'Goodbye';
            // alert(reason);
        };
    } else {
        // alert("Websockets not supported");
    }
}

function cleanUp() {
    document.getElementById("main").style.display = "none";

    userName = null;
    websocket = null;
}

function sendMessage() {
    let messageContent = document.getElementById("message").value;
    let chatId = 1; //todo: get chatId and set it
    let message = buildMessage(userName, chatId, messageContent);

    document.getElementById("message").value = '';

    setMessage(message);
    websocket.send(JSON.stringify(message));
}

function buildMessage(userName, chatId, message) {
    return {
        username: userName,
        chatId: chatId,
        message: message
    };
}

function setMessage(msg) {
    const currentHTML = document.getElementById('scrolling-messages').innerHTML;
    let newElem;

    if (msg.username === userName) {
        newElem = '<p style="background: #ebebe0;"><span>' + msg.username
            + ' : ' + msg.message + '</span></p>';
    } else {
        newElem = '<p><span>' + msg.username + ' : ' + msg.message
            + '</span></p>';
    }

    document.getElementById('scrolling-messages').innerHTML = currentHTML
        + newElem;
}