package app.washtime.com.washtime.utils;

/**
 * Created by cdima on 6/1/2017.
 */

public class DateParsing {

    public static String formatHistoryDate(String date) {
        String[] splitDate = date.split("/");
        String year = splitDate[2];
        if (Integer.valueOf(splitDate[2]) < 50) {
            year = "20" + splitDate[2];
        } else {
            year = "19" + splitDate[2];
        }
        return year + "-" + splitDate[0] + "-" + Integer.valueOf(splitDate[1]);
    }

    public static Integer[] getHistoryPartsDate(String date) {
        String[] splitDate = date.split("/");
        String year = splitDate[2];
        if (Integer.valueOf(splitDate[2]) < 50) {
            year = "20" + splitDate[2];
        } else {
            year = "19" + splitDate[2];
        }
        Integer[] parts = {Integer.valueOf(splitDate[0]), Integer.valueOf(splitDate[1]), Integer.valueOf(year)};
        return parts;
    }
}
