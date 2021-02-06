package austral.ing.lab1.service.profile;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/myTrips.do")
public class MyTrips extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            final List<Trip> driverTrips = Trips.listDriverTrips(user.getUserId(), true);
            req.setAttribute("tripsAsDriver", driverTrips);

            final List<Trip> passengerTrips = Trips.listPassengerTrips(user.getUserId(), true);
            req.setAttribute("tripsAsPassenger", passengerTrips);

            final List<Trip> beforeDriverTrips = Trips.listDriverTrips(user.getUserId(), false);
            req.setAttribute("tripsBeforeAsDriver", beforeDriverTrips);

            final List<Trip> beforePassengerTrips = Trips.listPassengerTrips(user.getUserId(), false);
            req.setAttribute("tripsBeforeAsPassenger", beforePassengerTrips);

            req.setAttribute("emptyTripsAsDriver", driverTrips.isEmpty());
            req.setAttribute("emptyTripsAsPassenger", passengerTrips.isEmpty());
            req.setAttribute("emptyTripsBeforeAsDriver", beforeDriverTrips.isEmpty());
            req.setAttribute("emptyTripsBeforeAsPassenger", beforePassengerTrips.isEmpty());
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/myTrips.jsp").forward(req, resp);
    }
}
