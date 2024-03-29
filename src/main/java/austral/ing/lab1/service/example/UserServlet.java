package austral.ing.lab1.service.example;

import austral.ing.lab1.entity.Users;
import austral.ing.lab1.jsonModel.UserData;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/secure/user")
public class UserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<UserData> userData = Users.listUsersData(Users.listAll());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        final Gson gson = new Gson();
        String json = gson.toJson(userData);
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

}


