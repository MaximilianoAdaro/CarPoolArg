package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/secure/home.do")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> user = Users.findByEmail(req.getUserPrincipal().getName());
        user.ifPresent(value -> req.getSession().setAttribute("isAdmin", value.getAdministrator()));
        user.ifPresent(value -> req.getSession().setAttribute("hasPath", value.getAvatarPath() != null));
        user.ifPresent(value -> req.getSession().setAttribute("avatarPath", value.getAvatarPath()));
        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}