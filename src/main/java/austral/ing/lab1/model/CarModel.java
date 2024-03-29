package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MODELS_CARS")
public class CarModel{

    public CarModel() {
    }

    public CarModel(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long carModelId;

    @Column(name = "CAR_MODEL")
    private String name;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    public long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(long id) {
        this.carModelId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car){
        cars.add(car);
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "carModelId=" + carModelId +
                ", name='" + name + '\'' +
                '}';
    }
}
