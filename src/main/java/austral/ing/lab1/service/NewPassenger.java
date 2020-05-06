package austral.ing.lab1.service;

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
import java.util.Optional;

@WebServlet("/newPassenger.do")
public class NewPassenger extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Optional<Trip> optionalTrip = Trips.findById(Long.parseLong(req.getParameter("tripId")));
            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                trip.addPassenger(user);
                Trips.persist(trip);
            }
        }

        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}
