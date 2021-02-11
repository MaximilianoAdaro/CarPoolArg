package austral.ing.lab1.service.trip;

import austral.ing.lab1.entity.Chats;
import austral.ing.lab1.entity.Locations;
import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Chat;
import austral.ing.lab1.model.Location;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;
import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.C;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
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
            Gson json = new Gson();
            Location locationFrom = json.fromJson(from, Location.class);
            Locations.persist(locationFrom);
            String to = req.getParameter("toTrip");
            Location locationTo = json.fromJson(to, Location.class);
            Locations.persist(locationTo);
            String day = req.getParameter("dayTrip");
            String time = req.getParameter("timeTrip");
            String comment = req.getParameter("commentTrip");
            int seats = Integer.parseInt(req.getParameter("seatsTrip"));

            System.out.println(Date.valueOf(day));

            String[] tim = time.split(":");
            Time timetable = new Time(Integer.parseInt(tim[0]), Integer.parseInt(tim[1]), 0);

            Trip trip = new Trip(driver, day, locationFrom, locationTo, timetable, comment, seats);
            Trips.persist(trip);

            Chat chat = new Chat(trip);
            Chats.persist(chat);
        }
    }

}
