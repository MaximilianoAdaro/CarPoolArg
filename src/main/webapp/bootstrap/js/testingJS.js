function loadUsers() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let users = JSON.parse(this.response);

            let root = document.getElementById("ajax-example");
            for (const user of users) {
                // root.innerHTML += `
                //     <li id="${user.email}"> ${user.firstName},` + " " + ` ${user.lastName} </li>
                // `
                var li = document.createElement("li");
                li.setAttribute('id', user.email);
                li.appendChild(document.createTextNode(user.lastName + ", " + user.firstName));
                root.appendChild(li);
            }
        }
    };
    xhttp.open("POST", "/secure/user", true);
    xhttp.send();
}
