package austral.ing.lab1.service.chat;

import austral.ing.lab1.entity.Chats;
import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.jsonModel.ChatDto;
import austral.ing.lab1.jsonModel.TripDto;
import austral.ing.lab1.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/chatsAll")
public class ChatGetAll extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            final List<ChatDto> data = Chats.getCurrentChats(user.getUserId());

            System.out.println(data);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            final Gson gson = new Gson();
            String json = gson.toJson(data);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        }
    }
}
