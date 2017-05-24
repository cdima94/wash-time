package app.washtime.com.washtime.task.impl;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import app.washtime.com.washtime.activity.GeneralAdminActivity;
import app.washtime.com.washtime.adapter.NewAccountLocationNameAdapter;
import app.washtime.com.washtime.adapter.NewAccountSNamesAdapter;
import app.washtime.com.washtime.adapter.StudentHomeAdapter;
import app.washtime.com.washtime.constants.ConfigConstants;
import app.washtime.com.washtime.entity.Student;
import app.washtime.com.washtime.entity.StudentHome;
import app.washtime.com.washtime.task.StudentHomeTask;

public class StudentHomeTaskImpl implements StudentHomeTask {

    @Override
    public void getAllLocations(StudentHomeAdapter adapter) {
        new GetAllLocations(adapter).execute();
    }

    @Override
    public void saveStudentHome(Context context, StudentHome studentHome, Student student) {
        new SaveStudentHome(context, studentHome, student).execute();
    }

    @Override
    public void getAllLocationsName(NewAccountLocationNameAdapter adapter) {
        new GetAllLocationsName(adapter).execute();
    }

    @Override
    public void getAllNames(NewAccountSNamesAdapter adapter, String locationName) {
        new GetAllNames(adapter, locationName).execute();
    }

    private class SaveStudentHome extends AsyncTask<Void, Void, String> {

        private StudentHome mStudentHome;
        private Context mContext;
        private Student mStudent;

        protected SaveStudentHome(Context context, StudentHome studentHome, Student student) {
            mStudentHome = studentHome;
            mContext = context;
            mStudent = student;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://" + ConfigConstants.IP + "/saveStudentHome";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<String> postResponse = restTemplate.postForEntity(url, mStudentHome, String.class);
                return postResponse.getStatusCode().toString();
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response.equals("200")) {
                Intent intent = new Intent(mContext, GeneralAdminActivity.class);
                intent.putExtra("admin", mStudent);
                mContext.startActivity(intent);
            }
        }
    }

    private class GetAllLocations extends AsyncTask<Void, Void, List<StudentHome>> {

        StudentHomeAdapter mAdapter;

        protected GetAllLocations(StudentHomeAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        protected List<StudentHome> doInBackground(Void... params) {
            List<StudentHome> studentHomeList = new ArrayList<>();
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getAllLocations";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<LinkedHashMap> studentHomeLinkedMap = restTemplate.getForObject(url, List.class);
                for(LinkedHashMap linkedHashMap: studentHomeLinkedMap) {
                    StudentHome studentHome = new StudentHome();
                    studentHome.setName((String) linkedHashMap.get("name"));
                    studentHome.setLocationName((String) linkedHashMap.get("locationName"));
                    studentHomeList.add(studentHome);
                }
                return studentHomeList;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<StudentHome> studentHome) {
            mAdapter.populateStudentHomeAdapter(studentHome);
        }
    }

    private class GetAllLocationsName extends AsyncTask<Void, Void, List<String>> {

        NewAccountLocationNameAdapter mAdapter;

        protected GetAllLocationsName(NewAccountLocationNameAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getAllLocationsName";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<String> locationsName = restTemplate.getForObject(url, List.class);
                return locationsName;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> locationsName) {
            mAdapter.populateStudentHomeAdapter(locationsName);
        }
    }

    private class GetAllNames extends AsyncTask<Void, Void, List<String>> {

        NewAccountSNamesAdapter mAdapter;
        String mLocationName;

        protected GetAllNames(NewAccountSNamesAdapter adapter, String locationName) {
            mAdapter = adapter;
            mLocationName = locationName;
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                final String url = "http://"+ ConfigConstants.IP +"/getAllNames/" + mLocationName;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                List<String> names = restTemplate.getForObject(url, List.class);
                return names;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> names) {
            mAdapter.populateStudentHomeAdapter(names);
        }
    }
}
