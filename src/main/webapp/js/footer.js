$(document).ready()
{
    const footerDom = document.getElementById("footer");
    footerDom.innerHTML += `

<footer class="container-fluid" style="background-color:#33363A; color:white; top: 5px">
<!--style="background-color:#33363A; color:white; position: absolute; bottom: 0; width: 100%;-->
    <div class="container py-5">
        <div class="row">
            <div class="col-3" style="right: 30px">
                <img src="../images/LogoIngenieria.png" alt="58.71" width="300">
            </div>
            <div class="col-3">
                Developers
                <ul>
                    <li>
                        Maximiliano Adaro
                    </li>
                    <li>
                        Numa Leone Elizalde
                    </li>
                </ul>
            </div>
            <div class="col-3">
                Laboratorio 1
                <ul>
                    <li>
                        Paca
                    </li>
                    <li>
                        Diego
                    </li>
                </ul>
            </div>
            <div class="col-3">
                Help
                <ul>
                    <li>
                        Frequently Asked Questions (FAQ)
                    </li>
                    <li>
                        Recommendations
                    </li>
                </ul>
            </div>
        </div>
    </div>
</footer>
`;
}