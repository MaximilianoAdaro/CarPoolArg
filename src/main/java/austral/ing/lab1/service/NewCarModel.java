package austral.ing.lab1.service;

import austral.ing.lab1.entity.CarModels;
import austral.ing.lab1.entity.Cars;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Car;
import austral.ing.lab1.model.CarModel;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/newCarModel.do")
public class NewCarModel extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CarModel carModel = new CarModel(req.getParameter("car_name"));
        CarModels.persist(carModel);

        req.getRequestDispatcher("/secure/profile.jsp").forward(req, resp);
    }
}