package app.washtime.com.washtime.task.impl;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

import app.washtime.com.washtime.activity.GeneralAdminActivity;
import app.washtime.com.washtime.activity.StudentActivity;
import app.washtime.com.washtime.constants.ConfigConstants;
import app.washtime.com.washtime.entity.Role;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.entity.StudentHome;
import app.washtime.com.washtime.task.StudentTask;


public class StudentTaskImpl implements StudentTask {

    @Override
    public void login(Context context, String email, String password) {
        new Login(context, email, password).execute();
    }

    public String createNewAccountStudent(String firstName, String lastName, String username, int room, String locationName, String location, String password) {
        try {
            return new CreateNewAccount(firstName, lastName, username, room, location, locationName, password).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateStudent(Student student) {
        try {
            return new UpdateStudent().execute(student).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updatePassword(Object... params) {
        try {
            return new UpdatePassword().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudentByUsername(String username) {
        try {
            return new StudentGetByUsername(username).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class StudentGetByUsername extends AsyncTask<Void, Void, Student> {

        private String mUsername;

        public StudentGetByUsername(String username) {
            mUsername = username;
        }

        @Override
        protected Student doInBackground(Void... params) {
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getUser/" + mUsername;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Student student = restTemplate.getForObject(url, Student.class);
                return student;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }

    private class CreateNewAccount extends AsyncTask<Void, Void, String> {

        private String mFirstName;
        private String mLastName;
        private String mUsername;
        private int mRoom;
        private String mLocation;
        private String mLocationName;
        private String mPassword;

        public CreateNewAccount(String firstName, String lastName, String username, int room, String location, String locationName, String password) {
            mFirstName = firstName;
            mLastName = lastName;
            mUsername = username;
            mRoom = room;
            mLocation = location;
            mPassword = password;
            mLocationName = locationName;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://"+ ConfigConstants.IP + "/saveStudent";
                RestTemplate restTemplate = new RestTemplate();
                StudentHome studentHome = new StudentHome();
                studentHome.setLocationName(mLocationName);
                studentHome.setName(mLocation);
                Student student = new Student();
                student.setFirstName(mFirstName);
                student.setUserName(mUsername);
                student.setLastName(mLastName);
                student.setRoom(mRoom);
                student.setPassword(mPassword);
                student.setRole(Role.STUDENT);
                student.setStudentHome(studentHome);
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<String> postResponse = restTemplate.postForEntity(url, student, String.class);
                return postResponse.getStatusCode().toString();
            } catch (Exception e) {
            }
            return null;
        }
    }

    private class Login extends AsyncTask<Void, Void, Student> {

        private String mUserName;
        private String mPassword;
        private Context mContext;

        public Login(Context context, String userName, String password) {
            mUserName = userName;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Student doInBackground(Void... params) {
            try {
                final String url = "http://" + ConfigConstants.IP + "/login/" + mUserName + "/" + mPassword;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Student student = restTemplate.getForObject(url, Student.class);
                return student;
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Student response) {
            if (response != null) {
                if (response.getRole().equals(Role.GENERAL_ADMIN)) {
                    Intent intent = new Intent(mContext, GeneralAdminActivity.class);
                    intent.putExtra("admin", response);
                    mContext.startActivity(intent);
                } else {
                   Intent intent = new Intent(mContext.getApplicationContext(), StudentActivity.class);
                   intent.putExtra("student", response);
                   mContext.startActivity(intent);
                }
            } else {
            }
        }
    }

    private class UpdateStudent extends AsyncTask<Student, Void, String> {

        @Override
        protected String doInBackground(Student... params) {
            final String url = "http://"+ ConfigConstants.IP + "/updateStudent";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String> postResponse = restTemplate.postForEntity(url, params[0], String.class);
            return postResponse.getStatusCode().toString();
        }
    }

    private class UpdatePassword extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            final String url = "http://"+ ConfigConstants.IP + "/updatePassword/" + (String) params[1];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String> postResponse = restTemplate.postForEntity(url, (Student) params[0], String.class);
            return postResponse.getStatusCode().toString();
        }
    }
}
