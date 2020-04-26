package austral.ing.lab1.service;

import austral.ing.lab1.entity.Cars;
import austral.ing.lab1.entity.Users;
import austral.ing.lab1.model.Car;
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
        String email = req.getUserPrincipal().getName();
        Optional<User> user = Users.findByEmail(email);

        if (user.isPresent()){
            final Car car = new Car();
            car.setUser(user.get());
            car.setName(req.getParameter("car_name"));

            Cars.persist(car);
            req.getRequestDispatcher("/profile.html").forward(req, resp);
        }

    }
}
