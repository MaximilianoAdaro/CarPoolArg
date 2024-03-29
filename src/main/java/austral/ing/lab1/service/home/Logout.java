package austral.ing.lab1.service.home;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout.do")
public class Logout extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getSession().invalidate();


    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    final RequestDispatcher view = req.getRequestDispatcher("/index.html");

    view.forward(req, resp);
  }
}