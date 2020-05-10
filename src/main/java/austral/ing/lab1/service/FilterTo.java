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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String str = req.getParameter("txtSearch");

        req.getSession().setAttribute("tripList", str);

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        req.getRequestDispatcher("/secure/home.jsp").forward(req, resp);
    }
}
