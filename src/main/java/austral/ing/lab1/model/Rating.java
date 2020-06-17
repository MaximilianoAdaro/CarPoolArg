package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "RATINGS")
public class Rating {

    public Rating(Trip idTrip, User idDriver, User idPassenger, boolean isDriver, int value, String message, boolean isRated) {
        this.idTrip = idTrip;
        this.idDriver = idDriver;
        this.idPassenger = idPassenger;
        this.isDriver = isDriver;
        this.value = value;
        this.message = message;
        this.isRated = isRated;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long idRating;

    @JoinColumn(name = "ID_TRIP")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trip idTrip;

    @JoinColumn(name = "ID_DRIVER")
    @ManyToOne(fetch = FetchType.LAZY)
    private User idDriver;

    @JoinColumn(name = "ID_PASSENGER")
    @ManyToOne(fetch = FetchType.LAZY)
    private User idPassenger;

    @Column(name = "IS_DRIVER")
    private boolean isDriver;

    @Column(name = "VALUE")
    private Integer value;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "IS_RATED")
    private boolean isRated;

    public long getIdRating() {
        return idRating;
    }

    public void setIdRating(long idRating) {
        this.idRating = idRating;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Trip getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(Trip idTrip) {
        this.idTrip = idTrip;
    }

    public User getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(User idDriver) {
        this.idDriver = idDriver;
    }

    public User getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(User idPassenger) {
        this.idPassenger = idPassenger;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "idRating=" + idRating +
                ", idTrip=" + idTrip.getTripId() +
                ", idDriver=" + idDriver.getUserId() +
                ", idPassenger=" + idPassenger.getUserId() +
                ", isDriver=" + isDriver +
                ", value=" + value +
                ", message='" + message + '\'' +
                ", isRated=" + isRated +
                '}';
    }
}
