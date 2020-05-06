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
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

@WebServlet("/createTrip.do")
public class CreateTrip extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(email);
        if (optionalUser.isPresent()) {
            User driver = optionalUser.get();
            String from = req.getParameter("fromTrip");
            String to = req.getParameter("toTrip");
            String day = req.getParameter("dayTrip");
            String time = req.getParameter("timeTrip");
            String comment = req.getParameter("commentTrip");
            int seats = Integer.parseInt(req.getParameter("seatsTrip"));

            String[] part = day.split("-");
            Date date = new Date(Integer.parseInt(part[0]), Integer.parseInt(part[1]), Integer.parseInt(part[2]));

            System.out.println(Date.valueOf(day));

            String[] tim = time.split(":");
            Time timetable = new Time(Integer.parseInt(tim[0]), Integer.parseInt(tim[1]), 0);

            Trip trip = new Trip(driver, date, from, to, timetable, comment, seats);
            Trips.persist(trip);
        }

        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}
