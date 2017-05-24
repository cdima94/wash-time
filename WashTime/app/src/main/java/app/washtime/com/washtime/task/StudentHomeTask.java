package app.washtime.com.washtime.task;

import android.content.Context;

import app.washtime.com.washtime.adapter.NewAccountLocationNameAdapter;
import app.washtime.com.washtime.adapter.NewAccountSNamesAdapter;
import app.washtime.com.washtime.adapter.StudentHomeAdapter;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.entity.StudentHome;

public interface StudentHomeTask {

    void getAllLocations(StudentHomeAdapter adapter);

    void saveStudentHome(Context context, StudentHome studentHome, Student student);

    void getAllLocationsName(NewAccountLocationNameAdapter adapter);

    void getAllNames(NewAccountSNamesAdapter adapter, String locationName);
}
