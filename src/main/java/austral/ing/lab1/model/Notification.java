package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "NOTIFICATIONS")
public class Notification {

    public Notification(User idUser, Trip idTrip, TypeNotification type, String date) {
        this.idUser = idUser;
        this.idTrip = idTrip;
        this.type = type;
        this.date = date;

    }

    public Notification() {
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long idNotification;

    @JoinColumn(name = "ID_USER")
    @ManyToOne(fetch = FetchType.LAZY)
    private User idUser;

    @JoinColumn(name = "ID_TRIP")
    @ManyToOne(fetch = FetchType.LAZY)
    private Trip idTrip;

    @JoinColumn(name = "TYPE")
    @ManyToOne(fetch = FetchType.LAZY)
    private TypeNotification type;

    @Column(name = "DATE")
    private String date;

    public long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(long idNotification) {
        this.idNotification = idNotification;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Trip getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(Trip idTrip) {
        this.idTrip = idTrip;
    }

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "idNotification=" + idNotification +
                ", idUser=" + idUser.getUserId() +
                ", idTrip=" + idTrip.getTripId() +
                ", type=" + type.toString() +
                ", date='" + date + '\'' +
                '}';
    }
}
