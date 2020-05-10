package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trip_table")
public class Trip {

    public Trip() {
    }

    public Trip(User driver, Date date, String from, String to, Time time, String comment, int seats) {
        this.driver = driver;
        driver.addTripAsDriver(this);
        date.setYear(date.getYear() - 1900);
        this.date = date;
        this.from = from;
        this.to = to;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "join_trip_passengers_table",
            joinColumns = {@JoinColumn(name = "tripId")},
            inverseJoinColumns = {@JoinColumn(name = "userId")})
    private List<User> passengers = new ArrayList<User>();

    @Column(name = "DATE")
    private Date date;

    @Column(name = "FROM_TRIP")
    private String from;

    @Column(name = "TO_TRIP")
    private String to;

    @Column(name = "TIME")
    private Time time;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "SEATS")
    private int seats;

    @Column(name = "AVAILABLE_SEATS")
    private int availableSeats;

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

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
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

    public void addPassenger(User user) {
        passengers.add(user);
        if (availableSeats > 0)
            availableSeats--;
    }

    public boolean removePassenger(User user) {
        if (passengers.remove(user))
            availableSeats++;
        return passengers.remove(user);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", driver=" + driver +
                ", passengers=" + passengers +
                ", date=" + date +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                ", seats=" + seats +
                '}';
    }
}
