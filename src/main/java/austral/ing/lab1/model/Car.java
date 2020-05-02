package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "car_table")
public class Car {

//    public Car() {}

    public Car(CarModel carModel, String color, String patent) {
        this.carModel = carModel;
        this.color = color;
        this.patent = patent;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAR_MODEL")
    private CarModel carModel;

    @OneToOne(mappedBy = "car", fetch = FetchType.LAZY)
    private User user;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "PATENT")
    private String patent;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carModel=" + carModel +
                ", user=" + user +
                ", color='" + color + '\'' +
                ", patent='" + patent + '\'' +
                '}';
    }
}