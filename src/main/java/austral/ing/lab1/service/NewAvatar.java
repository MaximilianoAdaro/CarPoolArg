package austral.ing.lab1.service;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/setAvatar.do")
public class NewAvatar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(email);

        if (optionalUser.isPresent()) {
            String avatar = req.getHeader("avatar"); //todo: check this, is null

            User user = optionalUser.get();
            user.setAvatarPath(".." + File.separator + "images" + File.separator + avatar);
            Users.persist(user);
        }

        req.getRequestDispatcher("/secure/profile.html").forward(req, resp);
    }
}