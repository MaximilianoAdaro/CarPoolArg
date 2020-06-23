package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TRIPS")
public class Trip {

    public Trip() {
    }

    public Trip(User driver, String date, String from, String to, Time time, String comment, int seats) {
        this.driver = driver;
        driver.addTripAsDriver(this);
        this.date = date;
        this.fromTrip = from;
        this.toTrip = to;
        this.time = time;
        this.comment = comment;
        this.seats = seats;
        availableSeats = seats;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long tripId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVER")
    private User driver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripPassenger> passengers = new ArrayList<>();

    @Column(name = "DATE")
    private String date;

    @Column(name = "FROM_TRIP")
    private String fromTrip;

    @Column(name = "TO_TRIP")
    private String toTrip;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "SEATS")
    private int seats;

    @Column(name = "AVAILABLE_SEATS")
    private int availableSeats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long id) {
        this.tripId = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public List<TripPassenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<TripPassenger> passengers) {
        this.passengers = passengers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void addPassenger(User user) {
        if (availableSeats > 0) {
            availableSeats--;
            TripPassenger personAddress = new TripPassenger(user, this);
            passengers.add(personAddress);
            user.addTripAsPassenger(personAddress);
        }
    }

    public void removePassenger(User user) {
        TripPassenger personAddress = new TripPassenger(user, this);
        passengers.remove(personAddress);
        user.removeTripAsPassenger(personAddress);
        availableSeats++;
        personAddress.setPassenger(null);
        personAddress.setTrip(null);
        personAddress.setState(false);

    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getFromTrip() {
        return fromTrip;
    }

    public void setFromTrip(String from) {
        this.fromTrip = from;
    }

    public String getToTrip() {
        return toTrip;
    }

    public void setToTrip(String to) {
        this.toTrip = to;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", driver=" + driver.getUserId() +
                ", passenger=" + passengers.size() +
                ", date=" + date +
                ", from='" + fromTrip + '\'' +
                ", to='" + toTrip + '\'' +
                ", time=" + time.toString() +
                ", comment='" + comment + '\'' +
                ", seats=" + seats +
                '}';
    }
}
