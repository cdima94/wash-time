package app.washtime.com.washtime.view;

/**
 * Created by cdima on 6/1/2017.
 */

public class HistoryItemsListView {

    private String mFirstName;
    private String mLastName;
    private String mDate;
    private String mDayOfMonth;

    public HistoryItemsListView() {
    }

    public HistoryItemsListView(String firstName, String lastName, String date, String dayOfMonth) {
        mFirstName = firstName;
        mLastName = lastName;
        mDate = date;
        mDayOfMonth = dayOfMonth;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }
}
