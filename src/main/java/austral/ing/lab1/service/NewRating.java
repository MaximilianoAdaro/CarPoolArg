package austral.ing.lab1.service;

import austral.ing.lab1.entity.Ratings;
import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Rating;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/newRating.do")
public class NewRating extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long idDriver = Long.parseLong(req.getParameter("idDriver"));
        long idPassenger = Long.parseLong(req.getParameter("idPassenger"));
        long idTrip = Long.parseLong(req.getParameter("idTrip"));
        int value = Integer.parseInt(req.getParameter("inlineRadioOptions"));
        boolean isDriver = Boolean.parseBoolean(req.getParameter("isDriver"));

//        Ratings.rate(idTrip,idDriver,idPassenger,isDriver,value);
        Optional<Trip> optionalTrip = Trips.findById(idTrip);
        Optional<User> optionalDriver = Users.findById(idDriver);
        Optional<User> optionalPassenger = Users.findById(idPassenger);
        if (optionalTrip.isPresent() & optionalDriver.isPresent() & optionalPassenger.isPresent()) {
            Optional<Rating> optionalRating = Ratings.findRate(optionalTrip.get(), optionalDriver.get(), optionalPassenger.get(), isDriver);
            if (optionalRating.isPresent()) {
                Rating rating = optionalRating.get();
                rating.setValue(value);
                rating.setRated(true);
                Ratings.persist(rating);
            }
        }

//        resp.sendRedirect("/notification.jsp");
        req.getRequestDispatcher("/notification.jsp").forward(req, resp);
    }
}
