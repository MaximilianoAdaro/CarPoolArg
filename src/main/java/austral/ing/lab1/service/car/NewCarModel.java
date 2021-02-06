package austral.ing.lab1.service.car;

import austral.ing.lab1.entity.CarModels;
import austral.ing.lab1.model.CarModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newCarModel.do")
public class NewCarModel extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nameCarModel = req.getParameter("car_name");
        if (nameCarModel.trim().length() < 1){
            req.getRequestDispatcher("/createCarBrand.jsp").forward(req, resp);
        }

        CarModel carModel = new CarModel(nameCarModel);
        CarModels.persist(carModel);

        req.getRequestDispatcher("/createCarBrand.jsp").forward(req, resp);
    }
}