package austral.ing.lab1.service;

import austral.ing.lab1.entity.Cars;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Car;
import austral.ing.lab1.model.CarModel;
import austral.ing.lab1.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class NewCar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
//            CarModel carModel = //todo: how to get carModel
//            Car car = new Car(carModel, req.getParameter("color"), req.getParameter("patent"));
//            Cars.persist(car);
//            car.setUser(user);
//            user.setCar(car);
        }
        req.getRequestDispatcher("/secure/profile.html").forward(req, resp);
    }
}