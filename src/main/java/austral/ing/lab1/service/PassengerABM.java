package austral.ing.lab1.service;

import austral.ing.lab1.entity.TripsPassengers;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PassengerABM extends HttpServlet {

//    todo: do this
//    Debe ser accepted si lo acepta al pasajero,o rejected en caso contrario y este lo elimina
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        Long tripId = Long.parseLong(req.getParameter("tripId"));
        String accepted = req.getParameter("case");

        if (optionalUser.isPresent()) {
            Long userId = optionalUser.get().getUserId();

            if (accepted.equals("accepted"))
                TripsPassengers.acceptPassenger(userId, tripId);
            else
                TripsPassengers.rejectPassenger(userId, tripId);
        }

        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);

    }
}
