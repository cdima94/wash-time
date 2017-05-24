package app.washtime.com.washtime.task;

import android.content.Context;

import app.washtime.com.washtime.entity.Student;

public interface StudentTask {

    void login(Context context, String email, String password);

    String createNewAccountStudent(String firstName, String lastName, String username, int room, String locationName, String location, String password);

    String updateStudent(Student student);

    String updatePassword(Object... params);

    Student getStudentByUsername(String username);
}
