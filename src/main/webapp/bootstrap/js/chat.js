let chats = null;

let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function () {
    if (this.readyState === 4 && this.status === 200) {
        chats = JSON.parse(this.response);
        console.log("chats1", chats)
        let chatsContainer = document.getElementById("chatsContainer");
        printChatsContainer(chats, chatsContainer)
    }
}
xhttp.open("GET", "/chatsAll", false);
xhttp.send();

function sendChats(chats) {
    let chatsContainer = document.getElementById("chatsContainer");
    printChatsContainer(chats, chatsContainer)
}

function printChatsContainer(chatList, docTrip) {
    if ((chatId === null) && chatList.length >= 1) {
        setVariables(chatList[0])
        console.log("chatId", chatId)
    }

    docTrip.innerHTML = '';
    if (chatList.length === 0) {
        docTrip.innerHTML = `
        <li class="ks-item">
            <div class="ks-body justify-content-center">
                <div class="ks-name" style="justify-content: center;
                            display: flex; font-size: 1.2rem; ">
                    There are no chats
                </div>
            </div>
        </li>
        `;
    }
    for (const chat of chatList) {
        if (chat.chatId === chatId) {
            docTrip.innerHTML += `
            <li class="ks-item ks-active">
                <a onclick="changeChat('${chat.chatId}')" class="hooverA">
                    <span class="ks-group-amount">${chat.amountUsers}</span>
                    <div class="ks-body">
                        <div class="ks-name">
                            ${chat.chatName}
                        </div>
                        <div class="ks-message">
                            <img src="${chat.tripDriverAvatarPath}"
                                 width="18" height="18" class="rounded-circle"> 
                                 Driver -> ${chat.tripDriverName}
                        </div>
                    </div>
                </a>
            </li>
            `;
        } else {
            docTrip.innerHTML += `
            <li class="ks-item">
                <a onclick="changeChat('${chat.chatId}')" class="hooverA">
                    <span class="ks-group-amount">${chat.amountUsers}</span>
                    <div class="ks-body">
                        <div class="ks-name">
                            ${chat.chatName}
                        </div>
                        <div class="ks-message">
                            <img src="${chat.tripDriverAvatarPath}"
                                 width="18" height="18" class="rounded-circle"> 
                                 Driver -> ${chat.tripDriverName}
                        </div>
                    </div>
                </a>
            </li>
            `;
        }
    }
}