package austral.ing.lab1.service;

import austral.ing.lab1.entity.Trips;
import austral.ing.lab1.model.Trip;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/filterHome.do")
public class FilterTo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fromTrip = req.getParameter("fromTrip").toLowerCase();
        String toTrip = req.getParameter("toTrip").toLowerCase();

        List<Trip> trips = Trips.searchList(fromTrip, toTrip);
        req.getSession().setAttribute("trip", trips);
        System.out.println(trips.toString());

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}
