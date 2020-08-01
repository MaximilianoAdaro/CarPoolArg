package austral.ing.lab1.jsonModel;

import austral.ing.lab1.model.Location;

public class TripData {

    public TripData(long tripId, String driverPath, String driverFirstN, String driverLastN, String fromTrip,
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

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public String getDriverFirstN() {
        return driverFirstN;
    }

    public void setDriverFirstN(String driverFirstN) {
        this.driverFirstN = driverFirstN;
    }

    public String getDriverLastN() {
        return driverLastN;
    }

    public void setDriverLastN(String driverLastN) {
        this.driverLastN = driverLastN;
    }

    public String getFromTrip() {
        return fromTrip;
    }

    public void setFromTrip(String fromTrip) {
        this.fromTrip = fromTrip;
    }

    public String getToTrip() {
        return toTrip;
    }

    public void setToTrip(String toTrip) {
        this.toTrip = toTrip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "TripData{" +
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
