let userName = null;
let websocket = null;
let chatId = null;
let chat = null;

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

    if (userName == null) loadUserName();
    if (chatId == null) selectFirstChat()

    console.log(userName)
    console.log(chatId)

    if ("WebSocket" in window) {
        websocket = new WebSocket('ws://localhost:8080/chat/' + userName + "/" + chatId);
        websocket.onopen = function (_) {
            console.log(userName)
            console.log(chatId)
        };

        websocket.onmessage = function (data) {
            setMessage(JSON.parse(data.data));
        };

        websocket.onerror = function (_) {
            cleanUp();
        };

        websocket.onclose = function (_) {
            cleanUp();
        };
    } else {
    }
}

function cleanUp() {
    userName = null;
    websocket = null;
    chatId = null;
}

function sendMessage() {
    let messageContent = document.getElementById("message").value;
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
        newElem = `
        <li class="ks-item ks-from">
                <span class="ks-avatar ks-online">
                    <img src="https://bootdey.com/img/Content/avatar/avatar2.png" width="36"
                         height="36" class="rounded-circle">
                </span>
            <div class="ks-body">
                <div class="ks-header">
                    <span class="ks-name"> ${msg.username} </span>
                    <span class="ks-datetime">6:46 PM</span>
                </div>
                <div class="ks-message"> ${msg.message} </div>
            </div>
        </li>
        `
    } else {
        newElem = `
        <li class="ks-item ks-self">
                <span class="ks-avatar ks-offline">
                    <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="36"
                         height="36" class="rounded-circle">
                </span>
            <div class="ks-body">
                <div class="ks-header">
                    <span class="ks-name"> ${msg.username} </span>
                    <span class="ks-datetime">6:46 PM</span>
                </div>
                <div class="ks-message"> ${msg.message} </div>
            </div>
        </li>
        `;
    }

    document.getElementById('scrolling-messages').innerHTML = currentHTML + newElem;
}

function changeChat(chatId) {
    this.chatId = chatId
    console.log("ENTRO ACA PRIMERO")
    // document.getElementById('chatName').innerHTML = (chatName);
    // document.getElementById('amountUsers').innerHTML = (amountUsers + " members");
    init()
}

function selectFirstChat() {
    if (chatId === null && chats.length >= 1) {
        console.log("chats2", chats)
        chatId = chats[0].chatId
    }
}

function setVariables(chat) {
    chatId = chat.chatId
    this.chat = chat
    document.getElementById('chatName').innerHTML = (chat.chatName);
    document.getElementById('amountUsers').innerHTML = (chat.amountUsers + " members");
}