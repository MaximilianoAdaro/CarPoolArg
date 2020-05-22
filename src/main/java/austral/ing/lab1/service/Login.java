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
import java.util.List;
import java.util.Optional;

@WebServlet("/secure/home.do")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()){
            List<Trip> trips = Trips.listCurrentTrips(optionalUser.get().getUserId());
            req.getSession().setAttribute("trip", trips);
        }

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}