package austral.ing.lab1.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TRIPS_PASSENGERS")
public class TripPassenger implements Serializable {

    public TripPassenger(User passenger, Trip trip) {
        this.passenger = passenger;
        this.trip = trip;
        state = false;
    }

    public TripPassenger() {
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PASSENGER_ID")
    private User passenger;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID")
    private Trip trip;

    @Column(name = "STATE")
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void acceptTrip() {
        state = true;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "TripPassenger{" +
                "passenger=" + passenger +
                ", trip=" + trip +
                ", state=" + state +
                '}';
    }
}
