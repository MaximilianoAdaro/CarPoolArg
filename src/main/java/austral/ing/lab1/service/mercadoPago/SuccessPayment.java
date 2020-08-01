package austral.ing.lab1.service.mercadoPago;

import austral.ing.lab1.entity.Payments;
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

@WebServlet("/secure/success-buy-event")
public class SuccessPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        Optional<Trip> optionalTrip = Trips.findById(Long.parseLong(req.getParameter("tripID")));
        if (optionalUser.isPresent() && optionalTrip.isPresent()) {
            User user = optionalUser.get();
            Trip trip = optionalTrip.get();
            double amount = Double.parseDouble(req.getParameter("amount"));
            Payments.savePayment(user, trip, amount);
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}
