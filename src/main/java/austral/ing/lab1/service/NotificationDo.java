package austral.ing.lab1.service;

import austral.ing.lab1.entity.*;
import austral.ing.lab1.model.Notification;
import austral.ing.lab1.model.Rating;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/notification.do")
public class NotificationDo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<User> optionalUser = Users.findByEmail(req.getUserPrincipal().getName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<NotificationType> notifList = Notifications.listAllNotif(user);

            List<Rating> ratingsUserAsDriver = Ratings.ratingsUserAsDriver(user);
            List<Rating> ratingsUserAsPassenger = Ratings.ratingsUserAsPassenger(user);
            List<TripsPassengers> tripsPassengers = TripsPassengers.listTrips(user);

            req.getSession().setAttribute("notifList", notifList);

            req.getSession().setAttribute("ratingsUserAsDriver", ratingsUserAsDriver);
            req.getSession().setAttribute("ratingsUserAsPassenger", ratingsUserAsPassenger);
            req.getSession().setAttribute("tripsPassengers", tripsPassengers);

            req.getSession().setAttribute("ratingAsDriver", !ratingsUserAsDriver.isEmpty());
            req.getSession().setAttribute("ratingAsPassenger", !ratingsUserAsPassenger.isEmpty());
            req.getSession().setAttribute("tripPassNotEmpty", !tripsPassengers.isEmpty());
        }
        req.getRequestDispatcher("/notification.jsp").forward(req, resp);
    }
}
