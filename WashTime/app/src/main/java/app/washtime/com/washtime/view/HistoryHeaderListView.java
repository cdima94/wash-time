package app.washtime.com.washtime.view;

/**
 * Created by cdima on 6/1/2017.
 */

public class HistoryHeaderListView {

    private String mIntervalHours;
    private int mRoom;

    public HistoryHeaderListView() {
    }

    public HistoryHeaderListView(String intervalHours, int room) {
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
