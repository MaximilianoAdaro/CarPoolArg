package austral.ing.lab1.service;

import austral.ing.lab1.entity.Notifications;
import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.TripsPassengers;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/passenger.do")
public class PassengerABM extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findById(Long.parseLong(req.getParameter("user")));
        Long tripId = Long.parseLong(req.getParameter("tripId"));
        Optional<Trip> optionalTrip = Trips.findById(tripId);
        String accepted = req.getParameter("case");
        System.out.println("accepted = " + accepted);

        if (optionalUser.isPresent() & optionalTrip.isPresent()) {
            User user = optionalUser.get();
            Trip trip = optionalTrip.get();

            if (accepted.equals("accepted")) {
                TripsPassengers.acceptPassenger(user.getUserId(), tripId);
                Notifications.newNotification(user, trip, 1L, LocalDate.now().toString());
            } else {
                TripsPassengers.rejectPassenger(user.getUserId(), tripId);
                Notifications.newNotification(user, trip, 2L, LocalDate.now().toString());
            }
        }

        req.getRequestDispatcher("/notification.do").forward(req, resp);

    }
}
