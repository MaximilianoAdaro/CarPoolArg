package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_TRIPS")
public class PaymentTrip {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ID")
    private Trip trip;

    @Column(name = "AMOUNT")
    private double amount;

    public PaymentTrip(User user, Trip trip, double amount) {
        this.user = user;
        this.trip = trip;
        this.amount = amount;
    }

    public long getTripId() {
        return paymentId;
    }

    public void setTripId(long paymentId) {
        this.paymentId = paymentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentTrip{" +
                "tripId=" + paymentId +
                ", user=" + user.getUserId() +
                ", trip=" + trip.getTripId() +
                ", amount=" + amount +
                '}';
    }
}
