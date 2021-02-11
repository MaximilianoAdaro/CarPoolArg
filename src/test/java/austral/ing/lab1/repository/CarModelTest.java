package austral.ing.lab1.repository;

import austral.ing.lab1.entity.CarModels;
import austral.ing.lab1.model.CarModel;
import austral.ing.lab1.util.EntityManagers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CarModelTest {

//    private EntityManagerFactory emf;
//
//    @Before
//    public void setUp() {
//        emf = Persistence.createEntityManagerFactory("test");
//        EntityManagers.setFactory(emf);
//    }
//
//    @After
//    public void tearDown() {
//        emf.close();
//    }
//
//    @Test
//    public void createCarModel() {
//        List<CarModel> carModelList = CarModels.listAll();
//        for (CarModel carModel : carModelList) {
//            System.out.println(carModel.toString());
//        }
//
//    }
}
