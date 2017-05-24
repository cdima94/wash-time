package app.washtime.com.washtime.view;


public class ReservationViewContainer {

    private String mIntervalHours;
    private int mRoom;

    public ReservationViewContainer() {
    }

    public ReservationViewContainer(String intervalHours, int room) {
        mIntervalHours = intervalHours;
        mRoom = room;
    }

    public String getIntervalHours() {
        return mIntervalHours;
    }

    public int getRoom() {
        return mRoom;
    }

    public void setIntervalHours(String intervalHours) {
        mIntervalHours = intervalHours;
    }

    public void setRoom(int room) {
        mRoom = room;
    }
}
