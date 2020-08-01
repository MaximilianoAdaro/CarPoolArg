package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "CAR_ID")
    private Car car;

    @Column(name = "AVATAR_PATH")
    private String avatarPath;

    @Column(name = "IS_ADMINISTRATOR")
    private Boolean isAdministrator;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Trip> driver = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripPassenger> passenger = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idDriver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> rateDriver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPassenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratePassenger;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        avatarPath = "/project/images/defaultAvatar.png";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
        car.setUser(this);
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setAdministrator(Boolean administrator) {
        isAdministrator = administrator;
    }

    public Boolean getAdministrator() {
        return isAdministrator;
    }

    public List<Trip> getDriver() {
        return driver;
    }

    public void setDriver(List<Trip> driver) {
        this.driver = driver;
    }

    public void addTripAsDriver(Trip trip) {
        driver.add(trip);
    }

    public void addTripAsPassenger(TripPassenger trip) {
        passenger.add(trip);
    }

    public void removeTripAsPassenger(TripPassenger trip) {
        passenger.remove(trip);
    }

    public void deleteCar() {
        car = null;
    }

    public List<TripPassenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<TripPassenger> trips) {
        this.passenger = trips;
    }

    public List<Rating> getRateDriver() {
        return rateDriver;
    }

    public void setRateDriver(List<Rating> rateDriver) {
        this.rateDriver = rateDriver;
    }

    public List<Rating> getRatePassenger() {
        return ratePassenger;
    }

    public void setRatePassenger(List<Rating> ratePassenger) {
        this.ratePassenger = ratePassenger;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", isAdministrator=" + isAdministrator +
                '}';
    }
}
