package austral.ing.lab1.service.trip;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.TripsPassengers;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;
import austral.ing.lab1.service.email.SendEmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/newPassenger.do")
public class NewPassenger extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        String addPassenger = req.getParameter("state");

        if (optionalUser.isPresent()) {
            User passenger = optionalUser.get();
            Optional<Trip> optionalTrip = Trips.findById(Long.parseLong(req.getParameter("tripId")));

            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                if (trip.getDriver().equals(passenger)) {
                    req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
                    return;
                }

                if (addPassenger.equals("join")) {
                    trip.addPassenger(passenger);
                    Trips.persist(trip);
                    Users.persist(passenger);
                    try {
                        SendEmailService.sendRequestEmail(trip.getDriver().getEmail(),trip.getToTrip().getName(),passenger.getFullName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (addPassenger.equals("goDown")) {
                    TripsPassengers.rejectPassenger(passenger.getUserId(), trip.getTripId());
                    trip.removePassenger(passenger);
                    Trips.persist(trip);
                    Users.persist(passenger);
                }
            }

            final List<Trip> driverTrips = Trips.listDriverTrips(passenger.getUserId(), true);
            req.setAttribute("tripsAsDriver", driverTrips);

            final List<Trip> passengerTrips = Trips.listPassengerTrips(passenger.getUserId(), true);
            req.setAttribute("tripsAsPassenger", passengerTrips);

            final List<Trip> beforeDriverTrips = Trips.listDriverTrips(passenger.getUserId(), false);
            req.setAttribute("tripsBeforeAsDriver", beforeDriverTrips);

            final List<Trip> beforePassengerTrips = Trips.listPassengerTrips(passenger.getUserId(), false);
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