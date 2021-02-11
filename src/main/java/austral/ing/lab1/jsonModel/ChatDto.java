package austral.ing.lab1.jsonModel;

import austral.ing.lab1.model.Chat;
import austral.ing.lab1.model.Trip;
import austral.ing.lab1.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ChatDto {

    long chatId;
    long tripId;
    String tripDriverName;
    String tripDriverAvatarPath;
    List<Long> passengersId;
    String tripFrom;
    String tripTo;
    String chatName;
    String date;
    String time;
    int amountUsers;

    public ChatDto() {
    }

    public ChatDto(long chatId, long tripId, String tripDriverName, List<Long> passengersId, String tripFrom, String tripTo, String date, String time, int amountUsers, String tripDriverAvatarPath, String chatName) {
        this.chatId = chatId;
        this.tripId = tripId;
        this.tripDriverName = tripDriverName;
        this.passengersId = passengersId;
        this.tripFrom = tripFrom;
        this.tripTo = tripTo;
        this.date = date;
        this.time = time;
        this.amountUsers = amountUsers;
        this.tripDriverAvatarPath = tripDriverAvatarPath;
        this.chatName = chatName;
    }

    public static ChatDto convert(Chat chat) {
        Trip trip = chat.getTrip();
        List<Long> passengersList = trip.getPassengers().stream().map(tp -> tp.getPassenger().getUserId()).collect(Collectors.toList());
        int amountUsers = passengersList.size() + 1;
        User driver = trip.getDriver();
        String chatName = "From " + trip.getFromTrip().getName() + " to " + trip.getToTrip().getName();
        return new ChatDto(chat.getId(), trip.getTripId(), driver.getFirstName() + " " + driver.getLastName(), passengersList,
                trip.getFromTrip().getName(), trip.getToTrip().getName(), trip.getDate(), trip.getTime().toString(), amountUsers, driver.getAvatarPath(), chatName);
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getTripDriverName() {
        return tripDriverName;
    }

    public void setTripDriverName(String tripDriverName) {
        this.tripDriverName = tripDriverName;
    }

    public List<Long> getPassengersId() {
        return passengersId;
    }

    public void setPassengersId(List<Long> passengersId) {
        this.passengersId = passengersId;
    }

    public String getTripFrom() {
        return tripFrom;
    }

    public void setTripFrom(String tripFrom) {
        this.tripFrom = tripFrom;
    }

    public String getTripTo() {
        return tripTo;
    }

    public void setTripTo(String tripTo) {
        this.tripTo = tripTo;
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

    public int getAmountUsers() {
        return amountUsers;
    }

    public void setAmountUsers(int amountUsers) {
        this.amountUsers = amountUsers;
    }

    public String getTripDriverAvatarPath() {
        return tripDriverAvatarPath;
    }

    public void setTripDriverAvatarPath(String tripDriverAvatarPath) {
        this.tripDriverAvatarPath = tripDriverAvatarPath;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
