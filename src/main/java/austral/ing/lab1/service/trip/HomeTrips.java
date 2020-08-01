package austral.ing.lab1.service.trip;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.jsonModel.TripData;
import austral.ing.lab1.model.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/homeTrip")
public class HomeTrips extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            final List<TripData> data = Trips.listTripsData(Trips.listCurrentTrips(user.getUserId()), user);

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
