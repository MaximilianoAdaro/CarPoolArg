package austral.ing.lab1.service;

import austral.ing.lab1.entity.Ratings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newRating.do")
public class NewRating extends HttpServlet {

//    todo: call from notification.jsp
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long idDriver = Long.valueOf(req.getParameter("idDriver"));
        Long idPassenger = Long.valueOf(req.getParameter("idPassenger"));
        Long idTrip = Long.valueOf(req.getParameter("idTrip"));
        int value = Integer.parseInt(req.getParameter("value"));
        boolean isDriver = Boolean.parseBoolean(req.getParameter("isDriver"));

        Ratings.rate(idTrip,idDriver,idPassenger,isDriver,value);

        req.getRequestDispatcher("/notification.jsp").forward(req, resp);
    }
}
