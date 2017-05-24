package app.washtime.com.washtime.view;

public class StudentHomeViewContainer {

    private String mLocationName;
    private String mName;

    public StudentHomeViewContainer(String locationName, String name) {
        mLocationName = locationName;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getLocationName() {
        return mLocationName;
    }
}
