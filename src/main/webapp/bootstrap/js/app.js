let userName = null;
let websocket = null;
let chatId = 1; //change to dynamic

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
// function init(chatId) {
//     this.chatId = chatId
    loadUserName();
    if ("WebSocket" in window) {
        websocket = new WebSocket('ws://localhost:8080/chat/' + userName + "/" + chatId);
        websocket.onopen = function (_) {};

        websocket.onmessage = function (data) {
            setMessage(JSON.parse(data.data));
        };

        websocket.onerror = function (_) {
            // alert('An error occurred, closing application');
            cleanUp();
        };

        websocket.onclose = function (_) {
            cleanUp();
            // let reason = (data.reason) ? data.reason : 'Goodbye';
            // alert(reason);
        };
    } else {
        // alert("Websockets not supported");
    }
}

function cleanUp() {
    userName = null;
    websocket = null;
}

function sendMessage() {
    // let messageContent = document.getElementById("message").value;
    let message = buildMessage(userName, chatId, messageContent);

    // document.getElementById("message").value = '';

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
    // const currentHTML = document.getElementById('scrolling-messages').innerHTML;
    let newElem;

    if (msg.username === userName) {
        newElem = '<p style="background: #ebebe0;"><span>' + msg.username
            + ' : ' + msg.message + '</span></p>';
    } else {
        newElem = '<p><span>' + msg.username + ' : ' + msg.message
            + '</span></p>';
    }

    // document.getElementById('scrolling-messages').innerHTML = currentHTML+ newElem;
}