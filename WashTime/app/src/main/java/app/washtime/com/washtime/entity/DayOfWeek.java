package app.washtime.com.washtime.entity;

public enum DayOfWeek {
    MONDAY("MONDAY", "Mon", 0),
    TUESDAY("TUESDAY", "Tue", 1),
    WEDNESDAY("WEDNESDAY", "Wed", 2),
    THURSDAY("THURSDAY", "Thu", 3),
    FRIDAY("FRIDAY","Fri", 4),
    SATURDAY("SATURDAY", "Sat", 5),
    SUNDAY("SUNDAY", "Sun", 6);

    private String mName;
    private String mAbbreviation;
    private int mPosition;

    DayOfWeek(String name, String abbreviation, int position) {
        mName = name;
        mAbbreviation = abbreviation;
        mPosition = position;
    }

    public String getName() {
        return mName;
    }

    public String getAbbreviation() {
        return mAbbreviation;
    }

    public int getPosition() {
        return mPosition;
    }

    public static DayOfWeek getByAbbreviation(String abbreviation) {
        for (DayOfWeek dayOfWeek: DayOfWeek.values()) {
            if (dayOfWeek.getAbbreviation().equals(abbreviation)) {
                return dayOfWeek;
            }
        }
        return null;
    }

    public static DayOfWeek getByPosition(int position) {
        for (DayOfWeek dayOfWeek: DayOfWeek.values()) {
            if (dayOfWeek.getPosition() == position) {
                return dayOfWeek;
            }
        }
        return null;
    }
}
