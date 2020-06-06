package austral.ing.lab1.service;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/myTrips.do")
public class MyTrips extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            req.setAttribute("tripsAsDriver", Trips.listDriverTrips(user.getUserId(), true));
            req.setAttribute("tripsAsPassenger", Trips.listPassengerTrips(user.getUserId(), true));

            req.setAttribute("tripsBeforeAsDriver", Trips.listDriverTrips(user.getUserId(), false));
            req.setAttribute("tripsBeforeAsPassenger", Trips.listPassengerTrips(user.getUserId(), false));
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/myTrips.jsp").forward(req, resp);
    }
}
