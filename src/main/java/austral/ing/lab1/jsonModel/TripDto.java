package austral.ing.lab1.jsonModel;

import austral.ing.lab1.entity.Ratings;
import austral.ing.lab1.model.Location;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

public class TripDto {

    public TripDto(long tripId, String driverPath, String driverFirstN, String driverLastN, String fromTrip,
                   String toTrip, String date, String time, int availableSeats, int rate, int rateSize,
                   Location fromLocation, Location toLocation) {
        this.tripId = tripId;
        this.driverPath = driverPath;
        this.driverFirstN = driverFirstN;
        this.driverLastN = driverLastN;
        this.fromTrip = fromTrip;
        this.toTrip = toTrip;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.rate = rate;
        this.rateSize = rateSize;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    private long tripId;
    private String driverPath;
    private String driverFirstN;
    private String driverLastN;
    private String fromTrip;
    private String toTrip;
    private String date;
    private String time;
    private int availableSeats;
    private int rate;
    private int rateSize;
    private Location fromLocation;
    private Location toLocation;

    public static TripDto from(Trip t, User user) {
        return new TripDto(t.getTripId(), t.getDriver().getAvatarPath(), t.getDriver().getFirstName(), t.getDriver().getLastName(),
                t.getFromTrip().getName(), t.getToTrip().getName(), t.getDate(), t.getTime().toString(),
                t.getAvailableSeats(), Ratings.rateUser(user), Ratings.getSizeRate(user), t.getFromTrip(), t.getToTrip());
    }

    @Override
    public String toString() {
        return "TripDto{" +
                "tripId=" + tripId +
                ", driverPath='" + driverPath + '\'' +
                ", driverFirstN='" + driverFirstN + '\'' +
                ", driverLastN='" + driverLastN + '\'' +
                ", fromTrip='" + fromTrip + '\'' +
                ", toTrip='" + toTrip + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", availableSeats=" + availableSeats +
                ", rate=" + rate +
                ", rateSize=" + rateSize +
                '}';
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRateSize() {
        return rateSize;
    }

    public void setRateSize(int rateSize) {
        this.rateSize = rateSize;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }
}
