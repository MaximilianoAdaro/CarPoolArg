package austral.ing.lab1.service.car;

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

@WebServlet("/carABM.do")
public class CarABM extends HttpServlet {

    //    modify car
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Long carModelId = Long.parseLong(req.getParameter("carModelId"));
            Optional<CarModel> carModel = CarModels.findById(carModelId);
            if (carModel.isPresent()) {
                Car car = new Car(carModel.get(), req.getParameter("car_color"), req.getParameter("car_patent"));
                Cars.persist(car);
                user.setCar(car);
                Users.persist(user);
            }
        }
        req.getRequestDispatcher("/secure/profile.jsp").forward(req, resp);
    }

    //    delete car
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getUserPrincipal().getName();
        Optional<User> optionalUser = Users.findByEmail(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getCar() != null) {
                user.deleteCar();
                Users.persist(user);
            }
        }
        req.getRequestDispatcher("/secure/profile.jsp").forward(req, resp);
    }
}