package austral.ing.lab1.service.user;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/username")
public class UserData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(name);
        if (optionalUser.isPresent()) {

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            final Gson gson = new Gson();
            String json = gson.toJson(name);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        }
    }
}
