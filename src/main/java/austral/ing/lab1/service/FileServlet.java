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
import java.nio.file.Files;
import java.util.Optional;

@WebServlet("/files")
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String avatarPath = user.getAvatarPath();

            File file = new File(avatarPath);
            resp.setHeader("Content-Type", getServletContext().getMimeType(avatarPath));
            resp.setHeader("Content-Length", String.valueOf(file.length()));
            resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            Files.copy(file.toPath(), resp.getOutputStream());
        }
    }
}
